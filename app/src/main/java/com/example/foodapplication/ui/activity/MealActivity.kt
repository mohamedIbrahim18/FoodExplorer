package com.example.foodapplication.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapplication.R
import com.example.foodapplication.databinding.ActivityMealBinding
import com.example.foodapplication.db.MealDataBase
import com.example.foodapplication.ui.fragments.HomeFragment
import com.example.foodapplication.model.MealsInformation
import com.example.foodapplication.viewmodel.MealViewModel
import com.example.foodapplication.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    lateinit var nameOfMeal : String
    lateinit var idOfMeal : String
    lateinit var imgOfMeal:String
    lateinit var youtubeLinkOfMeal:String
    lateinit var binding: ActivityMealBinding
    lateinit var mealsViewModel : MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
       val mealDataBase = MealDataBase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)
        mealsViewModel = ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)

        // mealsViewModel = ViewModelProvider(this).get(MealViewModel::class.java)
        getMealInformationFromIntent()
        setInformationInViews()
        Log.e("berffor view",idOfMeal)
        loadingCase()
        mealsViewModel.getMealDetails(idOfMeal)
        subscribeToLiveData()
        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnToAddFav.setOnClickListener {

            mealToSave?.let {
                mealsViewModel.insertMeal(it)
                Toast.makeText(this,"We Have Saved This Meal",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLinkOfMeal))
            startActivity(intent)

        }
    }

    private var mealToSave:MealsInformation?=null
    private fun subscribeToLiveData() {
        mealsViewModel.mealDetails.observe(this){
            Log.e("observe done" , ""+it?.idMeal)
            it.let {
                responseCase()
                mealToSave = it
                binding.tvDesc.text = it?.strInstructions
                binding.mealCategory.text = "Category : ${it?.strCategory}"
                binding.mealLocation.text = "Area : ${it?.strArea}"
                youtubeLinkOfMeal = it?.strYoutube.toString()
            }

        }
    }

    private fun setInformationInViews() {
        Glide.with(this).load(imgOfMeal).into(binding.imgMealDeatils)
        binding.collapsingToolbar.title = nameOfMeal
        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        nameOfMeal = intent.getStringExtra("MEAL_NAME")!!
        idOfMeal = intent.getStringExtra("MEAL_ID")!!
        imgOfMeal = intent.getStringExtra("MEAL_IMG")!!
    Log.e("code", idOfMeal)
    }
    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnToAddFav.visibility = View.INVISIBLE
        binding.tvInstrucations.visibility = View.INVISIBLE
        binding.mealCategory.visibility = View.INVISIBLE
        binding.mealLocation.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun responseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnToAddFav.visibility = View.VISIBLE
        binding.tvInstrucations.visibility = View.VISIBLE
        binding.mealCategory.visibility = View.VISIBLE
        binding.mealLocation.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}