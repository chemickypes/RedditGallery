package com.hooloovoochimico.redditgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hooloovoochimico.redditgallery.R
import com.hooloovoochimico.redditgallery.models.UnsplashImageBeanItem

class GridAdapter : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {

    private val items = mutableListOf<UnsplashImageBeanItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item,parent,false))
    }

    override fun getItemCount(): Int  = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    fun updateImages(images: List<UnsplashImageBeanItem>){
        items.clear()
        items.addAll(images)
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var imageview: ImageView? = null
        init {
            imageview = itemView.findViewById(R.id.imageView)
        }

        fun bind(item: UnsplashImageBeanItem){
            //Devo mettere le immagini
        }
    }

}