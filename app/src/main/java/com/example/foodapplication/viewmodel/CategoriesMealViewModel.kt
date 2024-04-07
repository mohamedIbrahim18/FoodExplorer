package com.example.foodapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapplication.model.PopularMealsItem
import com.example.foodapplication.retrofit.api.ApiManger
import kotlinx.coroutines.launch

class CategoriesMealViewModel : ViewModel() {
    val mealsLiveData =MutableLiveData<List<PopularMealsItem?>?>()


    fun getMealByCategory(nameOfMeal: String) {
        try {
            viewModelScope.launch {
                val meal = ApiManger.getApis().getMealsByCategory(nameOfMeal).meals
                mealsLiveData.postValue(meal)
            }
        } catch (e: Exception) {
            Log.d("api fail", e.localizedMessage)
        }

    }
}