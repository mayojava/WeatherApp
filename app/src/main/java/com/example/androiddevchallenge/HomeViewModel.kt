/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.AppState.Loaded
import com.example.androiddevchallenge.AppState.RequestingPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

sealed class AppState {
    object RequestingPermission : AppState()
    object Loading : AppState()
    data class Loaded(val data: WeatherResult) : AppState()
}

class HomeViewModel constructor(
    private val repository: WeatherRepository = Injector.repository()
) : ViewModel() {
    private val _currentState = MutableStateFlow<AppState>(RequestingPermission)
    val appState: StateFlow<AppState> = _currentState

    private val locationChannel = Channel<Location>(capacity = Channel.CONFLATED)

    init {
        viewModelScope.launch {
            delay(1000)
            locationChannel.consumeAsFlow()
                .map { loc -> repository.getWeatherData(loc.latitude, loc.longitude) }
                .collect { _currentState.value = Loaded(it) }
        }
    }

    fun fetchWeatherForLocation(location: Location) {
        viewModelScope.launch { locationChannel.send(location) }
    }
}
