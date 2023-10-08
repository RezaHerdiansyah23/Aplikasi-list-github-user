package com.example.aplikasigithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.database.FavoriteEntry
import com.example.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.example.aplikasigithubuser.model.user
import com.example.aplikasigithubuser.ui.adapter.UserAdapter
import com.example.aplikasigithubuser.ui.viewModel.FavoriteViewModel
import com.example.aplikasigithubuser.utils.LoadingHelper

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        setupAdapter()
        setupItemClickCallback()
    }

    private fun setupUI() {
        binding.rvRow.setHasFixedSize(true)
        binding.rvRow.layoutManager = LinearLayoutManager(this@FavoriteActivity)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getFavoriteUser()?.observe(this, { favoriteEntries ->
            if (favoriteEntries != null) {
                val userList = mapFavoriteEntriesToUserList(favoriteEntries)
                adapter.setList(userList)
            }
        })
    }

    private fun setupAdapter() {
        adapter = UserAdapter()
        binding.rvRow.adapter = adapter
    }

    private fun setupItemClickCallback() {
        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                navigateToDetailActivity(data)
            }
        })
    }

    private fun navigateToDetailActivity(user: user) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            putExtra(DetailActivity.EXTRA_ID, user.id)
            putExtra(DetailActivity.EXTRA_URL, user.avatar_url)
        }
        startActivity(intent)
    }

    private fun mapFavoriteEntriesToUserList(favoriteEntries: List<FavoriteEntry>): ArrayList<user> {
        val listUsers = ArrayList<user>()

        for (entry in favoriteEntries) {
            val userMapped = user(
                entry.login,
                entry.id,
                entry.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}
