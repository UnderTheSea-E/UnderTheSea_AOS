package com.example.underthesea_aos.calendar2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.underthesea_aos.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SimpleDateFormat defaultDateFormat =new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultMonthFormat=new SimpleDateFormat("yyyy-MM");
    public String selectedDate = "";

    private Date startDate = new Date();
    private Date selectedDateTime = null;
    private ImageButton beforeIB;
    private ImageButton afterIB;
    private TextView dateTV;

    public RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    private Date nowDate = new Date();//주에 오늘 날짜가 포함하는지 보기위해.
    private Calendar nowCal = Calendar.getInstance();
    private int nowMonth = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        calendarAdapter = new CalendarAdapter(this);
        calendarRecyclerView.setAdapter(calendarAdapter);
        beforeIB = findViewById(R.id.beforeIB);
        afterIB = findViewById(R.id.afterIB);
        dateTV = findViewById(R.id.dateTV);

        beforeIB.setOnClickListener(v -> {          
            setDate(-1);
        });
        afterIB.setOnClickListener(v -> {            
            setDate(1);
        });

        selectedDateTime = Calendar.getInstance().getTime();

        setCalendarList(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));

        //구분선 추가
        DividerItemDecoration dividerDecoration =
                new DividerItemDecoration(calendarRecyclerView.getContext(), new LinearLayoutManager(this).getOrientation());

        //calendarRecyclerView.addItemDecoration(dividerDecoration);

        //높이 여백 추가
        HeightDecoration decoration_height = new HeightDecoration(90);
        //calendarRecyclerView.addItemDecoration(decoration_height);
    }


    private void setDate(int addAmount) {
        Calendar calendar = Calendar.getInstance();

        if (selectedDateTime != null) {
            calendar.setTime(selectedDateTime);
        }
        calendar.add(Calendar.MONTH, addAmount);
        startDate =calendar.getTime();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);


        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), max);

        selectedDate = defaultDateFormat.format(nowDate);
        selectedDateTime = calendar.getTime();//strStartDate;

        int setMonth = calendar.get(Calendar.MONTH) + 1;
        if (setMonth == nowMonth) {
            selectedDate = defaultDateFormat.format(nowDate);
            selectedDateTime = calendar.getTime();//strStartDate;
        } else {
            Calendar dashboardDateCal = Calendar.getInstance();
            dashboardDateCal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
            selectedDate = defaultDateFormat.format(dashboardDateCal.getTime());//strStartDate;
            selectedDateTime = calendar.getTime();//strStartDate;
        }

        if (selectedDateTime == null) {
            calendar.setTime(startDate);
            selectedDateTime = startDate;
        } else {
            calendar.setTime(selectedDateTime);
        }
        setCalendarList(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));


    }


    public void setCalendarList(int year, int month) {
        try {
            dateTV.setText(defaultMonthFormat.format(selectedDateTime));

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DayModel> dayModelList = new ArrayList<>();

        try {
            GregorianCalendar calendar = new GregorianCalendar();
            GregorianCalendar preCalendar = new GregorianCalendar();

            if (year > 0 && month > -1) {
                calendar.set(GregorianCalendar.YEAR, year);
                calendar.set(GregorianCalendar.MONTH, month);
                calendar.set(GregorianCalendar.DATE, 1);

                preCalendar.set(GregorianCalendar.YEAR, year);
                preCalendar.set(GregorianCalendar.MONTH, month);
                preCalendar.set(GregorianCalendar.DATE, 1);

            } else {
                calendar.set(GregorianCalendar.YEAR, calendar.get(Calendar.YEAR));
                calendar.set(GregorianCalendar.MONTH, calendar.get(Calendar.MONTH));
                calendar.set(GregorianCalendar.DATE, 1);

                preCalendar.set(GregorianCalendar.YEAR, calendar.get(Calendar.YEAR));
                preCalendar.set(GregorianCalendar.MONTH, calendar.get(Calendar.MONTH));
                preCalendar.set(GregorianCalendar.DATE, 1);

            }

            preCalendar.add(Calendar.MONTH, -1);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; //해당 월에 시작하는 요일 -1 을 하면 빈칸을 구할 수 있겠죠 ?
            int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일


            int preMonthMax = preCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            //앞의 EMPTY 생성
            for (int j = 0; j < dayOfWeek; j++) {
                DayModel dayModel = new DayModel();
                dayModel.setType(102031);

                dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) - 1), preMonthMax - (dayOfWeek - 1 - j)));
                dayModelList.add(dayModel);

            }

            if (dayOfWeek > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date(dayModelList.get(0).getCalendarModel().getTimeInMillis());
            }

            for (int j = 1; j <= max; j++) {
                DayModel dayModel = new DayModel();
                dayModel.setType(1);
                dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                dayModelList.add(dayModel);
            }

            //뒤의 Empty
            if (dayModelList.size() / 7 > 0) {

                int extra = dayModelList.size() % 7;

                if (extra > 0) {
                    extra = 7 - extra;
                }

                for (int k = 0; k < extra; k++) {
                    DayModel dayModel = new DayModel();
                    dayModel.setType(102031);
                    dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, k + 1));
                    dayModelList.add(dayModel);
                }

                if (extra > 0) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date(dayModelList.get(dayModelList.size() - 1).getCalendarModel().getTimeInMillis());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        calendarAdapter.mCalendarList = dayModelList;
        calendarAdapter.notifyDataSetChanged();
    }
}
