package com.example.aplikasigithubuser.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.databinding.FollowFragmentBinding
import com.example.aplikasigithubuser.ui.activity.DetailActivity
import com.example.aplikasigithubuser.ui.adapter.UserAdapter
import com.example.aplikasigithubuser.utils.LoadingHelper

class FollowersFragment : Fragment(R.layout.follow_fragment) {
    private var _binding: FollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FollowFragmentBinding.bind(view)

        adapter = UserAdapter()

        binding.rvUser.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FollowersFragment.adapter
        }

        LoadingHelper.showLoading(binding.root, binding.progressBar, true)

        viewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner) { followersList ->
            followersList?.let {
                adapter.setList(it)
                LoadingHelper.showLoading(binding.root, binding.progressBar, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
