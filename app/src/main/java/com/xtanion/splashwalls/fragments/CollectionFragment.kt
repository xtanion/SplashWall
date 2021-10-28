package com.xtanion.splashwalls.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xtanion.splashwalls.R
import com.xtanion.splashwalls.adapter.CollectionRecyclerViewAdapter
import com.xtanion.splashwalls.databinding.FragmentCollectionBinding
import com.xtanion.splashwalls.utils.categories

class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectionBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = CollectionRecyclerViewAdapter()
        val categoryRV = binding.collectionsRv
        categoryRV.adapter = adapter
        categoryRV.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        categoryRV.setHasFixedSize(true)

        adapter.notifyDataChanged(categories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}