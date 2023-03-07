package com.example.underthesea_aos.calendar_plan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.R
import com.example.underthesea_aos.plan.MainActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_record.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var calendar: MaterialCalendarView
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //back 버튼 클릭 시
        //val intent1 = Intent(this, com.example.underthesea_aos.calendar.MainActivity::class.java)
        //back.setOnClickListener{ startActivity(intent1) }

        calendar = findViewById(R.id.calendar)
        calendar.setSelectedDate(CalendarDay.today())

        calendar.addDecorator(TodayDecorator(this@MainActivity))

        //날짜에 해당하는 플랜 목록 페이지로 이동할 intent
        val intent = Intent(this, MainActivity::class.java)

        calendar.setOnDateChangedListener(object: OnDateSelectedListener {
            override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
                calendar.removeDecorators()
                calendar.addDecorator(TodayDecorator(this@MainActivity))
                calendar.addDecorator(EventDecorator(this@MainActivity, date))
                //calendar -> String 변환
                val strDate = sdf.format(date.date)
                //println(strDate)
                intent.putExtra("date", strDate)
                startActivity(intent)
            }
        })
    }
}