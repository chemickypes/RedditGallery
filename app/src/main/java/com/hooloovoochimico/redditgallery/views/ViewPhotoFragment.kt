package com.hooloovoochimico.redditgallery.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.audiofx.BassBoost
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.adapter.GalleyPageAdapter
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewPhotoModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_view_photo.*
import java.lang.Exception


class ViewPhotoFragment : Fragment(R.layout.fragment_view_photo) {

    //
    private var imageUrl: String = ""
    private var imageName: String = ""


    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                saveImage(imageUrl, imageName)
            }
        }

    private val galleryPageAdapter: GalleyPageAdapter = GalleyPageAdapter { url, name ->
        imageUrl = url ?: ""
        imageName = name ?: ""
        permission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }
    private val galleryViewModel: GalleryViewPhotoModel by viewModels()

    private var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager?.adapter = galleryPageAdapter

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
               index = position
            }

        })

        index = arguments?.getInt("position") ?: 0

        galleryViewModel.images.observe(viewLifecycleOwner, Observer { list ->
            galleryPageAdapter.addItems(list)
        })

        galleryViewModel.imageSaved.observe(viewLifecycleOwner, Observer { result ->
            Toast.makeText(requireContext(),result, Toast.LENGTH_SHORT).show()
        })

        galleryViewModel.getImages()


    }

    override fun onResume() {
        super.onResume()
        viewPager.setCurrentItem(index, false)
    }

    private fun saveImage(url: String?, name: String?) {
        Picasso.get()
            .load(url)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Toast.makeText(requireContext(), "erro download", Toast.LENGTH_SHORT).show()
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    galleryViewModel.savePic(requireContext(), bitmap, name)
                }

            })
    }


}