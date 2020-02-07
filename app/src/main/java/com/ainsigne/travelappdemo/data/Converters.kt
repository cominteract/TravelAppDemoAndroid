package com.ainsigne.travelappdemo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }


    @TypeConverter fun categoriesToString(categories: ArrayList<VenueCategories>) = Gson().toJson(categories)

    @TypeConverter fun stringToCategories(value: String) : ArrayList<VenueCategories>{
        val venueType = object : TypeToken<ArrayList<VenueCategories>>() {}.type
        return Gson().fromJson(value,venueType)
    }

    @TypeConverter fun arrayListHashmapToString(value: ArrayList<HashMap<String,Any>>) = Gson().toJson(value)

    @TypeConverter fun stringToArrayListHashmap(value: String) : ArrayList<HashMap<String,Any>>{
        val venueType = object : TypeToken<ArrayList<HashMap<String,Any>>>() {}.type
        return Gson().fromJson(value,venueType)
    }

    @TypeConverter fun hashmapToString(value: HashMap<String,Any>) = Gson().toJson(value)

    @TypeConverter fun stringTohashmap(value: String) : HashMap<String,Any>{
        val venueType = object : TypeToken<HashMap<String,Any>>() {}.type
        return Gson().fromJson(value,venueType)
    }


    @TypeConverter fun anyToString(any : Any) = Gson().toJson(any)

    @TypeConverter fun stringToAny(value: String) : Any{
        val venueType = object : TypeToken<Any>() {}.type
        return Gson().fromJson(value,venueType)
    }

}