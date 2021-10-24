package com.xtanion.animewalls.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xtanion.animewalls.adapter.HomeRecyclerViewAdapter
import com.xtanion.animewalls.api.RetrofitInstance
import com.xtanion.animewalls.data.CustomWallData
import com.xtanion.animewalls.data.Urls
import com.xtanion.animewalls.data.WallData
import com.xtanion.animewalls.databinding.FragmentHomeBinding
import com.xtanion.animewalls.viewmodel.WallpaperViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpRetryException

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
        wallpaperRV.addOnScrollListener(this@HomeFragment.scrollListener)
        wallpaperRV.setHasFixedSize(false)

        mViewModel.wallpaperList.observe(viewLifecycleOwner, {
            adapter.notifyListChanged(it)
            binding.progressBar.visibility = View.GONE
            isLoading = false
            Log.d("DataAdded","New list size = ${it.size}")
        })
        binding.refreshLayoutHome.apply{
            setOnRefreshListener {
                isLoading = true
                mViewModel.addNewPage()
                this.isRefreshing = false
                Log.d("DataAdded", mViewModel.wallpaperList.value?.size.toString())
            }
        }
    }

    override fun onItemClick(data: WallData) {
        Log.d("Navigation", "Data: ${data.id}")
        val customData = CustomWallData(
            data.color,data.height,data.id,data.likes,data.updated_at,data.urls,data.user.first_name,data.user.id,data.width)
        val action = HomeFragmentDirections.actionHomeFragmentToPreviewFragment(customData)
        Navigation.findNavController(requireView()).navigate(action)
    }

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItem+visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItem>=0
            val isTotalMoreThanVisible = totalItemCount >= 10

            val shouldPaginate = isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && !isLoading
            if (shouldPaginate){
                isLoading = true
                mViewModel.addNewPage()
                isScrolling = false
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == recyclerView.scrollState){
                isScrolling = true
            }
        }
    }

    var isScrolling = false
    var isLoading = false

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}