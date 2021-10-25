package com.xtanion.animewalls.fragments

import android.app.WallpaperManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import coil.Coil
import coil.load
import coil.transform.BlurTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.xtanion.animewalls.R
import com.xtanion.animewalls.databinding.FragmentPreviewBinding
import org.xml.sax.Attributes
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