package com.example.underthesea_aos.calendar_record

import android.content.Context
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.example.underthesea_aos.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class EventDecorator(context: Context, date: CalendarDay): DayViewDecorator {
    var date: CalendarDay = date
    val drawable = context.getDrawable(R.drawable.background_border_top_blue)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }

    override fun decorate(view: DayViewFacade?) {
        //점찍기
        //view?.addSpan(DotSpan(8F, Color.parseColor("#C0E8FE")))

        //오늘 날짜 색상
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#000000")))

        //선택된 날짜에 대한 커스텀
        if (drawable != null) {
            view?.setSelectionDrawable(drawable)
        }
    }
}