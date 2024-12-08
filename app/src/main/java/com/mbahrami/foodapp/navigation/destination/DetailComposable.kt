package com.mbahrami.foodapp.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mbahrami.foodapp.ui.screen.detail.DetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class DetailScr(val id: Int)

fun NavGraphBuilder.detailComposable(navController: NavHostController) {
    composable<DetailScr> { backStackEntry ->
        val detailScr = backStackEntry.toRoute<DetailScr>()
        DetailScreen()
    }
}