package com.ainsigne.travelappdemo.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.ainsigne.travelappdemo.data.VenueAndFavorites
import java.text.SimpleDateFormat
import java.util.Locale

class VenueAndFavoritesViewModel(venueAndFaves: VenueAndFavorites) : ViewModel() {

    private val details = checkNotNull(venueAndFaves.details)
    private val venueFavorites = venueAndFaves.venueFavorites
    private val dateFormat by lazy { SimpleDateFormat("MMM d, yyyy", Locale.US) }

    val imageUrl = ObservableField<String>(details.url())
    val venueName = ObservableField<String>(details.name)

}