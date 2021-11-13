package com.example.dailynews.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailynews.CategoryRVModal
import com.example.dailynews.R
import com.squareup.picasso.Picasso

class CategoryRVAdapter(
    private var CategoryArrayList: ArrayList<CategoryRVModal>,
    private var context: Context
) : RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.categories_rv_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryRVModal: CategoryRVModal = CategoryArrayList[position]
        holder.categoryTV.text = categoryRVModal.category
        Picasso.get().load(categoryRVModal.categoryImageUrl).into(holder.categoryIV)
    }

    override fun getItemCount(): Int {
        return CategoryArrayList.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTV: TextView = itemView.findViewById(R.id.idTVCategory)
        val categoryIV: ImageView = itemView.findViewById(R.id.idIVCategory)
    }

}