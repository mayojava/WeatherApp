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

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {
    private fun okHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okHttp().newBuilder().addInterceptor { chain ->
                    val req = chain.request()
                    val originalUrl = req.url
                    val newurl = originalUrl.newBuilder()
                        .addQueryParameter("appid", BuildConfig.API_PRIVATE_KEY)
                        .addQueryParameter("units", "metric")
                        .build()

                    chain.proceed(
                        req.newBuilder()
                            .url(newurl)
                            .build()
                    )
                }.build()
            )
            .build()
    }

    private fun api(): WeatherService {
        return retrofit().create(WeatherService::class.java)
    }

    fun repository(): WeatherRepository = WeatherRepository(api())
}
