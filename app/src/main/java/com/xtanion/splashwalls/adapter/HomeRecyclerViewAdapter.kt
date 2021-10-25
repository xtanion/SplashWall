package com.xtanion.splashwalls.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xtanion.splashwalls.fragments.HomeFragment
import com.xtanion.splashwalls.data.photo.Photo
import com.xtanion.splashwalls.databinding.SingleGridItemBinding

class HomeRecyclerViewAdapter(val homeRVInterface: HomeFragment):PagingDataAdapter<Photo,HomeRecyclerViewAdapter.HomeRVViewHolder>(
    diffCallback) {

    private lateinit var binding: SingleGridItemBinding
    private lateinit var context:Context

    inner class HomeRVViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                homeRVInterface.onItemClick(getItem(position)!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVViewHolder {
        binding = SingleGridItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return HomeRVViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeRVViewHolder, position: Int) {
//        val currentItem:WallData = wallpaperList[position]
        val currentItem: Photo? = getItem(position)
        if (currentItem!=null){

            val color = currentItem.color
            val colorDrawable = ColorDrawable(Color.parseColor(color))
            val reversedColor = getReversedColor(color)
            Glide.with(context)
                .load(currentItem.urls.regular)
                .placeholder(colorDrawable)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(colorDrawable)
                .into(binding.wallpaperImage)

            val fullName = "${currentItem.user.first_name} ${currentItem.user.last_name?:""}"
            binding.photographerName.apply {
                text = fullName
                setTextColor(reversedColor)
            }
        }else{
            Glide.with(context).clear(binding.wallpaperImage)
            binding.wallpaperImage.setImageDrawable(null)
            binding.photographerName.text = "Failed to Load"
        }
        holder.setIsRecyclable(false)
    }

    override fun onViewRecycled(holder: HomeRVViewHolder) {
        super.onViewRecycled(holder)
        binding.wallpaperImage.let {
            Glide.with(context).clear(it)
        }
    }


    interface HomeRVInterface{
        fun onItemClick(data: Photo)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Photo>(){
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

        }
    }

    private fun getReversedColor(mainColor:String):Int{
        return try {
            val color = Color.parseColor(mainColor)
            val (r, g, b) = listOf<Int>(color.red, color.green, color.blue)
            val (inR, inG, inB) = listOf<Int>(255 - r, 255 - g, 255 - b)

            Color.rgb(inR, inG, inB)
        } catch (e: Exception) {
            Color.rgb(255, 255, 255)
        }
    }
}