package com.example.underthesea_aos.calendar2;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.underthesea_aos.R;

public class DayViewHolder extends RecyclerView.ViewHolder {// 요일 입 ViewHolder

    public TextView dayText;
    public TextView timeText;
    public LinearLayout mainLL;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);


        dayText = itemView.findViewById(R.id.dayText);
        timeText = itemView.findViewById(R.id.timeText);
        mainLL = itemView.findViewById(R.id.mainLL);
    }


}
