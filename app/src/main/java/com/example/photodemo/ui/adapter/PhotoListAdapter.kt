package com.example.photodemo.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photodemo.data.model.UnsplashImage
import com.example.photodemo.databinding.RowPhotoListBinding
import com.squareup.picasso.Picasso

class PhotoListAdapter(val listener: Listener) :
    PagedListAdapter<UnsplashImage, PhotoListAdapter.PhotoViewHolder>(
        PhotosDiffCallback
    ) {
    private val set = ConstraintSet()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            RowPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)!!
        if (photo.color != null)
            holder.binding.imgUnsplash.setBackgroundColor(Color.parseColor(photo.color))
        Picasso.get().run { load(photo.urls.small).into(holder.binding.imgUnsplash) }
        with(set) {
            // For maintaining the aspect ratio in staggered view
            val posterRatio = String.format("%d:%d", photo.width, photo.height)
            clone(holder.binding.clParent)
            setDimensionRatio(holder.binding.imgUnsplash.id, posterRatio)
            applyTo(holder.binding.clParent)
        }
        holder.binding.imgUnsplash.transitionName = photo.id
    }

    inner class PhotoViewHolder(val binding: RowPhotoListBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.imgUnsplash.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                binding.imgUnsplash -> listener.onImageClicked(binding.imgUnsplash, adapterPosition)
            }

        }

    }


    interface Listener {
        fun onImageClicked(view: View, position: Int)
    }

    companion object {
        val PhotosDiffCallback = object : DiffUtil.ItemCallback<UnsplashImage>() {
            override fun areItemsTheSame(oldItem: UnsplashImage, newItem: UnsplashImage): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashImage,
                newItem: UnsplashImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}