package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.adapter.GridAdapter
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val galleryViewModel: GalleryViewModel by viewModels()

    private val gridAdapter = GridAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryViewModel.images.observe(viewLifecycleOwner, Observer { list ->
            gridAdapter.updateImages(list)
        })

        galleryViewModel.loading.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
        })

        galleryViewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
        })

        button.setOnClickListener {
            galleryViewModel.search("soccer")
        }

        list.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            adapter = gridAdapter
        }
    }


}