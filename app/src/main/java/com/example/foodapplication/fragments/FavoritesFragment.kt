package com.example.foodapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import com.example.foodapplication.activity.MainActivity
import com.example.foodapplication.adapter.MealsAdapter
import com.example.foodapplication.databinding.FragmentFavoritesBinding
import com.example.foodapplication.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {

    lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel : HomeViewModel
    lateinit var favoritesMealAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        subscribeToLiveData()
        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            // delete
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoritesMealAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)
                Snackbar.make(binding.root,"Meal deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo"
                    ) {
                        viewModel.insertMeal(deletedMeal)
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recFavorites)
    }

    private fun prepareRecyclerView() {
        favoritesMealAdapter = MealsAdapter()
        binding.recFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesMealAdapter
        }
    }

    private fun subscribeToLiveData() {
        viewModel.favoriteMealsLiveData.observe(requireActivity()){
            favoritesMealAdapter.differ.submitList(it)

        }
    }
}