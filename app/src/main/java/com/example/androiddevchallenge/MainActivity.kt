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

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.AppState.Loaded
import com.example.androiddevchallenge.AppState.Loading
import com.example.androiddevchallenge.AppState.RequestingPermission
import com.example.androiddevchallenge.ui.theme.WeatherAppTheme
import com.google.android.gms.location.LocationServices
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val locationProvider = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            WeatherAppTheme {
                val vm = viewModel(HomeViewModel::class.java)
                val state = vm.appState.collectAsState().value

                ProvideWindowInsets {
                    Home(state = state) { permissionGranted ->
                        if (permissionGranted) {
                            locationProvider.lastLocation.addOnSuccessListener { loc ->
                                if (loc != null) {
                                    vm.fetchWeatherForLocation(loc)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun Home(state: AppState, onPermissionGranted: (Boolean) -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Crossfade(targetState = state) {
            when (it) {
                is RequestingPermission -> RequestPermission(onPermissionGranted)
                is Loading -> Loading()
                is Loaded -> WeatherHome(it.data)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        Home(state = Loading)
    }
}
