package com.ainsigne.travelappdemo.utils

import android.content.Context
import com.ainsigne.travelappdemo.fake.FakeVenueDetailsRepository
import com.ainsigne.travelappdemo.fake.FakeVenueFavoritesRepository
import com.ainsigne.travelappdemo.fake.FakeVenueItemsRepository
import com.ainsigne.travelappdemo.viewmodels.VenueDetailsViewModelFactory
import com.ainsigne.travelappdemo.viewmodels.VenueFavoritesViewModelFactory
import com.ainsigne.travelappdemo.viewmodels.VenueItemsViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {
//
//    private fun getDetailsRepository(context: Context): VenueDetailsRepository {
//        return VenueDetailsRepository.getInstance(
//                AppDatabase.getInstance(context.applicationContext).venueDetailsDao())
//    }
//
//    private fun getVenueFavoritesRepository(context: Context): VenueFavoritesRepository {
//        return VenueFavoritesRepository.getInstance(
//                AppDatabase.getInstance(context.applicationContext).venueFavoritesDao())
//    }
//
//    private fun getVenueItemsRepository(context: Context): VenueItemsRepository {
//        return VenueItemsRepository.getInstance(
//            AppDatabase.getInstance(context.applicationContext).venueItemDao())
//    }

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
        context: Context
    ): VenueFavoritesViewModelFactory {
        // val repository = getVenueFavoritesRepository(context)
        val repository = getFakeVenueFavoritesRepository()
        return VenueFavoritesViewModelFactory(repository)
    }

    fun provideVenueItemsViewModelFactory(context: Context): VenueItemsViewModelFactory {
        val repository = getFakeVenueItemsRepository()
        // val repository = getVenueItemsRepository(context)

        return VenueItemsViewModelFactory(repository)
    }

    fun provideVenueDetailsViewModelFactory(
        context: Context,
        detailId: String
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
