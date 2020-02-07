package com.ainsigne.travelappdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.ainsigne.travelappdemo.data.VenueDetails
import com.ainsigne.travelappdemo.data.VenueDetailsRepository
import com.ainsigne.travelappdemo.data.VenueFavoritesRepository
import com.ainsigne.travelappdemo.interfaces.DetailRepository
import com.ainsigne.travelappdemo.interfaces.FaveRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [VenueDetailsFragment].
 */
class VenueDetailsViewModel (
    repo: DetailRepository,
    var venueAndFavoritesRepository: FaveRepository,
    var detailId: String
) : ViewModel() {

    val isExisting: LiveData<Boolean> = repo.getVenueDetail(detailId).map { it == null }
    val venueDetailWithId : LiveData<VenueDetails> = repo.getVenueDetail(detailId)
    val venueDetails : LiveData<List<VenueDetails>> = repo.getVenueDetails()

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun addVenueToFave() {
        viewModelScope.launch {
            venueAndFavoritesRepository.createVenueFavorites(detailId)
        }
    }
}
