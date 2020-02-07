package com.ainsigne.travelappdemo.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.internal.LinkedTreeMap


@Entity(tableName = "venue_items")
data class VenueItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "venueItemId")
    val venueItemId : Int,
    @field:Embedded(prefix = "venue_")
    val venue : Venue? = null

)

@Entity(tableName = "travel_locations")
data class TravelLocations(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "locationId")
    var locationId : Int = -1,
    var lat : Double = 0.0,
    var lon : Double = 0.0
)


data class Venue(val id : String, val name : String,
                 val categories : ArrayList<HashMap<String, Any>>? = null){
    var fakeUrl : String? = ""
    fun url() : String?{
        if(!categories.isNullOrEmpty()){
            val icon = categories.get(0)["icon"] as LinkedTreeMap<String,String>
            return "${icon["prefix"]}64${icon["suffix"]}"
        }
        return fakeUrl
    }
}

data class VenueCategories(
    val name : String,
    val pluralName : String,
    val shortName : String,
    @field:Embedded(prefix = "venueicon_")
    val icon : VenueIcon? = null
)

data class VenueIcon(
    val prefix : String,
    val suffix : String
)

