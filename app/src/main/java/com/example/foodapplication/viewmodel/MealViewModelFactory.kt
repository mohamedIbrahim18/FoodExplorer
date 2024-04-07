package com.example.foodapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.foodapplication.db.MealDataBase

class MealViewModelFactory(
    val mealDataBase: MealDataBase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MealViewModel(mealDataBase) as T
    }
}