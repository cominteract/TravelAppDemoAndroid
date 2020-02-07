package com.ainsigne.travelappdemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ainsigne.travelappdemo.interfaces.DetailRepository
import com.ainsigne.travelappdemo.interfaces.FaveRepository

/**
 * Factory for creating a [VenueDetailsViewModel] with a constructor that takes a [DetailRepository]
 * and an ID for the current [VenueDetails].
 */
class VenueDetailsViewModelFactory(
    private val repo: DetailRepository,
    private val venueFavoritesRepository: FaveRepository,
    private val detailId: String

) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VenueDetailsViewModel(repo, venueFavoritesRepository, detailId) as T
    }
}
