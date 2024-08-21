package com.giftease.chethan.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giftease.chethan.data.Gifteasehome.GifteaseViewHome
import com.giftease.chethan.Gifteasenavigation.AppRouter
import com.giftease.chethan.Gifteasenavigation.Screen
import com.giftease.chethan.GiftEaseScreens.HomeScreen
import com.giftease.chethan.GiftEaseScreens.LoginScreen
import com.giftease.chethan.GiftEaseScreens.SignUpScreen
import com.giftease.chethan.GiftEaseScreens.TermsAndConditionsScreen


@Composable
fun GiftEase(homeViewModel: GifteaseViewHome = viewModel()) {

    homeViewModel.checkForActiveSession()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray
    ) {

        if (homeViewModel.isUserLoggedIn.value == true) {
            AppRouter.navigateTo(Screen.HomeScreen)
        }

        Crossfade(targetState = AppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen()
                }

                is Screen.HomeScreen -> {
                    HomeScreen()
                }
            }
        }

    }
}