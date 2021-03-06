package com.hooloovoochimico.redditgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.androidviews.load
import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem


class GalleyPageAdapter(val onTapOnSaveAction: (String?,String?) -> Unit) : PagerAdapter() {

    private val items = mutableListOf<ImageBean>()

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
        val save : Button? = view?.findViewById(R.id.save)

        imageView?.load(item.url ?: "")

        user?.text = item.title
        desc?.text = item.description

        save?.setOnClickListener {
            onTapOnSaveAction(item.url, item.id)
        }

        container.addView(view)
        return view!!
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun addItems(itemsToAdd: List<ImageBean>){
        items.addAll(itemsToAdd)
        notifyDataSetChanged()
    }
}