package com.mbahrami.foodapp.data.repository

import com.mbahrami.foodapp.data.model.ResponseCategoriesList
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.data.server.ApiService
import com.mbahrami.foodapp.util.ConnectionState
import com.mbahrami.foodapp.util.RequestState
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class ListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun randomFood():Flow<RequestState<ResponseFoodsList.Meal>> {
        return flow {
            emit(RequestState.Loading)
            val apiResponse = apiService.randomFood()
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
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun categoriesList(): Flow<RequestState<List<ResponseCategoriesList.Category>>> {
        return flow {
            emit(RequestState.Loading)
            val apiResponse = apiService.categoriesList()
            when (apiResponse.code()) {
                in 200..202 -> {
                    emit(RequestState.Success(apiResponse.body()!!.categories))
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
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun foodsList(letter: String): Flow<RequestState<List<ResponseFoodsList.Meal>>> {
        return flow {
            emit(RequestState.Loading)
            val apiResponse = apiService.foodsList(letter = letter)
            when (apiResponse.code()) {
                in 200..202 -> {
                    if (apiResponse.body()!!.meals.isNullOrEmpty()) {
                        emit(RequestState.Success(emptyList()))
                    } else {
                        emit(RequestState.Success(apiResponse.body()!!.meals!!))
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
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchFoods(letter: String): Flow<RequestState<List<ResponseFoodsList.Meal>>> {
        return flow {
            emit(RequestState.Loading)
            val apiResponse = apiService.searchFood(letter = letter)
            when (apiResponse.code()) {
                in 200..202 -> {
                    if (apiResponse.body()!!.meals.isNullOrEmpty()) {
                        emit(RequestState.Success(emptyList()))
                    } else {
                        emit(RequestState.Success(apiResponse.body()!!.meals!!))
                    }                }

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
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun foodsListByCategory(letter: String): Flow<RequestState<List<ResponseFoodsList.Meal>>> {
        return flow {
            emit(RequestState.Loading)
            val apiResponse = apiService.foodsByCategory(letter = letter)
            when (apiResponse.code()) {
                in 200..202 -> {
                    if (apiResponse.body()!!.meals.isNullOrEmpty()) {
                        emit(RequestState.Success(emptyList()))
                    } else {
                        emit(RequestState.Success(apiResponse.body()!!.meals!!))
                    }                }

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
        }.catch {
            emit(RequestState.Error(message = it.message))
        }.flowOn(Dispatchers.IO)
    }
}