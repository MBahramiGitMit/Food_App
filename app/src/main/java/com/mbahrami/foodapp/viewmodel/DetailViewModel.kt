package com.mbahrami.foodapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.data.repository.DetailRepository
import com.mbahrami.foodapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    var foodDetail: RequestState<ResponseFoodsList.Meal> by mutableStateOf(RequestState.Empty)
        private set

    fun loadDetail(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        detailRepository.foodDetail(id = id).collectLatest {
            foodDetail = it
        }
    }
}