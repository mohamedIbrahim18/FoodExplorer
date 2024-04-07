package com.example.foodapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapplication.databinding.CategoryItemBinding
import com.example.foodapplication.model.CategoriesItem

class AllCategoriesAdapter(var categoryList : List<CategoriesItem?>) : RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesViewHolder>() {

    class AllCategoriesViewHolder(val binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
    fun setAllCategories(catNewList:List<CategoriesItem?>){
        categoryList = catNewList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesViewHolder {
        val viewBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllCategoriesViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: AllCategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList.get(position)?.strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoryList.get(position)?.strCategory

        holder.binding.imgCategory.setOnClickListener {
            onItemClickedListener?.onItemClick(position,categoryList.get(position)!!)
        }
    }
    var onItemClickedListener: OnItemClickListner?=null
    interface OnItemClickListner{
        fun onItemClick(position: Int,categoryItem:CategoriesItem)
    }
}