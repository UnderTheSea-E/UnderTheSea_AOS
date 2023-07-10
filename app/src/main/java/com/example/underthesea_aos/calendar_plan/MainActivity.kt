package com.example.underthesea_aos.calendar_plan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.plan.MainActivity
import com.example.underthesea_aos.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.charac
import kotlinx.android.synthetic.main.activity_main2.plan
import kotlinx.android.synthetic.main.activity_main2.record
import java.text.SimpleDateFormat
/*달력 출력 페이지*/
class MainActivity : AppCompatActivity() {
    lateinit var calendar: MaterialCalendarView
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //plan calendar로 이동
        val intent1 = Intent(this, com.example.underthesea_aos.calendar_plan.MainActivity::class.java)
        plan.setOnClickListener{
            startActivity(intent1)
        }

        //record calendar로 이동
        val intent2 = Intent(this, com.example.underthesea_aos.calendar_record.MainActivity::class.java)
        record.setOnClickListener{
            startActivity(intent2)
        }

        //main(캐릭터 화면)으로 이동
        val intent3 = Intent(this, com.example.underthesea_aos.main.MainActivity::class.java)
        charac.setOnClickListener{
            startActivity(intent3)
        }

        //back 버튼 클릭 시
        val intent4 = Intent(this, com.example.underthesea_aos.main.MainActivity::class.java)
        back.setOnClickListener{ startActivity(intent4) }

        calendar = findViewById(R.id.calendar)
        calendar.setSelectedDate(CalendarDay.today())

        calendar.addDecorator(TodayDecorator(this@MainActivity))

        //날짜에 해당하는 레코드 목록 페이지로 이동할 intent
        val intent = Intent(this, com.example.underthesea_aos.plan.MainActivity::class.java)

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