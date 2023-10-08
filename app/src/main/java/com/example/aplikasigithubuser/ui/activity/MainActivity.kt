package com.example.aplikasigithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.database.SettingPreferences
import com.example.aplikasigithubuser.database.dataStore
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.model.user
import com.example.aplikasigithubuser.ui.adapter.UserAdapter
import com.example.aplikasigithubuser.ui.viewModel.MainViewModel
import com.example.aplikasigithubuser.ui.viewModel.MainViewModelFactory
import com.example.aplikasigithubuser.utils.LoadingHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setViewModel()
        darkModeCheck()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        binding.rvRow.layoutManager = LinearLayoutManager(this)
        binding.rvRow.adapter = adapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        LoadingHelper.showLoading(binding.root, binding.progressBar, true)

        viewModel.setSearchUser("Reza")

        viewModel.getSearchUser().observe(this) { userList ->
            userList?.let {
                adapter.setList(it)
                LoadingHelper.showLoading(binding.root, binding.progressBar, false)
            }
        }

        adapter.setOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchUser()
                searchView.hide()
                false
            }
        }
        binding.searchBar.inflateMenu(R.menu.options_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite_menu -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.dark_menu ->{
                    val intent = Intent(this, ThemeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }



    private fun searchUser() {
        val query = binding.searchView.text.toString()
        LoadingHelper.showLoading(binding.root, binding.progressBar, true)
        if (query.isNotEmpty()) {
            viewModel.setSearchUser(query)
        }
    }
    private fun setViewModel(){
        val pref = SettingPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, MainViewModelFactory(pref))[MainViewModel::class.java]
    }
    private fun darkModeCheck(){
        viewModel.getThemeSettings().observe(this@MainActivity,{isDarkModeActive ->
            if (isDarkModeActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        })

    }
}
