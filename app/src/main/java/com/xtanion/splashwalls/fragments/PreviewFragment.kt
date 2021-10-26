package com.xtanion.splashwalls.fragments

import android.animation.Animator
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
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.animation.content.Content
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.xtanion.splashwalls.R
import com.xtanion.splashwalls.databinding.FragmentPreviewBinding
import com.xtanion.splashwalls.downloads.applyWallpaper
import com.xtanion.splashwalls.downloads.downloadWall
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        activity?.findViewById<MaterialToolbar>(R.id.topBar)?.visibility = View.GONE
        if (args.imageData !=null){
            args.imageData.apply {
                val fullUrl = this!!.urls.full
                val lowUrl = this.urls.small
                val regularUrl = this.urls.regular
                val downloadUrl = this.links.download
                val color = this.color
                val colorDrawable = ColorDrawable(Color.parseColor(color))

                //Thumbnail aka low quality image
                val thumbnailRequest = Glide.with(requireContext())
                    .load(regularUrl)
                    .fitCenter()
                try {

                    Glide.with(requireContext())
                        .load(fullUrl)
                        .placeholder(colorDrawable)
                        .thumbnail(thumbnailRequest)
                        .error(R.drawable.ic_window)
                        .override(1600,1600)
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
                                bmp = resource?.toBitmap()

                                binding.loadingLottie.visibility = View.GONE
                                binding.bottomBarItems.visibility = View.VISIBLE
                                binding.touchImage.apply {
                                    setZoomTransitionDuration(1)
                                    setImageBitmap(bmp)
                                }
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
                    viewLifecycleOwner.lifecycleScope.launch {
                        requireActivity().runOnUiThread {
                            Snackbar.make(view, "Applying Wallpaper", Snackbar.LENGTH_SHORT).show()
                        }
                        applyWallpaper(requireContext(),bmp,view)
                    }
                }

                binding.downloadWallpaper.setOnClickListener {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        downloadWall(context,downloadUrl,this.id)

                        binding.downloadLottie.apply {
                            binding.lottieContainer.visibility = View.VISIBLE
                            playAnimation()
                            addAnimatorListener(object : Animator.AnimatorListener{
                                override fun onAnimationStart(p0: Animator?) {
                                    TODO("Not implemented")
                                }

                                override fun onAnimationEnd(p0: Animator?) {
                                    binding.lottieContainer.visibility = View.GONE
                                }

                                override fun onAnimationCancel(p0: Animator?) {
                                    Log.d("Lottie Animation", "Animation Canceled")
                                }

                                override fun onAnimationRepeat(p0: Animator?) {
                                    cancelAnimation()
                                }
                            })
                        }
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
        Glide.with(requireContext()).clear(binding.touchImage)
        activity?.apply {
            findViewById<BottomNavigationView>(R.id.global_appbar)?.visibility = View.VISIBLE
            findViewById<MaterialToolbar>(R.id.topBar)?.visibility = View.VISIBLE
        }
        _binding = null
        requireActivity().window.decorView.systemUiVisibility = View.VISIBLE
        requireActivity().window.statusBarColor = requireContext().themeColor(R.attr.colorPrimaryVariant)
    }

    @ColorInt
    fun Context.themeColor(@AttrRes attrRes: Int): Int = TypedValue()
        .apply { theme.resolveAttribute (attrRes, this, true) }
        .data
}