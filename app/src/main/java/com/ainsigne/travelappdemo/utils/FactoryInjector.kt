package com.ainsigne.travelappdemo.utils

import com.ainsigne.travelappdemo.MainActivity
import com.ainsigne.travelappdemo.fake.FakeVenueDetailsRepository
import com.ainsigne.travelappdemo.fake.FakeVenueFavoritesRepository
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.viewmodels.VenueDetailsViewModelFactory
import com.ainsigne.travelappdemo.viewmodels.VenueFavoritesViewModelFactory
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
class FactoryInjector {

    lateinit var activity : MainActivity


    init {
        //database = AppDatabase.getInstance(activity.applicationContext)
    }


    private fun getFakeDetailsRepository(): FakeVenueDetailsRepository {
        return FakeVenueDetailsRepository()
    }

    private fun getFakeVenueFavoritesRepository(): FakeVenueFavoritesRepository {
        return FakeVenueFavoritesRepository()
    }

    private fun getFakeVenueItemsRepository() : FakeVenueItemsRepository {
        return FakeVenueItemsRepository()
    }

    fun provideVenueFavoritesViewModelFactory(
        ): VenueFavoritesViewModelFactory {
        // val repository = getVenueFavoritesRepository(context)
        val repository = getFakeVenueFavoritesRepository()
        return VenueFavoritesViewModelFactory(repository)
    }

    fun provideVenueItemsViewModelFactory(): VenueItemsViewModelFactory {
        val repository = getFakeVenueItemsRepository()
        // val repository = getVenueItemsRepository(context)

        return VenueItemsViewModelFactory(repository)
    }

    fun provideVenueDetailsViewModelFactory(detailId: String
    ): VenueDetailsViewModelFactory {
        val repository = getFakeDetailsRepository()
        // val repository = getDetailsRepository(context)

        // val faveRepository = getVenueFavoritesRepository(context)
        val faveRepository = getFakeVenueFavoritesRepository()
        return VenueDetailsViewModelFactory(
                repository, faveRepository
                , detailId)
    }
}
