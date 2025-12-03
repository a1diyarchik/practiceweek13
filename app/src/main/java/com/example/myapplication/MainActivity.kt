package com.example.myapplication

import PostsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("edit_profile")
    data object Posts : Screen("posts")


}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by rememberSaveable { mutableStateOf(false) }
            val vm: ProfileViewModel = viewModel()

            MyApplicationTheme(darkTheme = darkMode) {
                AppNavHost(
                    viewModel = vm,
                    darkMode = darkMode,
                    onToggleDarkMode = { darkMode = !darkMode }
                )
            }
        }
    }
}


@Composable
fun AppNavHost(
    viewModel: ProfileViewModel,
    darkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
        composable(Screen.Posts.route) {
            PostsScreen(
                navController = navController,
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                navController = navController,
                viewModel = viewModel,
                darkMode = darkMode,
                onToggleDarkMode = onToggleDarkMode
            )
        }
    }
}


@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.4f),
        Color.LightGray.copy(alpha = 0.8f),
        Color.LightGray.copy(alpha = 0.4f),
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation - 1000f, 0f),
        end = Offset(translateAnimation, 0f)
    )

    Box(
        modifier = modifier
            .background(brush)
    )
}


