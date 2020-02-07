package com.ainsigne.travelappdemo.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ainsigne.travelappdemo.data.AppDatabase
import com.ainsigne.travelappdemo.data.VenueDetails
import com.ainsigne.travelappdemo.data.VenueExploreData
import com.ainsigne.travelappdemo.data.VenueResponse
import com.ainsigne.travelappdemo.utils.VENUES_FILE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class VenueDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { VenueDatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result = coroutineScope {

        try {
            applicationContext.assets.open(VENUES_FILE).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val venueType = object : TypeToken<VenueExploreData>() {}.type
                    val venueData: VenueExploreData = Gson().fromJson(jsonReader, venueType)
                    Result.success()
//                    val database = AppDatabase.getInstance(applicationContext)
//                    if(venueData.response.groups.isNotEmpty()){
//                        venueData.response.groups[0].items?.let {items ->
//                            database.venueItemDao().insertAll(items.toList())
//                        }
//
//                        Result.success()
//                    }else Result.failure()

                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error venue database", ex)
            Result.failure()
        }
    }
}