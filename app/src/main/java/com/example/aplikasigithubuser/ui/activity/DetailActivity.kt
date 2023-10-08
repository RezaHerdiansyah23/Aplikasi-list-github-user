package com.example.aplikasigithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.database.FavoriteEntry
import com.example.aplikasigithubuser.databinding.ActivityDetailBinding
import com.example.aplikasigithubuser.model.DetailResponse
import com.example.aplikasigithubuser.ui.adapter.SectionPagerAdapter
import com.example.aplikasigithubuser.ui.viewModel.DetailViewModel
import com.example.aplikasigithubuser.utils.LoadingHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val bundle = Bundle().apply {
            putString(EXTRA_USERNAME, username)
        }




        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this) { userDetail ->
            userDetail?.let {
                updateUI(it)
            }
        }

        setupViewPager(bundle)

        val fab: FloatingActionButton = binding.fab
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    isFavorite = count > 0
                    if (isFavorite) {
                        fab.setImageResource(R.drawable.baseline_favorite_24)
                    } else {
                        fab.setImageResource(R.drawable.ic_border_f)
                    }
                    binding.fab.isActivated = isFavorite
                }
            }
        }

        binding.fab.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                username?.let { it1 -> avatarUrl?.let { it2 ->
                    viewModel.addToFavorite(it1, id, it2)
                } }
                fab.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                viewModel.removeFromFavorite(id)
                fab.setImageResource(R.drawable.ic_border_f)
            }
            binding.fab.isActivated = isFavorite
        }

        binding.topAppBar.inflateMenu(R.menu.options_menu)
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
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

    private fun updateUI(userDetail: DetailResponse) {
        binding.apply {
            tvName.text = userDetail.name
            tvUsername.text = userDetail.login
            tvFollower.text = "${userDetail.followers} Pengikut"
            tvFollowing.text = "${userDetail.following} Mengikuti"
            Glide.with(this@DetailActivity)
                .load(userDetail.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(ivProfile)
            LoadingHelper.showLoading(binding.root, binding.progressBar, false)
        }
    }

    private fun setupViewPager(bundle: Bundle) {
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }
}
