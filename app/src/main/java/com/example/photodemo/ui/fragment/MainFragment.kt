package com.example.photodemo.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photodemo.R
import com.example.photodemo.databinding.FragmentMainBinding
import com.example.photodemo.ui.adapter.PhotoListAdapter
import com.example.photodemo.ui.viewmodel.MainViewModel
import com.example.photodemo.util.Status
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by sharedViewModel()
    private lateinit var photoListAdapter: PhotoListAdapter

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        fetchData()
    }

    private fun fetchData() {

        viewModel.getProgressStatus().observe(this, { state ->
            binding.pbLoading.visibility =
                if (viewModel.listIsEmpty() && state.status == Status.LOADING) View.VISIBLE else View.GONE
        })
    }

    private fun initAdapter() {

        val staggeredGridLayoutManager = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE ->
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            else -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        photoListAdapter = PhotoListAdapter(object : PhotoListAdapter.Listener {
            override fun onImageClicked(view: View, position: Int) {
                openDetail(view, position)
            }
        })

        binding.rvPhotoList.layoutManager = staggeredGridLayoutManager
        binding.rvPhotoList.adapter = photoListAdapter

        viewModel.photos.observe(this, {
            photoListAdapter.submitList(it)
        })
    }

    private fun openDetail(transitioningView: View, position: Int) {
        viewModel.selectedPos = position

        fragmentManager?.beginTransaction()
            ?.add(R.id.container, PhotoDetailFragment(), PhotoDetailFragment::class.java.simpleName)
            ?.addToBackStack(PhotoDetailFragment::class.java.simpleName)
            ?.hide(this)
            ?.commit()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            scrollToPosition()
        }
    }

    private fun scrollToPosition() {

        val layoutManager = binding.rvPhotoList.layoutManager
        val viewAtPosition = layoutManager?.findViewByPosition(viewModel.selectedPos)
        // Scroll to position if the view for the current position is null (not currently part of
        // layout manager children), or it's not completely visible.
        if (viewAtPosition == null || layoutManager
                .isViewPartiallyVisible(viewAtPosition, false, true)
        ) {
            binding.rvPhotoList.post {
                layoutManager?.scrollToPosition(viewModel.selectedPos)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}