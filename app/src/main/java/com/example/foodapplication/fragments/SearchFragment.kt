package com.example.foodapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapplication.activity.MainActivity
import com.example.foodapplication.adapter.MealsAdapter
import com.example.foodapplication.databinding.FragmentSearchBinding
import com.example.foodapplication.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: HomeViewModel
    lateinit var searchRecyclerViewAdpater: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.imgSearchMeal.setOnClickListener {
            searchMeals()
        }
        obsereveSearchLiveData()
        var searchJob: Job? = null
        binding.searchBox.addTextChangedListener { search ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(search.toString())
            }
        }

    }

    private fun obsereveSearchLiveData() {
        viewModel.searchMealLiveData.observe(viewLifecycleOwner) {
            searchRecyclerViewAdpater.differ.submitList(it)
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.searchBox.text.toString()
        Log.d("Text", searchQuery)
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerViewAdpater = MealsAdapter()
        binding.recViewSearchMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchRecyclerViewAdpater
        }
    }

}