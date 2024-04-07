package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapplication.databinding.MealItemBinding
import com.example.foodapplication.model.PopularMealsItem

class CategoryMealAdapter(var mealsList: List<PopularMealsItem?>?) :
    RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {


    class CategoryMealViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    fun setTheMeals(mealsList: List<PopularMealsItem?>?) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        val view = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mealsList?.size?:0
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList?.get(position)?.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = mealsList?.get(position)?.strMeal
        holder.binding.imgMeal.setOnClickListener {
            onItemClickedListener?.onItemClick(position,mealsList?.get(position)!!)
        }
    }
    var onItemClickedListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(position: Int,meal:PopularMealsItem)
    }
}