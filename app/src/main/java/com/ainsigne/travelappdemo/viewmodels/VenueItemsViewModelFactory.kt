package com.ainsigne.travelappdemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ainsigne.travelappdemo.data.VenueItemsRepository
import com.ainsigne.travelappdemo.interfaces.ItemRepository


/**
 * Factory for creating a [VenueItemsViewModel] with a constructor that takes a [VenueItemsRepository].
 */
class VenueItemsViewModelFactory(var repo: ItemRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = VenueItemsViewModel(repo) as T
}
