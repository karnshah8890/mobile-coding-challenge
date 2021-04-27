package com.example.photodemo.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photodemo.data.model.UnsplashImage
import com.example.photodemo.databinding.RowPhotoDetailBinding
import com.example.photodemo.databinding.RowPhotoListBinding
import com.squareup.picasso.Picasso

class PhotoDetailAdapter(val photos: List<UnsplashImage>) :
    RecyclerView.Adapter<PhotoDetailAdapter.PhotoViewHolder>() {
    private val set = ConstraintSet()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoDetailAdapter.PhotoViewHolder {
        val binding =
            RowPhotoDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        if (photo.color != null)
            holder.binding.ivPhoto.setBackgroundColor(Color.parseColor(photo.color))
        Picasso.get().run { load(photo.urls.small).into(holder.binding.ivPhoto) }
        holder.binding.tvDesc.text = photo.description ?: photo.altDescription
        holder.binding.tvLikes.text = photo.likes.toString()
    }

    inner class PhotoViewHolder(val binding: RowPhotoDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return photos.size
    }

}