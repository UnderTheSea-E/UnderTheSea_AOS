package com.example.underthesea_aos.kakaoLogIn

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.example.underthesea_aos.user.KakaoResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakaologin)

        /*
        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
         */

        //백엔드와의 통신 성공 or 실패
        fun Login(token: KakaoToken){
            val call = RetrofitBuilder().retrofit().postKakaoLoginResponse(token)
            //비동기 방식의 통신
            call.enqueue(object : Callback<BaseResponse<KakaoResponse>>{
                //통신 성공
                override fun onResponse(call: Call<BaseResponse<KakaoResponse>>, response: Response<BaseResponse<KakaoResponse>>) {
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
                override fun onFailure(call: Call<BaseResponse<KakaoResponse>>, t: Throwable) {
                    Log.d("Connection Failure", t.localizedMessage)
                }
            })
        }

        //accesstoken(, refreshtoken) 발급 과정 성공 or 실패
        val callback: (OAuthToken?, Throwable?) -> Unit = {token, error ->
            //발급 실패
            if (error != null) {
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //발급 성공
            else if (token != null) {
                //Log.d(ContentValues.TAG, "token : ${token}")

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                //val intent = Intent(this, SecondActivity::class.java)
                //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                //finish()

                val kakaoToken = KakaoToken()
                kakaoToken.accessToken = token.accessToken.toString()
                kakaoToken.refreshToken = token.refreshToken.toString()
                kakaoToken.accessExpiration = token.accessTokenExpiresAt.toString()
                kakaoToken.refreshExpiration = token.refreshTokenExpiresAt.toString()
                Login(kakaoToken)
            }
        }

        //로그인 버튼 클릭 동작
        val kakao_login_button = findViewById<ImageButton>(R.id.kakao_login_button) // 로그인 버튼
        kakao_login_button.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}