package com.example.foodapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapplication.R
import com.example.foodapplication.ui.activity.CategoryMealActivity
import com.example.foodapplication.ui.activity.MainActivity
import com.example.foodapplication.ui.activity.MealActivity
import com.example.foodapplication.adapter.AllCategoriesAdapter
import com.example.foodapplication.adapter.MostPopularAdapter
import com.example.foodapplication.databinding.FragmentHomeBinding
import com.example.foodapplication.model.CategoriesItem
import com.example.foodapplication.model.MealsInformation
import com.example.foodapplication.model.PopularMealsItem
import com.example.foodapplication.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    lateinit var randomMeal: MealsInformation
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoryItemAdapter: AllCategoriesAdapter
    private lateinit var mealsPopularList: MutableList<PopularMealsItem>
    private lateinit var categoryList: MutableList<CategoriesItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        //viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        categoryList = arrayListOf()
        categoryItemAdapter = AllCategoriesAdapter(categoryList)
        mealsPopularList = arrayListOf()
        popularItemsAdapter = MostPopularAdapter(mealsPopularList)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        viewModel.getRandomImage()
        subscribeToLiveData()
        onRandomMealClick()
        viewModel.getPopularMeals()
        onPopularItemClick()
        viewModel.getCategoriesMeal()
        onCategoryItemClick()
        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryItemClick() {
        categoryItemAdapter.onItemClickedListener =
            object : AllCategoriesAdapter.OnItemClickListner {
                override fun onItemClick(position: Int, categoryItem: CategoriesItem) {
                    val intent = Intent(activity, CategoryMealActivity::class.java)
                    intent.putExtra("CATEGORY_MEAl_NAME", categoryItem.strCategory)
                    startActivity(intent)
                }

            }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClickListner = object : MostPopularAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, meal: PopularMealsItem) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra("MEAL_ID", meal.idMeal)
                intent.putExtra("MEAL_NAME", meal.strMeal)
                intent.putExtra("MEAL_IMG", meal.strMealThumb)
                startActivity(intent)
            }

        }
    }

    private fun prepareRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryItemAdapter
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra("MEAL_ID", randomMeal.idMeal)
            intent.putExtra("MEAL_NAME", randomMeal.strMeal)
            intent.putExtra("MEAL_IMG", randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.randomMeal.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it?.strMealThumb)
                .into(binding.imageRandomMeal)

            this.randomMeal = it!!
        }

        viewModel.popularMeals.observe(viewLifecycleOwner) { meals ->
            meals?.let {
                popularItemsAdapter.setMeals(it)
            }
        }
        viewModel.allCategories.observe(viewLifecycleOwner) {
            it?.let {
                categoryItemAdapter.setAllCategories(it)
            }
        }
    }
}