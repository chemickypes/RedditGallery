package com.hooloovoochimico.redditgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.androidviews.SquareImageView
import com.hooloovoochimico.redditgallery.androidviews.load
import com.hooloovoochimico.redditgallery.models.ImageBean
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem
import com.squareup.picasso.Picasso

class GridAdapter(private val onTapListener: (ImageBean,Int)-> Unit = {_,_ -> }) : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {

    private val items = mutableListOf<ImageBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item,parent,false),onTapListener = onTapListener)
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(item = items[position],position = position)
    }

    fun updateImages(images: List<ImageBean>){
        items.clear()
        items.addAll(images)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View, private val onTapListener: (ImageBean, Int) -> Unit) : RecyclerView.ViewHolder(itemView){
        private var imageview: SquareImageView? = null
        init {
            imageview = itemView.findViewById(R.id.imageView)
        }

        fun bind(item: ImageBean, position: Int){
            imageview?.load(item.url?:"")
            imageview?.setOnClickListener {
                onTapListener.invoke(item,position)
            }

        }
    }

}

