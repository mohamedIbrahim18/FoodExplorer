package com.example.foodapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapplication.adapter.CategoryMealAdapter
import com.example.foodapplication.databinding.ActivityCategoryMealBinding
import com.example.foodapplication.model.PopularMealsItem
import com.example.foodapplication.viewmodel.CategoriesMealViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityCategoryMealBinding
    lateinit var categoryMealViewModel: CategoriesMealViewModel
    lateinit var categoryMealAdapter : CategoryMealAdapter
    lateinit var nameOfCategory : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        prepareRecycler()
        categoryMealViewModel = ViewModelProvider(this)[CategoriesMealViewModel::class.java]
        nameOfCategory = intent.getStringExtra("CATEGORY_MEAl_NAME")!!
        categoryMealViewModel.getMealByCategory(nameOfCategory)
        getTheTotalNumbersOfMeals()

       // getDataFromIntent()
        subscribeToLiveData()
        onMealClick()

    }

    private fun getTheTotalNumbersOfMeals() {


    }

    private fun onMealClick() {
        categoryMealAdapter.onItemClickedListener = object :CategoryMealAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, meal: PopularMealsItem) {
                val intent = Intent(this@CategoryMealActivity,MealActivity::class.java)
                intent.putExtra("MEAL_ID", meal.idMeal)
                intent.putExtra("MEAL_NAME", meal.strMeal)
                intent.putExtra("MEAL_IMG", meal.strMealThumb)
                startActivity(intent)
            }

        }
    }

    private fun prepareRecycler() {
        categoryMealAdapter = CategoryMealAdapter(null)
        viewBinding.recViewSpesifcMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }

    private fun subscribeToLiveData() {
        categoryMealViewModel.mealsLiveData.observe(this){mealsList->
                    categoryMealAdapter.setTheMeals(mealsList)
            viewBinding.tvCategoryCount.text = mealsList?.size.toString()
        }
    }
}