package com.example.underthesea_aos.character

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.example.underthesea_aos.user.UserResponse
import kotlinx.android.synthetic.main.activity_charac2.*
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.charac_img
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {
    private val imageList = mutableListOf<Int>().apply {
        add(R.drawable.turtle1)
        add(R.drawable.seal1)
        add(R.drawable.penguin1)
        add(R.drawable.dolphin1)
    }

    var character_name = ""
    var character_num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charac2)

        //캐릭터 식별자 불러오기
        GetUser()

        btn_confirm.setOnClickListener{
            character_name = charac_name2.text.toString()
            val characInfo = CharacterInfo()
            characInfo.character_id = character_num.toLong()
            characInfo.character_name = character_name
            PutCharacter(characInfo)
            //화면 전환
            val intent1 = Intent(this, com.example.underthesea_aos.main.MainActivity::class.java)
            startActivity(intent1)
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
                    character_num = characNum
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

    //캐릭터 정보 update
    fun PutCharacter(character: CharacterInfo) {
        val call = RetrofitBuilder().retrofit().putCharacterResponse(character)
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<Long>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<Long>>,
                response: Response<BaseResponse<Long>>
            ) {
                //응답 성공
                if (response.isSuccessful()) {
                    Log.d("Response: ", response.body().toString())
                }
                //응답 실패
                else {
                    Log.d("Response: ", "failure")
                }
            }

            //통신 실패
            override fun onFailure(call: Call<BaseResponse<Long>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }
}