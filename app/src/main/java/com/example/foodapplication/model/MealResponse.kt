package com.example.foodapplication.model

import com.google.gson.annotations.SerializedName

data class MealResponse(

	@field:SerializedName("meals")
	val meals: List<MealsInformation?>? = null
)