package com.o2.sctrachapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.o2.sctrachapp.feature_scratchcard.presentation.activation.ActivationScreen
import com.o2.sctrachapp.feature_scratchcard.presentation.home.HomeScreen
import com.o2.sctrachapp.feature_scratchcard.presentation.scratch.ScratchCardScreen
import com.o2.sctrachapp.feature_scratchcard.presentation.util.ScreenRoute
import com.o2.sctrachapp.ui.theme.SctrachappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SctrachappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = ScreenRoute.HomeScreen.route
                        ) {
                            composable(route = ScreenRoute.HomeScreen.route) {
                                HomeScreen(navController)
                            }
                            composable(route = ScreenRoute.ScratchCardScreen.route) {
                                ScratchCardScreen(navController)
                            }
                            composable(route = ScreenRoute.ActivationScreen.route) {
                                ActivationScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}