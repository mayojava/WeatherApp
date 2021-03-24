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

import com.example.androiddevchallenge.ui.theme.color1
import com.example.androiddevchallenge.ui.theme.color3
import com.example.androiddevchallenge.ui.theme.color5
import com.example.androiddevchallenge.ui.theme.color7
import com.example.androiddevchallenge.ui.theme.color8
import com.example.androiddevchallenge.ui.theme.color9

object Util {
    fun getLottieFile(condition: String): Int {
        return when (condition) {
            "Thunderstorm" -> R.raw.weather_thunder
            "Drizzle" -> R.raw.weather_partly_shower
            "Rain" -> R.raw.weather_partly_shower
            "Snow" -> R.raw.weather_snow
            "Mist" -> R.raw.weather_mist
            "Fog" -> R.raw.foggy
            "Dust" -> R.raw.foggy
            "Clouds" -> R.raw.weather_partly_cloudy
            else -> R.raw.weather_sunny
        }
    }

    val colors = arrayOf(
        listOf(color3.copy(alpha = 0.5f), color3),
        listOf(color5.copy(alpha = 0.5f), color5),
        listOf(color1.copy(alpha = 0.5f), color1),
        listOf(color7.copy(alpha = 0.5f), color7),
        listOf(color8.copy(alpha = 0.5f), color8),
        listOf(color9.copy(alpha = 0.5f), color9)
    )
}
