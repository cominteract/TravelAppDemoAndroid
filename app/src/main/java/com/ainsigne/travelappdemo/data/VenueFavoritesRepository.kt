package com.ainsigne.travelappdemo.data

import com.ainsigne.travelappdemo.interfaces.FaveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Repository module for handling data operations for the [VenueFavorites].
 */
class VenueFavoritesRepository (
    val venueFavoritesDao: VenueFavoritesDao
) : FaveRepository {

    override fun createVenueFavorites(faveId: String) {
        CoroutineScope(IO).launch {
            val venueFavorites = VenueFavorites(favedetailId = faveId)
            venueFavoritesDao.insertVenueFavorite(venueFavorites)
        }
    }

    override fun removeVenueFavorites(venueFavorites: VenueFavorites) {
        CoroutineScope(IO).launch {
            venueFavoritesDao.deleteVenueFavorite(venueFavorites)
        }
    }


    override fun getVenueFavorites() = venueFavoritesDao.getVenueFavorites()

    override fun getVenueAndFavorites() = venueFavoritesDao.getVenueAndFavorites()

}