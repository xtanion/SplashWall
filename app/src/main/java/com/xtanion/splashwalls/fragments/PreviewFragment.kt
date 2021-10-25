package com.xtanion.splashwalls.fragments

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.animation.content.Content
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
import com.xtanion.splashwalls.downloads.downloadWall
import java.io.IOException
import java.util.jar.Manifest

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
                val downloadUrl = this.links.download
                val color = this.color
                val colorDrawable = ColorDrawable(Color.parseColor(color))

                //Thumbnail aka low quality image
                val thumbnailRequest = Glide.with(requireContext())
                    .load(lowUrl)
                    .fitCenter()
                try {

                    Glide.with(requireContext())
                        .load(fullUrl)
                        .placeholder(colorDrawable)
                        .thumbnail(thumbnailRequest)
                        .transition(DrawableTransitionOptions.withCrossFade(1000))
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
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
                } catch (e: IOException) {
                    binding.loadingLottie.visibility = View.GONE
                    binding.bottomBarItems.visibility = View.VISIBLE
                    Toast.makeText(context, "Image too big!", Toast.LENGTH_LONG).show()
                }
                binding.infoText.text = "Photo by ${this.userName} | Unsplash.com"

                binding.setWallpaper.setOnClickListener {
                    val wallManager = WallpaperManager.getInstance(requireContext())
                    if (bmp != null) {
                        try {
                            Snackbar.make(view, "Applying Wallpaper", Snackbar.LENGTH_SHORT).show()
                            wallManager.setBitmap(bmp)
                            Snackbar.make(view, "Wallpaper Applied", Snackbar.LENGTH_SHORT).show()
                        } catch (e: IOException) {
                            Snackbar.make(view, "Failed to set", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }

                binding.downloadWallpaper.setOnClickListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        downloadWall(context,downloadUrl,this.id)
                    }else{
                        ActivityCompat.requestPermissions(requireActivity(),
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE.toString()),100
                        )
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