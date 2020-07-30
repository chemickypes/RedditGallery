package com.hooloovoochimico.redditgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.androidviews.load
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem


class GalleyPageAdapter : PagerAdapter() {

    private val items = mutableListOf<UnsplashImageBeanItem>()

    private var layoutInflater: LayoutInflater? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(container.context)

        val view = layoutInflater?.inflate(R.layout.datail_image, container, false)

        val item = items[position]

        val imageView: ImageView? = view?.findViewById(R.id.imageView2)
        val user: TextView? = view?.findViewById(R.id.user)
        val desc: TextView? = view?.findViewById(R.id.desc)

        imageView?.load(item.urls?.regular ?: "")

        user?.text = item.user?.username
        desc?.text = item.altDescription

        container.addView(view)
        return view!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    fun addItems(itemsToAdd: List<UnsplashImageBeanItem>){
        items.addAll(itemsToAdd)
        notifyDataSetChanged()
    }
}