package com.example.foodapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapplication.db.MealDataBase
import com.example.foodapplication.model.CategoriesItem
import com.example.foodapplication.retrofit.api.ApiManger
import com.example.foodapplication.model.MealsInformation
import com.example.foodapplication.model.PopularMealsItem
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mealDataBase: MealDataBase
) : ViewModel() {
    val randomMeal = MutableLiveData<MealsInformation?>()
    val popularMeals = MutableLiveData<List<PopularMealsItem?>?>()
    val allCategories = MutableLiveData<List<CategoriesItem?>?>()
    val favoriteMealsLiveData = mealDataBase.mealDao().getAllMeals()
     val searchMealLiveData = MutableLiveData<List<MealsInformation>>()
private var saveStateRandomMeal : MealsInformation?=null
    fun getRandomImage() {
        saveStateRandomMeal?.let {
            randomMeal.postValue(it)
            return
        }
        viewModelScope.launch {
            try {
                val randomMealVar = ApiManger.getApis().getRandomMeal()
                randomMeal.value = (randomMealVar.meals?.get(0))
                saveStateRandomMeal = randomMealVar.meals?.get(0)
              //  this@HomeViewModel.randomMeal.postValue(randomMealVar.meals?.get(0))
            } catch (e: Exception) {
                Log.d("api fail", e.localizedMessage)
            }

        }
    }

    fun getPopularMeals() {
        viewModelScope.launch {
            try {
                val popMeal = ApiManger.getApis().getPopularMeals("Seafood")
                popularMeals.postValue(popMeal.meals)
            } catch (e: Exception) {
                Log.d("api fail", e.localizedMessage)

            }
        }
    }

    fun getCategoriesMeal() {
        viewModelScope.launch {
            try {
                val categories = ApiManger.getApis().getCategoriesMeals()
                allCategories.postValue(categories.categories)
            } catch (e: Exception) {
                Log.d("api fail", e.localizedMessage)
            }
        }
    }

    fun insertMeal(meal: MealsInformation) {
        viewModelScope.launch {
            mealDataBase.mealDao().update(meal)
        }
    }

    fun deleteMeal(meal: MealsInformation) {
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun searchMeals(searchQuery: String) {
        viewModelScope.launch {
            try {
                val result = ApiManger.getApis().searchMeals(searchQuery)
                result.meals.let {
                    searchMealLiveData.postValue(it as List<MealsInformation>?)
                }
            } catch (e: Exception) {
                Log.d("api fail", e.localizedMessage)
            }
        }
    }
}