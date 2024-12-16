package com.mbahrami.foodapp.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.util.RequestState
import com.mbahrami.foodapp.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun DetailScreen(detailVM: DetailViewModel) {
    YoutubePlayer(
        detailState = detailVM.foodDetail,
        lifecycleOwner = LocalLifecycleOwner.current
    )
}

@Composable
fun YoutubePlayer(
    detailState: RequestState<ResponseFoodsList.Meal>,
    lifecycleOwner: LifecycleOwner
) {
    var showShimmer =
        remember { derivedStateOf { detailState is RequestState.Empty || detailState is RequestState.Loading } }
    when (detailState) {
        is RequestState.Empty,
        is RequestState.Loading -> {

        }

        is RequestState.Success -> {
            AndroidView(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                factory = {
                    YouTubePlayerView(context = it).apply {
                        lifecycleOwner.lifecycle.addObserver(this)

                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                super.onReady(youTubePlayer)
                                youTubePlayer.loadVideo("FgAL6T_KILw", 0f)
                            }

                            override fun onError(
                                youTubePlayer: YouTubePlayer,
                                error: PlayerConstants.PlayerError
                            ) {
                                super.onError(youTubePlayer, error)
                                Log.e("909090", "onError: ${error.name}", )
                            }

                        })
                    }
                }
            )
        }

        is RequestState.Error -> {
            Log.e("909090", "YoutubePlayer: ${detailState.message}")
        }
    }
}