package com.mbahrami.foodapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mbahrami.foodapp.navigation.destination.ListScr
import com.mbahrami.foodapp.navigation.destination.detailComposable
import com.mbahrami.foodapp.navigation.destination.listComposable

@Composable
fun SetupNavigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = ListScr) {
        listComposable(navController = navController)
        detailComposable(navController = navController)
    }
}