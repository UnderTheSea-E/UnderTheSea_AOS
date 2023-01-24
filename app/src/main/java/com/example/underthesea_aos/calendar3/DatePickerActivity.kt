package com.example.underthesea_aos.calendar3

import android.content.Intent
import android.net.Uri
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.R
import java.util.Calendar.*

class DatePickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_picker)

        val picker : DatePicker = findViewById(R.id.datePicker1) //DatePicker id
        val btnGet : Button = findViewById(R.id.button1) //Button id
        val tvw : TextView = findViewById(R.id.textView1) //TextView id

        val minDate = getInstance()
        val maxDate = getInstance()

        minDate.set(2020,1-1,1); // 보여줄 최소 날짜

        picker.setMinDate(minDate.getTime().getTime()); // 보여줄 최소 날짜 picker에 Set

        maxDate.set(2024,1-1,1); // 보여줄 최대 날짜
        picker.setMaxDate(maxDate.getTimeInMillis());  // 보여줄 최대 날짜  picker에 적용 Set

        btnGet.setOnClickListener { // 버튼 click 시 선택 된 날짜 정보 불러옴
            tvw.setText("Selected Date: "+ picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear());
        }

    }
}