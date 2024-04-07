package com.example.foodapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapplication.R
import com.example.foodapplication.databinding.ActivityMainBinding
import com.example.foodapplication.db.MealDataBase
import com.example.foodapplication.viewmodel.HomeViewModel
import com.example.foodapplication.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: HomeViewModel by lazy {
        val mealDataBase = MealDataBase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDataBase)
        ViewModelProvider(this,homeViewModelProviderFactory).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val navController = findNavController(R.id.host_fragment)
        binding.bottomNav.setupWithNavController(navController)
    }
}