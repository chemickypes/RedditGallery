package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val galleryViewModel: GalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryViewModel.images.observe(viewLifecycleOwner, Observer {

            text.text = it.foldRight("") {acc, item ->
                "$acc $item"
            }
        })
    }


}