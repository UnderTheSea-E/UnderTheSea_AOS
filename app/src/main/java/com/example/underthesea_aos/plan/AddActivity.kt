package com.example.underthesea_aos.plan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.underthesea_aos.R
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_recyclerview.*
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.databinding.ActivityPlanAddBinding
import com.example.underthesea_aos.databinding.ActivityPlanPreviewRecyclerviewBinding
import com.example.underthesea_aos.recyclerview.HorizontalItemDecorator
import com.example.underthesea_aos.recyclerview.VeritcalItemDecorator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.prefs.Preferences

/*
     계획 상세 페이지
 */

class AddActivity : AppCompatActivity() {
    lateinit var planAdapter: PlanAdapter
    private val dataSet = mutableListOf<RecommendationData>()
    lateinit var binding : ActivityPlanAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_add)
        showDate()
        initRecycler()

        save_button.setOnClickListener{
            val memo = contents_memo.text.toString()
            MyApplication.preferences.setString("content",memo)

            val intent3 = Intent(this, MainActivity::class.java)
            startActivity(intent3)
            finish()
            Toast.makeText(this, "저장이 완료되었습니다", Toast.LENGTH_SHORT).show()
        }

        //뒤로 가기 버튼
        back_btn.setOnClickListener{
            val intent1 = Intent(this, MainActivity::class.java)
            startActivity(intent1)
        }

        //cancel 버튼
        cancel_button.setOnClickListener{
            val intent2 = Intent(this,MainActivity::class.java)
            startActivity(intent2)
        }
    }


    private fun showDate(){
        val date = Date()
        val formatType = SimpleDateFormat("yyyy-MM-dd")
        dateTextView.text = formatType.format(date)
    }

    private fun initRecycler(){
        planAdapter = PlanAdapter(this)

        recommendation.adapter = planAdapter
        recommendation.addItemDecoration(VeritcalItemDecorator(20))
        recommendation.addItemDecoration(HorizontalItemDecorator(10))

        dataSet.apply {
            add(RecommendationData(img1 = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
            add(RecommendationData(img1 = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
            add(RecommendationData(img1 = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
        }

        planAdapter.dataSet = dataSet
        planAdapter.notifyDataSetChanged()
    }
}