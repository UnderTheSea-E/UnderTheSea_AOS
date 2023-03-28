package com.example.underthesea_aos.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.example.underthesea_aos.user.UserResponse
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val imageList = mutableListOf<Int>().apply {
        add(R.drawable.turtle1)
        add(R.drawable.seal1)
        add(R.drawable.penguin1)
        add(R.drawable.dolphin1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //캐릭터 불러오기
        GetUser()

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
    }

    //업데이트된 캐릭터 정보를 포함한 사용자 정보 받아오기
    fun GetUser() {
        val call = RetrofitBuilder().retrofit().getUserResponse()
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<UserResponse>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<UserResponse>>,
                response: Response<BaseResponse<UserResponse>>
            ) {
                //응답 성공
                if (response.isSuccessful()) {
                    //Log.d("Response2: ", response.body()!!.result!!.characterId.toString())
                    val characNum = response.body()!!.result!!.characterId!!.toInt()
                    Log.d("Response2: ", characNum.toString())
                    charac_img.setImageResource(imageList[characNum])
                }
                //응답 실패
                else {
                    Log.d("Response2: ", "failure")
                }
            }

            //통신 실패
            override fun onFailure(call: Call<BaseResponse<UserResponse>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }
}