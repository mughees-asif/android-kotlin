package com.mughees.flickerbrowser

import android.os.AsyncTask
import android.view.View
import java.lang.Exception

class GetFlickrJSONData(private val listener: onDataAvailable) : AsyncTask<String, Void, ArrayList<Photo>>() {

    private val TAG = "GetFlickrJSONData"

    interface onDataAvailable {
        fun onDataAvialable(data: List<Photo>)
        fun onError(exception: Exception)
    }
}