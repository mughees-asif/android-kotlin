package com.mughees.flickerbrowser

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class GetFlickrJSONData(private val listener: onDataAvailable) : AsyncTask<String, Void, ArrayList<Photo>>() {

    private val TAG = "GetFlickrJSONData"

    interface onDataAvailable {
        fun onDataAvialable(data: List<Photo>)
        fun onError(exception: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground starts")
        try {
            val jsonData: JSONObject(params[0])
            val itemsArray = jsonData.getJSONArray("items")
        }
    }

    override fun onPostExecute(result: ArrayList<Photo>?) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
    }
}