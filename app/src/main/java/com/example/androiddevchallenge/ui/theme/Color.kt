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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val white = Color(0xFFFFFF)
val black = Color(0xFF000000)
val color1 = Color(0xFF052731)
val color3 = Color(0xFF242D28)
val color4 = Color(0xFF7DEFF4)
val color5 = Color(0xFF17518C)
val color6 = Color(0xFF76E8FC)
val color7 = Color(0xFF480F24)
val color8 = Color(0xFF4E887D)
val color9 = Color(0xFF00303E)

val DarkColorPalette = darkColors(
    primary = black,
    onPrimary = white,
    background = black,
    onBackground = white
)

val LightColorPalette = lightColors(
    primary = white,
    onPrimary = black,
    background = white,
    onBackground = black

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
