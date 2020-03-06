package com.ainsigne.travelappdemo.data

import android.util.Log
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
    val venue : Venue? = null,
    var latlng : String = "0.0,0.0"

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



    fun url() : String? {
        categories?.let { categorylist ->
            if(!categorylist.isNullOrEmpty()){

                var icon = categorylist[0]["icon"] as? LinkedTreeMap<*, *>?
                icon?.let {best ->
                    if(best.containsKey("prefix") && best.containsKey("suffix") && best["prefix"] != ""){
                        return "${best.get("prefix")}64${best.get("suffix")}"
                    }
                    return best["suffix"].toString()
                }
            }
        }
        return ""
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

