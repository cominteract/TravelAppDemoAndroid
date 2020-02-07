package com.ainsigne.travelappdemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ainsigne.travelappdemo.interfaces.FaveRepository

/**
 * Factory for creating a [VenueFavoritesViewModel] with a constructor that takes a
 * [FaveRepository].
 */
class VenueFavoritesViewModelFactory(
    private val repo: FaveRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VenueFavoritesViewModel(repo) as T
    }
}