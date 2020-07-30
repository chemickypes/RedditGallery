package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.adapter.GalleyPageAdapter
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewPhotoModel
import kotlinx.android.synthetic.main.fragment_view_photo.*


class ViewPhotoFragment : Fragment(R.layout.fragment_view_photo) {

    private val galleryPageAdapter: GalleyPageAdapter = GalleyPageAdapter()
    private val galleryViewModel: GalleryViewPhotoModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager?.adapter = galleryPageAdapter

        galleryViewModel.images.observe(viewLifecycleOwner, Observer { list ->
           galleryPageAdapter.addItems(list)
        })
    }

    override fun onResume() {
        super.onResume()
        galleryViewModel.getImages()
    }


}