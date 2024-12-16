package com.mbahrami.foodapp.data.repository

import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.data.server.ApiService
import com.mbahrami.foodapp.util.RequestState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@ViewModelScoped
class DetailRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun foodDetail(id: Int): Flow<RequestState<ResponseFoodsList.Meal>> {
        return flow {
            val apiResponse = apiService.foodDetail(id = id)
            when (apiResponse.code()) {
                in 200..202 -> {
                    if (apiResponse.body()!!.meals.isNullOrEmpty()) {
                        emit(RequestState.Empty)
                    } else {
                        emit(RequestState.Success(apiResponse.body()!!.meals!!.first()))
                    }
                }

                422 -> {
                    emit(RequestState.Error(message = "422"))

                }

                in 400..499 -> {
                    emit(RequestState.Error(message = "400 .. 499"))

                }

                in 500..599 -> {
                    emit(RequestState.Error(message = "500 .. 599"))

                }
            }
        }.onStart {
            emit(RequestState.Loading)
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }
}