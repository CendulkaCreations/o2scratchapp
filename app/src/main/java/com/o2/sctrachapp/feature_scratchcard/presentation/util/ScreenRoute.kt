package com.o2.sctrachapp.feature_scratchcard.presentation.util

sealed class ScreenRoute(val route: String) {
    data object HomeScreen: ScreenRoute("home_screen")
    data object ScratchCardScreen: ScreenRoute("scratch_card_screen")
    data object ActivationScreen: ScreenRoute("activation_screen")
}