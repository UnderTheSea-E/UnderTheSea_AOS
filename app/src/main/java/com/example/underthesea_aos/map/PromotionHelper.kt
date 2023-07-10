package com.example.underthesea_aos.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PromotionHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
    ): SQLiteOpenHelper(context, name, factory, version) {
    //lateinit var lats: ArrayList<String>
    //lateinit var lngs: ArrayList<String>
    lateinit var names: Array<String>
    lateinit var contents: Array<String>
    lateinit var homepages: Array<String>

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.disableWriteAheadLogging()
    }

    //Place table 생성
    override fun onCreate(db: SQLiteDatabase) {
        var sql: String = "CREATE TABLE if not exists Promotion(" +
                "food_id integer primary key autoincrement," +
                //"latitude text not null, " +
                //"longitude text not null, " +
                "name text not null, " +
                "content text, " +
                "homepage blob)"

        db.execSQL(sql)
    }

    //새로운 버전의 Place table로 다시 생성할 때
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists Promotion"
        db.execSQL(sql)
        onCreate(db)
    }

    fun insertPromotion() {
        //lats = arrayListOf()
        //lngs = arrayListOf()
        names = arrayOf("IBK 기업은행 일회용 컵 보증금 제도","한겨레 신문돗자리 이벤트","해양환경공단")
        contents = arrayOf("일회용 컵 보증금 제도는 전국 주요 커피 판매점. 패스트투드점 등을 대상으로 제품 가격에 일회용 컵 1개 당 300원의 자연순환 보증금을 부과하는 제도",
        "기후 위기, 탄소배출, 플라스틱 등 환경 관련 기사를 담은 돗자리.대한민국 최초로 폐플라스틱을 100% 재활용함",
        "해양오염예방 홍보영상 의견수렴 event")
        homepages = arrayOf("https://blog.ibk.co.kr/2741","https://support.hani.co.kr/introduce/event_mat.html",
        "https://www.koem.or.kr/site/koem/main.do")

        val db = this.writableDatabase

        for(i in 0..names.size-1) {
            //insert할 문자열 데이터
            val values = ContentValues()
            //values.put("latitude", lats[i])
            //values.put("longitude", lngs[i])
            values.put("name", names[i])
            values.put("content", contents[i])
            values.put("homepage", homepages[i])
            //insert data
            db.insert("Promotion", null, values)
        }
        db.close()
    }
}