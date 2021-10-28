package com.xtanion.splashwalls.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xtanion.splashwalls.data.category.Category
import com.xtanion.splashwalls.databinding.CollectionsItemviewBinding
import com.xtanion.splashwalls.utils.categories

class CollectionRecyclerViewAdapter:RecyclerView.Adapter<CollectionRecyclerViewAdapter.CollectionViewHolder>() {

    private lateinit var binding: CollectionsItemviewBinding
    private lateinit var context: Context
    private val listCollection:MutableList<Category> = mutableListOf()

    class CollectionViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        binding = CollectionsItemviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context

        return CollectionViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val currentItem = listCollection[position]
        Glide.with(context)
            .load(currentItem.imageUrl)
            .into(binding.categoryImage)

        binding.collectionName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return listCollection.size
    }

    fun notifyDataChanged(list: List<Category>){
        this.listCollection.addAll(list)
        notifyDataSetChanged()
    }
}