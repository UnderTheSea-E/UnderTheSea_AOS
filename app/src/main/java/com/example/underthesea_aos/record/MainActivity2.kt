package com.example.underthesea_aos.record

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.plan.GetPlanRes
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_recoed2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
    날짜에 해당하는 모든 기록 조회
 */
class MainActivity2 : AppCompatActivity() {
    var strDate = ""
    var result = { }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recoed2)

        //back 버튼 클릭 시
        val intent1 = Intent(this, com.example.underthesea_aos.calendar_record.MainActivity::class.java)
        back.setOnClickListener{ startActivity(intent1) }

        //add 버튼 클릭 -> 새 기록 작성 페이지로 이동
        val intent2 = Intent(this, MainActivity::class.java)
        btn_add.setOnClickListener {
            startActivity(intent2)
        }

        //캘린더로부터 날짜 받아올 인텐트
        if(intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            intent2.putExtra("date", strDate)
        }

        //백엔드와의 통신 성공 or 실패 (날짜에 해당하는 plan 받아오기)
        fun GetPlans(planDate: String){
            val call = RetrofitBuilder().retrofit().getPlansResponse(planDate)
            //비동기 방식의 통신
            call.enqueue(object : Callback<BaseResponse<GetPlanRes>> {
                //통신 성공
                override fun onResponse(call: Call<BaseResponse<GetPlanRes>>, response: Response<BaseResponse<GetPlanRes>>) {
                    //응답 성공
                    if(response.isSuccessful()){
                        //Log.d("Response2: ", Gson().toJson(response.body()))
                    }
                    //응답 실패
                    else{
                        Log.d("Response: ", "failure")
                    }
                }
                //통신 실패
                override fun onFailure(call: Call<BaseResponse<GetPlanRes>>, t: Throwable) {
                    Log.d("Connection Failure", t.localizedMessage)
                }
            })
        }

        //날짜에 해당하는 모든 기록 받기
        GetPlans(strDate)
    }
}