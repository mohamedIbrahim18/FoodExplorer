package com.example.foodapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapplication.model.MealsInformation

@Database(entities = [MealsInformation::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        private var instance: MealDataBase? = null

        @Synchronized
        fun getInstance(context: Context): MealDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDataBase::class.java, "Meal.db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}