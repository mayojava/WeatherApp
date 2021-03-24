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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieAnimationSpec
import com.airbnb.lottie.compose.rememberLottieAnimationState
import com.example.androiddevchallenge.Util.colors
import com.example.androiddevchallenge.ui.theme.color3
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherHome(data: WeatherResult) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dominantColor = rememberDominantColorState(defaultColor = color3)

        var selectedIndex by remember { mutableStateOf(0) }

        DynamicThemePrimaryColorsFromImage(dominantColor) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalGradientScrim(
                        colors = colors[selectedIndex], // MaterialTheme.colors.primary.copy(alpha = 0.38f),
                        startYPercentage = 0f,
                        endYPercentage = 1f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.statusBarsHeight(additional = 16.dp))
                Text(data.timezone, style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(48.dp))

                val pagerState = remember { PagerState() }
                WeatherPager(
                    items = data.daily.subList(0, 6),
                    pagerState = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(560.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    data.daily.subList(0, 6).forEachIndexed { index, dailyWeatherData ->
                        DayWeatherItem(data = dailyWeatherData, selected = index == selectedIndex) {
                            selectedIndex = index
                            // pagerState.sna
                            pagerState.currentPage = selectedIndex
                            dominantColor.updateColors(mainColor = colors[selectedIndex][0])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherPager(
    items: List<DailyWeatherData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    pagerState.maxPage = items.size - 1
    Pager(state = pagerState, modifier = modifier) {
        WeatherPagerItem(items[page], modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun WeatherPagerItem(data: DailyWeatherData, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val animationSpec =
            remember { LottieAnimationSpec.RawRes(Util.getLottieFile(data.weather[0].main)) }
        val animationState =
            rememberLottieAnimationState(autoPlay = true, repeatCount = Integer.MAX_VALUE)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${data.temp.day.roundToInt()}", style = MaterialTheme.typography.h1)
            Text(text = "o", style = MaterialTheme.typography.h4.copy(fontSize = 64.sp))
        }

        Box(modifier = Modifier.size(260.dp)) {
            LottieAnimation(
                animationSpec,
                animationState = animationState,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(data.weather[0].description, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Feels like ${data.feels_like.day.roundToInt()}", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = getDay(data.dt), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun getDay(tm: Long): String {
    val cal = Calendar.getInstance()

    val today = cal.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())
    cal.timeInMillis = tm * 1000L
    val day = cal.getDisplayName(Calendar.DAY_OF_WEEK, 2, Locale.getDefault())

    return if (today == day) "Today" else day!!
}

@Composable
fun DayWeatherItem(data: DailyWeatherData, selected: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = if (selected) animateColorAsState(
                    targetValue = MaterialTheme.colors.background,
                    spring(stiffness = Spring.StiffnessLow)
                ).value
                else animateColorAsState(
                    targetValue = Color.Transparent,
                    spring(stiffness = Spring.StiffnessLow)
                ).value,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        val icon = data.weather[0].icon
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                data = "https://openweathermap.org/img/wn/$icon@4x.png",
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${data.temp.max.roundToInt()}", style = MaterialTheme.typography.body1)
        }
    }
}
