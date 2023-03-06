package com.example.underthesea_aos.plan

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val preferences:SharedPreferences = context.getSharedPreferences("contents",Context.MODE_PRIVATE)
    //file name "contents" 에 저장

    fun getString(key: String, defValue: String):String{
        return preferences.getString(key,defValue).toString()
    }

    fun setString(key: String, defValue: String){
        preferences.edit().putString(key,defValue).apply()
    }
}