package com.example.aplikasigithubuser.utils

import android.view.View
import android.widget.ProgressBar

object LoadingHelper {
    fun showLoading(view: View, progressBar: ProgressBar, state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}

