package com.mbahrami.foodapp.ui.screen.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.ui.theme.shadowBlue
import com.mbahrami.foodapp.util.RequestState
import com.mbahrami.foodapp.util.shimmerBrush

@Composable
fun Header(requestState: RequestState<ResponseFoodsList.Meal>) {
    when (requestState) {
        is RequestState.Empty, is RequestState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenWidthDp.dp)
                    .background(brush = shimmerBrush(showShimmer = true))
            )
        }

        is RequestState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenWidthDp.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = requestState.data.strMealThumb,
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.4f)
                        .background(shadowBlue)
                )
            }
        }

        is RequestState.Error -> {
            Log.e("909090", "Header: ${requestState.message}")
        }
    }

}
