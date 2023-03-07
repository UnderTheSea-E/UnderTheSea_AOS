package com.example.underthesea_aos.plan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.underthesea_aos.R
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_recyclerview.*
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.databinding.ActivityPlanAddBinding
import com.example.underthesea_aos.databinding.ActivityPlanPreviewRecyclerviewBinding
import com.example.underthesea_aos.record.PostRecordRes
import com.example.underthesea_aos.recyclerview.HorizontalItemDecorator
import com.example.underthesea_aos.recyclerview.VeritcalItemDecorator
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_plan_add.date
import kotlinx.android.synthetic.main.activity_plan_main.*
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.*
import kotlinx.android.synthetic.main.activity_record.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    var strDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_add)
        //showDate()
        initRecycler()

        if (intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            date.text = strDate
            intent.putExtra("date", strDate)
        }

        //save 저장하기 버튼
        val planInfo = Plan()
        save_button.setOnClickListener{
            PostPlan(planInfo)

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

    private fun PostPlan(plan: Plan){
        plan.title = title_plan.text.toString()
        plan.content = contents_memo.text.toString()
        plan.date = strDate
        plan.friend = 0

        val call = RetrofitBuilder().retrofit().postPlanResponse(plan)
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<Plan>> {
            //통신 성공
            override fun onResponse(call: Call<BaseResponse<Plan>>, response: Response<BaseResponse<Plan>>) {
                //응답 성공
                if(response.isSuccessful()){
                    Log.d("Response: ", response.body().toString())
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }
            //통신 실패
            override fun onFailure(call: Call<BaseResponse<Plan>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
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
