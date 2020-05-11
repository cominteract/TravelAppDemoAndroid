package com.ainsigne.travelappdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database for this app
 */
@Database(entities = [VenueFavorites::class, VenueDetails::class, VenueItem::class, TravelLocations::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    /**
     * the dao for room access to perform crud on [VenueFavorites]
     * @return [VenueFavoritesDao]
     */
    abstract fun venueFavoritesDao(): VenueFavoritesDao
    /**
     * the dao for room access to perform crud on [VenueDetails]
     * @return [VenueDetailsDao]
     */
    abstract fun venueDetailsDao(): VenueDetailsDao
    /**
     * the dao for room access to perform crud on [VenueItem]
     * @return [VenueItemDao]
     */
    abstract fun venueItemDao(): VenueItemDao

}
