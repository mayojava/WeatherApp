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

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/onecall")
    suspend fun getWeatherData(@Query("lat") lat: String, @Query("lon") lon: String): WeatherResult
}

data class WeatherResult(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val current: WeatherData,
    val hourly: List<WeatherData>,
    val daily: List<DailyWeatherData>
)

data class Temp(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)

data class FeelsLike(
    val day: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)

data class DailyWeatherData(
    val dt: Long,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val temp: Temp,
    val feels_like: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val uvi: Float,
    val clouds: Int,
    val wind_speed: Float,
    val wind_deg: Int,
    val pop: Float = 0f,
    val weather: List<Weather>
)

data class WeatherData(
    val dt: Long,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val temp: Float,
    val feels_like: Float,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Float,
    val uvi: Float,
    val clouds: Int,
    val visibility: Int,
    val wind_speed: Float,
    val wind_deg: Int,
    val wind_gust: Float = 0.0f,
    val pop: Float = 0f,
    val weather: List<Weather>
)

data class Weather(val id: Int, val main: String, val description: String, val icon: String)
