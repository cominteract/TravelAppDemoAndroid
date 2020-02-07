package com.ainsigne.travelappdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "venue_favorites",
    foreignKeys = [ForeignKey(entity = VenueDetails::class, parentColumns = ["id"], childColumns = ["favedetail_id"])],
    indices = [Index("favedetail_id")]
)
data class VenueFavorites   (
    @ColumnInfo(name = "favedetail_id") val favedetailId: String

) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var venueFavoriteId: Long = 0
}