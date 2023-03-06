package com.example.underthesea_aos.plan

import android.content.Context

class PlanPreviewData(context: Context) {
 var title : String? = null
    get(){
        return MyApplication.preferences.getString("title","")
    }
    set(value){field=value}
}

