package com.example.underthesea_aos.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FoodHelper (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
    ): SQLiteOpenHelper(context, name, factory, version){
        lateinit var lats: ArrayList<String>
        lateinit var lngs: ArrayList<String>
        lateinit var names: Array<String>
        lateinit var contents: Array<String>
        lateinit var homepages: Array<String>

    override fun onConfigure(db: SQLiteDatabase) {
            super.onConfigure(db)
            db.disableWriteAheadLogging()
        }

        //Place table 생성
        override fun onCreate(db: SQLiteDatabase) {
            var sql: String = "CREATE TABLE if not exists Food(" +
                    "food_id integer primary key autoincrement," +
                    "latitude text not null, " +
                    "longitude text not null, " +
                    "name text not null, " +
                    "content text, " +
                    "homepage blob)"

            db.execSQL(sql)
        }

        //새로운 버전의 Place table로 다시 생성할 때
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val sql: String = "DROP TABLE if exists Food"
            db.execSQL(sql)
            onCreate(db)
        }

        //만들어진 Place table에 column에 맞춰서 데이터 insert
        fun insertFood(){
            //food 마커 데이터 (latitude, longitude, name)
            lats = arrayListOf("37.6073519", "37.476251", "37.6699738", "35.1564263", "37.2736649", "60.1633002", "40.6917328")
            lngs = arrayListOf("126.9333319", "127.0377763", "126.7854227", "129.1185915", "127.0725465", "24.93828", "-73.9714244")
            names = arrayOf("카페 쓸 cafe ssssl", "홀썸 Wholesome", "제로웨이스트 차차", "러브얼스", "레트로33", "Ravintola Nolla", "Rhodora Wine Bar")
            contents = arrayOf("일회용없는 제로웨이스트를 지향하는 비건카페 쓸입니다. 포장하실 경우 텀블러 및 개인용기를 지참해주세요.",
                "비건 음식점으로 제로 웨이스트 프로젝트를 진행합니다. 홀썸 쇼퍼로서 매장 방문 시 다회용기를 챙겨오시면 다양한 혜택이 제공됩니다.",
                "차차는 '서두르지 않고 천천히' 라는 순우리말의 뜻을 가진 제로 웨이스트를 지향하는 카페 겸 제로웨이스트샵입니다. 깊고 신선한 원두를 사용하여 커피를 내리며 일회용품을 대체할 수 있는 다양한 다회용품, 친환경 소품을 판매하고 있습니다. 테이크아웃 용품은 180일 이내 생분해되는 용품을 사용하며 다회용품 지참 시 음료는 1500원 할인해드리고 있으니 '용기' 주시는 건 언제든지 환영이에요.",
                "비건 음식점으로 다회용기 지참시에만 포장이 가능합니다.",
                "동물성 콜레스테롤 0% 모든 재료는 100% 식물성 재료로 만들어집니다.(all menus are VEGAN) 직접 만드는 후무스, 깻잎 페스토, 피클, 등으로 가정식 느낌을 물씬 느끼실 수 있습니다. 환경을 위해 티슈와 빨대가 제공되지 않으니 실내 세면대를 이용해야 합니다.",
                "스칸디나비아의 첫 제로웨이스트 레스토랑입니다. 주방에 쓰레기통이 없습니다. 자체 소프트웨어로 월간 메뉴, 재료의 양을 결정합니다. 일회용 포장을 사용하는 공급자에게 식재료를 받지 않습니다.",
                "뉴욕 최초의 제로 웨이스트 와인 바입니다. 와인 안주 굴 공급 시 씻어서 반환 가능한 상자를 사용하며 포장 과정에서부터 힘썼습니다.")
            homepages = arrayOf("https://www.instagram.com/__ssssl__", "https://www.wholesomevegan.co/", "https://www.instagram.com/chacha_zip/",
            "http://www.instagram.com/love_urth/", "https://www.instagram.com/retro33_/", "http://www.restaurantnolla.com/",
            "http://rhodorabk.com/", )

            //insert할 db 준비
            val db = this.writableDatabase

            //insert할 데이터 수만큼 반복
            for(i in 0..lats.size-1) {
                //insert할 문자열 데이터
                val values = ContentValues()
                values.put("latitude", lats[i])
                values.put("longitude", lngs[i])
                values.put("name", names[i])
                values.put("content", contents[i])
                values.put("homepage", homepages[i])
                //insert data
                db.insert("Food", null, values)
            }
            db.close()
        }
    }