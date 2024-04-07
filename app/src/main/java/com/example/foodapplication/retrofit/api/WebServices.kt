package com.example.foodapplication.retrofit.api

import com.example.foodapplication.model.CategoriesMealsResponse
import com.example.foodapplication.model.MealResponse
import com.example.foodapplication.model.PopularMealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealResponse
    @GET("filter.php")
    suspend fun getPopularMeals(@Query("c") categoryName:String) : PopularMealsResponse
    @GET("categories.php")
    suspend fun getCategoriesMeals(): CategoriesMealsResponse
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") mealCategoryName:String):  PopularMealsResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") mealsName:String) : MealResponse
}