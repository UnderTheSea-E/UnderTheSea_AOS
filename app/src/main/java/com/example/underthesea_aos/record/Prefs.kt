package com.example.underthesea_aos.record

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefNm="mPref"
    private val prefs=context.getSharedPreferences(prefNm,MODE_PRIVATE)

    var token:String?
        get() = prefs.getString("jwt",null)
        set(value){
            prefs.edit().putString("jwt", value).apply()
        }
}