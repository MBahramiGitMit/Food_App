package com.mbahrami.foodapp.ui.screen.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mbahrami.foodapp.component.DisconnectedView
import com.mbahrami.foodapp.ui.theme.bg
import com.mbahrami.foodapp.util.RequestState
import com.mbahrami.foodapp.viewmodel.ListViewModel


enum class FoodListSourceState {
    ALPHABET, CATEGORY, SEARCH
}

@Composable
fun ListScreen(listVM: ListViewModel, navigateToDetail: (id: Int) -> Unit) {
    Surface(color = bg, modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Header(listVM.randomFood)
                    Spacer(Modifier.height(40.dp))
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (listVM.isConnected) {
                            Category(
                                categoriesListState = listVM.categoryListState,
                                selectedCategory = listVM.selectedCategory,
                                onItemClicked = { listVM.onCategoryClicked(it) })
                            FoodsList(
                                foodsListState = listVM.foodsListState, onItemClicked = navigateToDetail
                            )
                        } else {
                            DisconnectedView(onTryAgainClick = { listVM.loadData() })
                        }
                    }
                }
                if (listVM.randomFood is RequestState.Success) {
                    MidAppBar(
                        modifier = Modifier,
                        searchQuery = listVM.searchQuery,
                        onSearchFieldValueChange = { listVM.onSearchFieldValueChange(it) },
                        selectedFilterChar = listVM.selectedFilterChar,
                        onFilterCharClicked = { newChar ->
                            listVM.onFilterCharClicked(newChar)
                        })
                }

            }
        }

    }
}