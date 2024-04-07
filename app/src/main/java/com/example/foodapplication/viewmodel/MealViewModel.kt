package com.example.foodapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapplication.db.MealDataBase
import com.example.foodapplication.retrofit.api.ApiManger
import com.example.foodapplication.model.MealsInformation
import kotlinx.coroutines.launch

class MealViewModel(
    val mealDataBase: MealDataBase
) : ViewModel() {

    var mealDetails = MutableLiveData<MealsInformation?>()

    fun getMealDetails(id: String) {
        viewModelScope.launch {
            try {
                val details = ApiManger.getApis().getMealDetails(id).meals?.get(0)
                mealDetails.postValue(details)
                Log.e("check", details?.idMeal ?: "hahaha null")
            } catch (e: Exception) {
                Log.e("Api meal deatils error", e.localizedMessage)
            }
        }
    }

    fun insertMeal(meal: MealsInformation) {
        viewModelScope.launch {
            mealDataBase.mealDao().update(meal)
        }
    }



}