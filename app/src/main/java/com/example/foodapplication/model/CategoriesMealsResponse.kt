package com.example.foodapplication.model

import com.google.gson.annotations.SerializedName

data class CategoriesMealsResponse(

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null
)