package com.ainsigne.travelappdemo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "venue_details")
data class VenueDetails(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    val name: String,
    val description: String? = null,
    //@field:Embedded(prefix = "venuelocation_")
    var location: HashMap<String,Any>? = null,

    val likes : HashMap<String,Any>? = null,

    var bestPhoto : HashMap<String,Any>? = null,

    var contact: HashMap<String,Any>? = null,
    val headerLocation: String? = null
) {



    override fun toString() = name


    fun url() : String? {
        bestPhoto?.let { best ->
            if(best.containsKey("width") && best["width"] is Double){
                val width = "${best.get("width")}".toDouble().toInt()
                val height = "${best.get("height")}".toDouble().toInt()
                return "${best.get("prefix")}${width}x${height}${best.get("suffix")}"
            }
            return best.get("suffix").toString()
        }


        return ""
    }

    fun latLng() : String?{
        if(!location.isNullOrEmpty()){
            return "${location?.get("lat").toString()},${location?.get("lng").toString()}"

        }
        return ""
    }
}


data class VenueContact(
    val twitter : String?,
    val facebook : String?,
    val facebookUsername : String?,
    val facebookName : String?
)


data class VenueLocation(
    val address : String,
    val formattedAddress : String? = null

)

data class VenueLikes(
    val count : Int,
    val summary : String
)

data class VenuePhotos(

    val prefix : String,
    val suffix : String,
    val width : Int,
    val height : Int
){
    override fun toString(): String {
        val fullUrl = "$prefix/${height}x${width}/$suffix"
        return fullUrl
    }
}


