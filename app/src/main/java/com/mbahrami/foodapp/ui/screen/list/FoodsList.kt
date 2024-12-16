package com.mbahrami.foodapp.ui.screen.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.rounded.Fastfood
import androidx.compose.material.icons.rounded.Language
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mbahrami.foodapp.component.EmptyView
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.ui.theme.tartOrange
import com.mbahrami.foodapp.util.RequestState
import com.mbahrami.foodapp.util.shimmerBrush


@Composable
fun FoodsList(
    foodsListState: RequestState<List<ResponseFoodsList.Meal>>,
    onItemClicked: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        if (foodsListState is RequestState.Success && foodsListState.data.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Foods",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            when (foodsListState) {
                is RequestState.Loading, RequestState.Empty -> {
                    items(2) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(230.dp)
                                .width((170 * 3 / 2).dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(brush = shimmerBrush(showShimmer = true))
                        )
                    }
                }

                is RequestState.Success -> {
                    if (foodsListState.data.isNotEmpty()) {
                        items(items = foodsListState.data, key = { it.idMeal }) {
                            FoodItem(food = it, onItemClicked = onItemClicked)
                        }
                    } else {
                        item { EmptyView() }
                    }
                }

                is RequestState.Error -> {
                    Log.e("909090", "Category: ${foodsListState.message}")
                }
            }
        }
    }
}

@Composable
fun FoodItem(food: ResponseFoodsList.Meal, onItemClicked: (id: Int) -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .width((170 * 3 / 2).dp)
            .clickable { onItemClicked(food.idMeal.toInt()) },
        shape = MaterialTheme.shapes.medium,
        elevation = 4.dp
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .height(170.dp)
                    .aspectRatio(3f / 2f),
                model = food.strMealThumb,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                text = food.strMeal ?: "Food name",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
            )

            if (!food.strCategory.isNullOrEmpty()) {
                Row(
                    modifier = Modifier.width((170 * 3 / 2).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FoodAttr(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Rounded.Fastfood,
                        value = food.strCategory ?: "Enter value"
                    )
                    FoodAttr(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Rounded.Language,
                        value = food.strArea ?: "Enter value"
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = null,
                            tint = tartOrange
                        )
                    }
                }
            } else {
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FoodAttr(modifier: Modifier, icon: ImageVector, value: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(4.dp),
            imageVector = icon,
            contentDescription = null,
            tint = tartOrange
        )
        Text(text = value, style = MaterialTheme.typography.subtitle2, maxLines = 1)
    }
}
