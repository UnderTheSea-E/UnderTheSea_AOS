package com.example.underthesea_aos.character

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.main.MainActivity
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.example.underthesea_aos.user.UserResponse
import kotlinx.android.synthetic.main.activity_charac.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var characNum = 0

    private val imageList = mutableListOf<Int>().apply {
        add(R.drawable.turtle1)
        add(R.drawable.seal1)
        add(R.drawable.penguin1)
        add(R.drawable.dolphin1)
    }
    lateinit var intent1: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charac)
        mainInitViewPager2()
        //캘린더로 화면 전환할 intent
        intent1 = Intent(this, MainActivity::class.java)
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
                    Log.d("Response: ", response.body()!!.result.toString())
                }
                //응답 실패
                else {
                    Log.d("Response: ", "failure")
                }
            }

            //통신 실패
            override fun onFailure(call: Call<BaseResponse<UserResponse>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    private fun mainInitViewPager2(){
        viewPager.apply {
            clipToPadding= false
            clipChildren= false
            offscreenPageLimit = 1
            adapter = ViewPager2Adepter(this@MainActivity, imageList)
        }
        viewPager.setPageTransformer(MarginPageTransformer(0))
        viewPager.setPadding(100,0,100,0)
        mainViewChangeEvent()
    }

    private fun mainViewChangeEvent(){
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                btn_confirm.setOnClickListener{
                    characNum = position;
                    val characInfo = CharacterInfo()
                    characInfo.character_id = characNum.toLong()
                    characInfo.character_name = "임시이름"
                    PutCharacter(characInfo)
                    //GetUser()
                    startActivity(intent1)
                }
            }
        })
    }
}