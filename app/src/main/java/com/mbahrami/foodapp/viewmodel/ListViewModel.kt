package com.mbahrami.foodapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbahrami.foodapp.data.model.ResponseCategoriesList
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import com.mbahrami.foodapp.data.repository.ListRepository
import com.mbahrami.foodapp.ui.screen.list.FoodListSourceState
import com.mbahrami.foodapp.util.ConnectionState
import com.mbahrami.foodapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository,
    private val connectionState: ConnectionState
) : ViewModel() {


    var isConnected by mutableStateOf(true)
        private set

    private var foodListSourceState by mutableStateOf(FoodListSourceState.ALPHABET)

    var searchQuery by mutableStateOf("")
        private set
    var selectedFilterChar by mutableStateOf("A")
        private set

    var selectedCategory by mutableStateOf<ResponseCategoriesList.Category?>(null)
        private set

    var randomFood by mutableStateOf<RequestState<ResponseFoodsList.Meal>>(
        RequestState.Empty
    )
        private set

    var foodsListState by mutableStateOf<RequestState<List<ResponseFoodsList.Meal>>>(
        RequestState.Empty
    )
        private set

    var categoryListState by mutableStateOf<RequestState<List<ResponseCategoriesList.Category>>>(
        RequestState.Empty
    )
        private set

    init {
        loadRandomFood()
        loadCategories()
        loadFoodsByChar()
    }


    private fun onFoodListSourceStateChange(newState: FoodListSourceState) {
        foodListSourceState = newState
    }

    fun onSearchFieldValueChange(newValue: String) {
        searchQuery = newValue
        if (searchQuery.trim().isNotEmpty()) {
            onFoodListSourceStateChange(FoodListSourceState.SEARCH)
            searchFoods()
            selectedCategory = null
        } else {
            onFoodListSourceStateChange(FoodListSourceState.ALPHABET)
            loadData()
        }
    }

    fun onFilterCharClicked(newFilterChar: String) {
        selectedFilterChar = newFilterChar
        onFoodListSourceStateChange(FoodListSourceState.ALPHABET)
        loadData()
        selectedCategory = null
        onSearchFieldValueChange("")
    }

    fun onCategoryClicked(newSelectedCategory: ResponseCategoriesList.Category) {
        if (newSelectedCategory == selectedCategory) {
            selectedCategory = null
            onFoodListSourceStateChange(FoodListSourceState.ALPHABET)
        } else {
            selectedCategory = newSelectedCategory
            onFoodListSourceStateChange(FoodListSourceState.CATEGORY)
            searchQuery = ""
        }
        loadData()
    }

    private fun loadRandomFood() =
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionState.isConnected.value) {
                isConnected = true
                repository.randomFood().collectLatest {
                    randomFood = it
                }
            } else {
                isConnected = false
            }
        }

    private fun loadCategories() =
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionState.isConnected.value) {
                isConnected = true
                repository.categoriesList().collectLatest {
                    categoryListState = it
                }
            } else {
                isConnected = false
            }
        }

    private fun loadFoodsByChar() =
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionState.isConnected.value) {
                isConnected = true
                repository.foodsList(letter = selectedFilterChar).collectLatest {
                    foodsListState = it
                }
            } else {
                isConnected = false
            }
        }

    private fun loadFoodsByCategory() =
        viewModelScope.launch(Dispatchers.IO) {
            selectedCategory?.let { category ->
                if (connectionState.isConnected.value) {
                    isConnected = true
                    repository.foodsListByCategory(letter = category.strCategory).collectLatest {
                        foodsListState = it
                    }
                } else {
                    isConnected = false
                }
            }
        }

    private fun searchFoods() =
        viewModelScope.launch(Dispatchers.IO) {
            if (connectionState.isConnected.value) {
                isConnected = true
                repository.searchFoods(letter = searchQuery).collectLatest {
                    foodsListState = it
                }

            } else {
                isConnected = false
            }
        }

    fun loadData() {
        when (foodListSourceState) {
            FoodListSourceState.ALPHABET -> {
                loadFoodsByChar()
            }

            FoodListSourceState.CATEGORY -> {
                loadFoodsByCategory()
            }

            FoodListSourceState.SEARCH -> {
                searchFoods()
            }
        }
    }
}