package com.xtanion.splashwalls.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtanion.splashwalls.adapter.HomeRecyclerViewAdapter
import com.xtanion.splashwalls.data.photo.Photo
import com.xtanion.splashwalls.data.photo.WallpaperData
import com.xtanion.splashwalls.databinding.FragmentHomeBinding
import com.xtanion.splashwalls.viewmodel.WallpaperViewModel

class HomeFragment : Fragment(), HomeRecyclerViewAdapter.HomeRVInterface {

    private val mViewModel: WallpaperViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var wallpaperRV: RecyclerView
    private lateinit var adapter: HomeRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = HomeRecyclerViewAdapter(this)
        wallpaperRV = binding.homeRecyclerView
        wallpaperRV.adapter = adapter
        wallpaperRV.layoutManager = GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false)
//        wallpaperRV.layoutManager = LinearLayoutManager(context)
//        wallpaperRV.addOnScrollListener(this@HomeFragment.scrollListener)
        wallpaperRV.setHasFixedSize(false)

        mViewModel.wallPagerData.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
            binding.progressBar.visibility = View.GONE
            isLoading = false
            Log.d("DataAdded","NewData")
        })
        binding.refreshLayoutHome.apply{
            setOnRefreshListener {
                isLoading = true
                this.isRefreshing = false
                Log.d("DataAdded", mViewModel.wallpaperList.value?.size.toString())
            }
        }
    }

    override fun onItemClick(data: Photo) {
        Log.d("Navigation", "Data: ${data.id}")
        val customData = WallpaperData(
            data.color,data.height,data.id,data.likes,data.updated_at,data.urls,data.links,data.user.first_name,data.user.id,data.width)
        val action = HomeFragmentDirections.actionHomeFragmentToPreviewFragment(customData)
        Navigation.findNavController(requireView()).navigate(action)
        Toast.makeText(context,data.id,Toast.LENGTH_SHORT).show()
    }


    var isScrolling = false
    var isLoading = false

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}