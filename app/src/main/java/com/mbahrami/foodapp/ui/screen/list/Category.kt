package com.mbahrami.foodapp.ui.screen.list

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mbahrami.foodapp.data.model.ResponseCategoriesList
import com.mbahrami.foodapp.ui.theme.tartOrange
import com.mbahrami.foodapp.util.RequestState
import com.mbahrami.foodapp.util.shimmerBrush


@Composable
fun Category(
    categoriesListState: RequestState<List<ResponseCategoriesList.Category>>,
    selectedCategory: ResponseCategoriesList.Category?,
    onItemClicked: (category: ResponseCategoriesList.Category) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        when (categoriesListState) {
            is RequestState.Loading, RequestState.Empty -> {
                items(4) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(40.dp)
                            .width(150.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(brush = shimmerBrush(showShimmer = true))
                    )
                }
            }

            is RequestState.Success -> {
                items(items = categoriesListState.data, key = { it.idCategory }) {
                    CategoryItem(
                        category = it,
                        isSelected = it == selectedCategory,
                        onItemClicked = onItemClicked
                    )
                }
            }

            is RequestState.Error -> {
                Log.e("909090", "Category: ${categoriesListState.message}")
            }

        }
    }
}

@Composable
fun CategoryItem(
    category: ResponseCategoriesList.Category,
    isSelected: Boolean,
    onItemClicked: (category: ResponseCategoriesList.Category) -> Unit
) {
    Surface(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 2.dp, if (isSelected) tartOrange else Color.Transparent),
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 10.dp)
            .selectable(selected = isSelected, onClick = { onItemClicked(category) })
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                model = category.strCategoryThumb,
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = category.strCategory, style = MaterialTheme.typography.h6)
        }
    }
}
