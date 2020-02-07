package com.ainsigne.travelappdemo.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [VenueDetails] and a user's [VenueFavorites], which is
 * used by Room to fetch the related entities.
 */
class VenueAndFavorites {

    @Embedded
    lateinit var details: VenueDetails

    @Relation(parentColumn = "id", entityColumn = "favedetail_id")
    var venueFavorites: List<VenueFavorites> = arrayListOf()
}
