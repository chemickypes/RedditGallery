package com.hooloovoochimico.redditgallery.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
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


        configureSearch()

        galleryViewModel.images.observe(viewLifecycleOwner, Observer { list ->
            gridAdapter.updateImages(list)
        })

        galleryViewModel.loading.observe(viewLifecycleOwner, Observer {
           if(it) Log.d(javaClass.name, "loading")
        })

        galleryViewModel.error.observe(viewLifecycleOwner, Observer {
           if(it) {
               Log.d(javaClass.name, "error")
           }
        })

        list.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = gridAdapter
        }
    }

    private fun configureSearch() {
        val searchItem: MenuItem? = toolabr.menu.findItem(R.id.action_search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnCloseListener { true }

            val searchEditText =
                searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
            searchEditText.hint = "Eg sunset"
            val searchPlateView: View =
                searchView.findViewById(androidx.appcompat.R.id.search_plate)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText?.isNotBlank() == true || newText?.isNotEmpty() == true){
                        galleryViewModel.search(newText)
                    }

                    return true
                }

            })
        }

    }

}