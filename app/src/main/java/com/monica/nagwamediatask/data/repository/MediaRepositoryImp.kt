package com.monica.nagwamediatask.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.monica.nagwamediatask.data.models.MediaItem
import com.monica.nagwamediatask.domain.repository.MediaRepository
import com.monica.nagwamediatask.utils.JsonHelper.getJsonDataFromAsset

class MediaRepositoryImp : MediaRepository {
    override fun getMediaListFromJSOnFile(context: Context): ArrayList<MediaItem> {
        val jsonFileString = getJsonDataFromAsset(context, "media.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }
        val gson = Gson()
        val listPersonType = object : TypeToken<ArrayList<MediaItem>>() {}.type

        val mediaListResponse: ArrayList<MediaItem> = gson.fromJson(jsonFileString, listPersonType)
        Log.i("hello Monica", mediaListResponse.toString())
        return mediaListResponse
    }
}