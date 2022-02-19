package com.learn.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.learn.artbook.R
import com.learn.artbook.roomdb.Art
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    val  glide : RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private var ItemClick : ((String) -> Unit) ? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerDiffer = AsyncListDiffer(this,diffUtil)
    var imagess: List<String>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageViewHolder(view)
    }

    fun setOnItemClick(listener : (String) -> Unit) {

        ItemClick = listener
    }



    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.singleView)
        val url = imagess[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                ItemClick?.let {
                    it(url)
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return imagess.size
    }
}