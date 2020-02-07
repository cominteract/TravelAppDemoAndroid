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
    val location: HashMap<String,Any>? = null,

    val likes : HashMap<String,Any>? = null,

    val bestPhoto : HashMap<String,Any>? = null,

    val contact: HashMap<String,Any>? = null,
    val headerLocation: String? = null
) {



    override fun toString() = name

    var fakeUrl : String? = ""

    fun url() : String? {
        if(bestPhoto != null){
            val width = "${bestPhoto?.get("width")}".toDouble().toInt()
            val height = "${bestPhoto?.get("height")}".toDouble().toInt()

            return "${bestPhoto.get("prefix")}${width}x${height}${bestPhoto.get("suffix")}"
        }
        return fakeUrl
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


