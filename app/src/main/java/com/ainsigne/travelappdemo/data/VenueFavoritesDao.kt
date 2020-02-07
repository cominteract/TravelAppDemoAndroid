package com.ainsigne.travelappdemo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [VenueFavorites] class.
 */
@Dao
interface VenueFavoritesDao {
    @Query("SELECT * FROM venue_favorites")
    fun getVenueFavorites(): LiveData<List<VenueFavorites>>

    @Query("SELECT * FROM venue_favorites WHERE id = :venueFavoriteId")
    fun getVenueFavorite(venueFavoriteId: Long): LiveData<VenueFavorites>

    /**
     * This query will tell Room to query both the [VenueDetails] and [VenueFavorites] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM venue_details")
    fun getVenueAndFavorites(): LiveData<List<VenueAndFavorites>>

    @Insert
    fun insertVenueFavorite(venueFavorite: VenueFavorites): Long

    @Delete
    fun deleteVenueFavorite(venueFavorite: VenueFavorites)
}