package com.lukaszkalnik.moovis.util

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> FragmentActivity.viewModel(crossinline provider: () -> VM): Lazy<VM> =
    viewModels {
        object : ViewModelProvider.Factory {
            override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM = provider() as VM
        }
    }

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : ViewModel> Fragment.viewModel(crossinline provider: () -> VM): Lazy<VM> =
    viewModels {
        object : ViewModelProvider.Factory {
            override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM = provider() as VM
        }
    }
