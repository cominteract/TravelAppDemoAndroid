package com.ainsigne.travelappdemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.ainsigne.travelappdemo.data.VenueAndFavorites
import com.ainsigne.travelappdemo.interfaces.FaveRepository

/**
 * The ViewModel for [VenueFavoritesFragment].
 */
class VenueFavoritesViewModel internal constructor(
   venueFavoritesRepository: FaveRepository
) : ViewModel() {

    val venueFavorites = venueFavoritesRepository.getVenueFavorites()

    val venueAndFavorites: LiveData<List<VenueAndFavorites>> =
            venueFavoritesRepository.getVenueAndFavorites().map { faves ->
                faves.filter { it.venueFavorites.isNotEmpty() }
            }
}