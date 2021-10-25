package com.xtanion.splashwalls.fragments

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.xtanion.splashwalls.R
import com.xtanion.splashwalls.databinding.FragmentPreviewBinding
import java.io.IOException

class PreviewFragment : Fragment() {

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val args: PreviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        // Inflate the layout for this fragment
        _binding = FragmentPreviewBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {100
        Log.d("PreviewCreated","successful")
        var bmp: Bitmap? = null

        activity?.findViewById<BottomNavigationView>(R.id.global_appbar)?.visibility = View.GONE
        if (args.imageData !=null){
            args.imageData.apply {
                val fullUrl = this!!.urls.full
                val lowUrl = this.urls.small
                val rawUrl = this.urls.raw
                val color = this.color
                val colorDrawable = ColorDrawable(Color.parseColor(color))

                //Thumbnail aka low quality image
                val thumbnailRequest = Glide.with(requireContext())
                    .load(lowUrl)
                    .fitCenter()
                Glide.with(requireContext())
                    .load(fullUrl)
                    .placeholder(colorDrawable)
                    .thumbnail(thumbnailRequest)
                    .transition(DrawableTransitionOptions.withCrossFade(1000))
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                        ): Boolean {
                            binding.loadingLottie.visibility = View.GONE
                            binding.bottomBarItems.visibility = View.VISIBLE
                            bmp = resource?.toBitmap()
                            return false
                        }
                    })
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL)
                    .into(binding.touchImage)

                binding.infoText.text = "Photo by ${this.userName} | Unsplash.com"
            }

            binding.setWallpaper.setOnClickListener {
                val wallManager = WallpaperManager.getInstance(requireContext())
                if (bmp!=null) {
                    try{
                        Snackbar.make(view,"Applying Wallpaper",Snackbar.LENGTH_SHORT).show()
                        wallManager.setBitmap(bmp)
                        Snackbar.make(view,"Wallpaper Applied",Snackbar.LENGTH_SHORT).show()
                    }catch (e:IOException){
                        Snackbar.make(view,"Failed to set",Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.findViewById<BottomNavigationView>(R.id.global_appbar)?.visibility = View.VISIBLE
        _binding = null
        requireActivity().window.decorView.systemUiVisibility = View.VISIBLE
//        val outValue = TypedValue()
//        requireContext().theme.resolveAttribute(R.attr.colorPrimaryVariant,outValue,true)
//        requireActivity().window.statusBarColor = requireContext().getColor(outValue.data)
    }
}