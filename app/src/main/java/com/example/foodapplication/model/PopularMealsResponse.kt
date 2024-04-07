package com.example.foodapplication.model

import com.google.gson.annotations.SerializedName

data class PopularMealsResponse(

	@field:SerializedName("meals")
	val meals: List<PopularMealsItem?>? = null
)