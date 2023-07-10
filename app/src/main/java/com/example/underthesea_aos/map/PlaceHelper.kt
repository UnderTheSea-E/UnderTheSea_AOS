package com.example.underthesea_aos.map

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class PlaceHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
): SQLiteOpenHelper(context, name, factory, version){
    lateinit var lats: ArrayList<String>
    lateinit var lngs: ArrayList<String>
    lateinit var contents: Array<String>
    lateinit var names: Array<String>
    lateinit var homepages: Array<String>

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.disableWriteAheadLogging()
    }

    //Place table 생성
    override fun onCreate(db: SQLiteDatabase) {
        var sql: String = "CREATE TABLE if not exists Place(" +
                "place_id integer primary key autoincrement," +
                "latitude text not null, " +
                "longitude text not null, " +
                "name text not null, " +
                "content text, " +
                "homepage blob)"
        db.execSQL(sql)
    }

    //새로운 버전의 Place table로 다시 생성할 때
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE if exists Place"
        db.execSQL(sql)
        onCreate(db)
    }

    //만들어진 Place table에 column에 맞춰서 데이터 insert
    fun insertPlace(){
        //place 마커 데이터 (latitude, longitude, name)
        lats = arrayListOf("37.7941946", "37.5284304", "37.5660148", "37.5103503", "37.5206865", "37.5677554", "35.231024",
            "34.7585289", "36.8944539", "33.5434231", "33.5582327")
        lngs = arrayListOf("127.1644031", "126.9330781", "126.8765181", "126.9960378", "127.0122724", "126.8856819", "128.684556",
            "127.9759777", "126.2066731", "126.6697752", "126.759787")
        names = arrayOf("고모리호수공원", "여의도한강공원", "난지한강공원", "반포한강공원", "잠원한강공원", "하늘공원", "용지호수공원",
            "한려해상국립공원", "태안해안국립공원", "함덕해수욕장", "김녕해수욕장")
        contents = arrayOf("고모리 호수공원은 경기도 포천시에 위치한 고모리 저수지 주변에 있는 공원입니다. 이 곳은 자연과 함께 산책하며 힐링을 할 수 있는 곳으로, 둘레길을 걷거나 자전거를 타며 즐길 수 있습니다",
            "서울의 3대 도심 중 두 곳인 한양도성과 인접하고, 다른 한강공원들보다 접근성도 편리하기에 한강공원 중 방문자가 제일 많다고 합니다. 이 곳에서는 산책, 자전거 타기, 피크닉, 놀이터, 수영장, 배타기, 물놀이 등 다양한 활동을 즐길 수 있습니다",
            " 2002년 FIFA 월드컵을 기념하여 조성된 5개의 월드컵공원 중 하나입니다. 이 곳에서는 캠핑, 산책, 자전거 타기, 놀이터, 수영장, 배타기, 물놀이 등 다양한 활동을 즐길 수 있습니다.",
            "반포대교(잠수교)를 중심으로 상류는 한남대교, 하류는 동작대교 사이 강변 남단에 위치해 있고, 길이는 7.2㎞로 서초구 반포동, 동작구 흑석동에 인접해 있습니다.",
            "잠원한강공원에서는 바나나보트에서 웨이크보드까지 다양한 종류의 레포츠를 즐길 수 있습니다.",
            "전체적인 형태는 정사각형으로 이뤄져 있으며 억새 식재지·순초지·암석원·혼생초지·해바라기 식재지 등 테마별로 구성되어 있습니다1.",
            "창원시민헌장, 새영남포정사, 이원수선생노래비 등이 있으며, 이 외에도 불모산에서 발굴한 불모산동사지 3층 석탑을 원형으로 복원해 놓았으며, 음악분수가 설치되어 있습니다.",
            "한려해상국립공원은 경상남도 거제시와 전라남도 여수시에 걸쳐 있는 국립공원입니다. 1968년 우리나라에서 2번째이자 해상공원으로는 최초로 국립공원으로 지정되었습니다.",
            "태안해안국립공원은 충청남도 태안반도를 중심으로 하여 가로림만에서 안면도에 이르는 해안국립공원입니다.",
            "함덕해수욕장은 제주시에서 동쪽으로 14km 떨어져 있으며, 제주도에서 가장 아름다운 해수욕장 중 하나입니다.",
            "김녕해수욕장은 성세기 해변이라고도 불리며, 거대한 너럭바위 용암 위에 모래가 쌓여 만들어졌습니다.")
        homepages = arrayOf("https://ggtour.or.kr/tourdb/goosuk.php?tmenu=&smenu=&stitle=&tsort=2&msort=15&board_code=71&board=71&s_category_name=&s_view_yn=&key=&page=1&mode=detail&no=1919",
            "https://hangang.seoul.go.kr/www/contents/669.do?mid=473",
            "https://hangang.seoul.go.kr/www/contents/672.do?mid=478",
            "https://hangang.seoul.go.kr/www/contents/663.do?mid=463",
            "https://hangang.seoul.go.kr/www/contents/657.do?mid=454",
            "https://www.uic.or.kr/skypark/main/mainPage.do",
            "https://culture.changwon.go.kr/index.changwon?contentId=130&bbsId=BBSMSTR_000000000082&nttId=738&menuNo=11010000",
            "https://www.knps.or.kr/front/portal/visit/visitCourseMain.do?parkId=120300&menuNo=7020103",
            "https://www.knps.or.kr/front/portal/visit/visitCourseMain.do?parkId=120300&menuNo=7020103",
            "https://archive.much.go.kr/data/01/folderView.do?jobdirSeq=1726",
            "https://www.jeju.go.kr/vill/gimnyeong/index.htm")

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
            db.insert("Place", null, values)
        }
        db.close()
    }
}