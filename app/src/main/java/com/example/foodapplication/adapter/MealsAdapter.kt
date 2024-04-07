package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapplication.databinding.MealItemBinding
import com.example.foodapplication.model.MealsInformation

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesMealViewHolder>() {
    class FavoritesMealViewHolder(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    private val diffUtil = object : DiffUtil.ItemCallback<MealsInformation>(){
        override fun areItemsTheSame(
            oldItem: MealsInformation,
            newItem: MealsInformation
        ): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(
            oldItem: MealsInformation,
            newItem: MealsInformation
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMealViewHolder {
        val view = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesMealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)

        holder.binding.tvMealName.text = meal.strMeal
        holder.binding.imgMeal.setOnClickListener {
            onItemClickListner?.onItemClick(position,meal!!)
        }
    }
    var onItemClickListner: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(position: Int, meal: MealsInformation)
    }
}