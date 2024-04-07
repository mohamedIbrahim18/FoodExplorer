package com.example.foodapplication.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import com.example.foodapplication.R
import com.example.foodapplication.activity.CategoryMealActivity
import com.example.foodapplication.activity.MainActivity
import com.example.foodapplication.activity.MealActivity
import com.example.foodapplication.adapter.AllCategoriesAdapter
import com.example.foodapplication.adapter.CategoryMealAdapter
import com.example.foodapplication.databinding.FragmentCategoriesBinding
import com.example.foodapplication.databinding.FragmentFavoritesBinding
import com.example.foodapplication.model.CategoriesItem
import com.example.foodapplication.viewmodel.HomeViewModel

class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoryAdapter : AllCategoriesAdapter
    private lateinit var listOfCategories : List<CategoriesItem>

    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOfCategories = emptyList()
        prepareRecyclerView()
        observeCategories()
        onClickOnCategory()
    }

    private fun onClickOnCategory() {
        categoryAdapter.onItemClickedListener = object :AllCategoriesAdapter.OnItemClickListner{
            override fun onItemClick(position: Int, categoryItem: CategoriesItem) {
                val intent = Intent(activity, CategoryMealActivity::class.java)
                intent.putExtra("CATEGORY_MEAl_NAME", categoryItem.strCategory)
                startActivity(intent)
            }

        }
    }

    private fun observeCategories() {
        viewModel.allCategories.observe(viewLifecycleOwner){
            if (it != null) {
                categoryAdapter.setAllCategories(it)
            }
        }
    }

    private fun prepareRecyclerView() {
        categoryAdapter = AllCategoriesAdapter(listOfCategories)
        binding.recCategory.apply {
            layoutManager = GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter = categoryAdapter
        }
    }

}