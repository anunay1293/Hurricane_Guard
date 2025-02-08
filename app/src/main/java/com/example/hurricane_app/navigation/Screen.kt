package com.example.hurricane_app.navigation

sealed class Screen(val route: String) {
    object MentalHealth : Screen("mental_health")
    object Shelter : Screen("shelter")
    object StayAtHome : Screen("stay_at_home")
    object Hurricane : Screen("hurricane")
}
