package com.mbahrami.foodapp.navigation.destination

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mbahrami.foodapp.ui.screen.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListScr

fun NavGraphBuilder.listComposable(navController: NavHostController) {
    composable<ListScr> {
        ListScreen(
            listVM = hiltViewModel(),
            navigateToDetail = { id ->
                navController.navigate(DetailScr(id = id))
            })
    }
}