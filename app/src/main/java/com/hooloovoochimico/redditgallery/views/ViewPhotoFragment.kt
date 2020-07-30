package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hooloovoochimico.redditgallery.R


class ViewPhotoFragment : Fragment(R.layout.fragment_view_photo) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_photo, container, false)
    }


}