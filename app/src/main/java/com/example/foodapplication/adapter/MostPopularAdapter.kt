package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapplication.databinding.PopularItemBinding
import com.example.foodapplication.model.PopularMealsItem

class MostPopularAdapter(private var mealList : List<PopularMealsItem?>) : RecyclerView.Adapter<MostPopularAdapter.PoupularMealViewHolder>() {


    class PoupularMealViewHolder(val binding : PopularItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    fun setMeals(mealList: List<PopularMealsItem?>){
        this.mealList = mealList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoupularMealViewHolder {
        val viewBinding = PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PoupularMealViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PoupularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList.get(position)?.strMealThumb)
            .into(holder.binding.imgPopularMealItem)
        holder.binding.imgPopularMealItem.setOnClickListener {
            onItemClickListner?.onItemClick(position, mealList[position]!!)
        }
    }
    var onItemClickListner: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onItemClick(position: Int,meal:PopularMealsItem)
    }
}