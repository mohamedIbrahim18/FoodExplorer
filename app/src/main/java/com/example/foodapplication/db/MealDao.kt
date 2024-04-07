package com.example.foodapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapplication.model.MealsInformation

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(mealsInformation: MealsInformation)

    @Delete
    suspend fun delete(mealsInformation: MealsInformation)
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<MealsInformation>>
}