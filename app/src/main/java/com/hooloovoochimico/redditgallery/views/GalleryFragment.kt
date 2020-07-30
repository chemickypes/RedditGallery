package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.adapter.GridAdapter
import com.hooloovoochimico.redditgallery.viewmodels.GalleryViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.util.concurrent.TimeUnit

class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val galleryViewModel: GalleryViewModel by viewModels()

    private val gridAdapter = GridAdapter { _, position ->
        findNavController().navigate(
            R.id.action_galleryFragment_to_viewPhotoFragment, bundleOf("position" to position)
        )

    }

    private val searchSubject = PublishSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        configureSearch()

        galleryViewModel.images.observe(viewLifecycleOwner, Observer { list ->
            gridAdapter.updateImages(list)
        })

        galleryViewModel.loading.observe(viewLifecycleOwner, Observer {
            loadingView.isVisible = it
            if (it){
                errorText.isVisible = false
            }
        })

        galleryViewModel.error.observe(viewLifecycleOwner, Observer {
            errorText.isVisible = it
        })

        list.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = gridAdapter
        }
    }

    private fun configureSearch() {

        val d = searchSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                galleryViewModel.search(it)
            }


        val searchItem: MenuItem? = toolabr.menu.findItem(R.id.action_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnCloseListener { true }

            val searchEditText =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchEditText.hint = "Eg sunset"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText?.isNotBlank() == true || newText?.isNotEmpty() == true) {
                        searchSubject.onNext(newText)
                    }

                    return true
                }

            })
        }

    }

}