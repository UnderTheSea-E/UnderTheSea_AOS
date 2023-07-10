package com.example.underthesea_aos.record

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.plan.Plan
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_record3.*
import kotlinx.android.synthetic.main.activity_record3.back
import kotlinx.android.synthetic.main.activity_record3.date
import kotlinx.android.synthetic.main.activity_record3.smile
import kotlinx.android.synthetic.main.activity_record3.txt_content
import kotlinx.android.synthetic.main.activity_record3.writing
import retrofit2.Call
import retrofit2.Response

/*
   기록 상세 view 페이지
 */
class MainActivity3 : AppCompatActivity() {
    var id = 0.toLong()
    var strDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record3)

        //back 버튼 클릭 시
        back.setOnClickListener{
            val intent1 = Intent(this, MainActivity2::class.java)
            intent1.putExtra("date",strDate)
            startActivity(intent1)
        }

        //writing 버튼 클릭 -> 기록 작성 페이지로 이동
//        val intent2 = Intent(this, MainActivity::class.java)
//        writing.setOnClickListener { startActivity(intent2) }

        //캘린더로부터 날짜 받아올 인텐트
        if(intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            date.text = strDate
        }

        //기록 정보 조회
        if (intent.hasExtra("record_id")) {
            id = intent.getLongExtra("record_id", -1)
            GetRecord(id)
        }
    }

    private fun GetRecord(record_id: Long){

        val call = RetrofitBuilder().retrofit().getRecordResponse(record_id)
        //비동기 방식의 통신
        call.enqueue(object : retrofit2.Callback<BaseResponse<RecordInfo>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<RecordInfo>>,
                response: Response<BaseResponse<RecordInfo>>
            ) {
                if(response.isSuccessful()){
                    var record = RecordInfo()
                    record = response.body()?.result!!
                    Log.d("Response1: ", response.body()!!.result?.content.toString())
                    //Log.d("Response2: ",Editable.Factory.getInstance().newEditable(response.body()!!.result!!.content).toString())
                    txt_content.text = Editable.Factory.getInstance().newEditable(record.content)
                    date.text = Editable.Factory.getInstance().newEditable(response.body()!!.result!!.date)
                    if(response.body()!!.result!!.satisfaction == 2) {
                        smile.setBackgroundResource(R.drawable.sad)
                    }
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }

            override fun onFailure(call: Call<BaseResponse<RecordInfo>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }

        })
    }
}