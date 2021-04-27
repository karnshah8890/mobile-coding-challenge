package com.example.photodemo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.photodemo.databinding.FragmentPhotoDetailBinding
import com.example.photodemo.ui.adapter.PhotoDetailAdapter
import com.example.photodemo.ui.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class PhotoDetailFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var _binding: FragmentPhotoDetailBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.vpPhotos.layoutManager = layoutManager
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.vpPhotos)
        binding.vpPhotos.adapter =
            PhotoDetailAdapter(viewModel.photos.value!!)
        binding.vpPhotos.scrollToPosition(viewModel.selectedPos)
        binding.vpPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    val centerView = snapHelper.findSnapView(layoutManager)
                    val pos: Int = layoutManager.getPosition(centerView!!)
                    viewModel.selectedPos = pos
                }

            }
        })

    }
}