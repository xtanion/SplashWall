package com.xtanion.animewalls.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xtanion.animewalls.fragments.HomeFragment
import com.xtanion.animewalls.data.WallData
import com.xtanion.animewalls.databinding.SingleGridItemBinding

class HomeRecyclerViewAdapter(val homeRVInterface: HomeFragment):RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeRVViewHolder>() {

    private lateinit var binding: SingleGridItemBinding
    private var wallpaperList = emptyList<WallData>()
    private lateinit var context:Context

    inner class HomeRVViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                homeRVInterface.onItemClick(wallpaperList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRVViewHolder {
        binding = SingleGridItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return HomeRVViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeRVViewHolder, position: Int) {
        val currentItem:WallData = wallpaperList[position]
        val thumbUrl:String = currentItem.urls.regular
        val lowThumbUrl = currentItem.urls.small
        val color = currentItem.color
        val colorDrawable = ColorDrawable(Color.parseColor(color))
        binding.wallpaperImage.setImageDrawable(null)
        Glide.with(context).clear(binding.wallpaperImage)

//        binding.wallpaperImage.load(thumbUrl){
//            crossfade(true)
//            crossfade(500)
//            placeholder(colorDrawable)
//        }
        Glide.with(context)
            .load(thumbUrl)
            .placeholder(colorDrawable)
            .fitCenter()
            .into(binding.wallpaperImage)
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    fun notifyListChanged(newList: List<WallData>){
        val diffUtil = WallpaperDiffUtil(this.wallpaperList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.wallpaperList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface HomeRVInterface{
        fun onItemClick(data: WallData)
    }
}