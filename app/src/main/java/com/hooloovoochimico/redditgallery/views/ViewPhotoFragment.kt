package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.adapter.GalleyPageAdapter
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_view_photo.*


class ViewPhotoFragment : Fragment(R.layout.fragment_view_photo) {

    private val galleryPageAdapter: GalleyPageAdapter = GalleyPageAdapter()
    private val galleryViewModel: GalleryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager?.adapter = galleryPageAdapter

        galleryPageAdapter.addItems(galleryViewModel.images.value?: emptyList())
    }


}