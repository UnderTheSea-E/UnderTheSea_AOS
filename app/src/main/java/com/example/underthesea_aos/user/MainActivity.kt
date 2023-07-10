package com.example.underthesea_aos.user

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.kakaoLogIn.GlobalApplication
import com.example.underthesea_aos.kakaoLogIn.KakaoToken
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.activity_signin.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    var jwtToken = ""
    var refreshToken = ""
    var char = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        //백엔드와의 통신 성공 or 실패
        fun Login(token: KakaoToken){
            val call = RetrofitBuilder().retrofit().postKakaoLoginResponse(token)
            //비동기 방식의 통신
            call.enqueue(object : Callback<BaseResponse<KakaoResponse>> {
                //통신 성공
                override fun onResponse(call: Call<BaseResponse<KakaoResponse>>, response: Response<BaseResponse<KakaoResponse>>) {
                    //응답 성공
                    if(response.isSuccessful()){
                        //Log.d("Response1: ", Gson().toJson(response.body()))
                        // jwt token 저장
                        response.headers().get("Authorization").toString().split(" ")[1]
                        response.headers().get("Refresh").toString()
                        jwtToken = response.headers().get("Authorization").toString()
                        refreshToken = response.headers().get("Refresh").toString()
                        GlobalApplication.prefs.token = jwtToken
                        GlobalApplication.prefs.refresh = refreshToken

                        auth = FirebaseAuth.getInstance()
                        val firebaseJwt = response.body()!!.result!!.firebaseJwt
                        auth.signInWithCustomToken(firebaseJwt!!)

                        Log.d("jwt", GlobalApplication.prefs.token.toString())
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
            val jsonObject = JSONObject()
            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            //이미 캐릭터 정보를 선택한 사용자라면 바로 메인 화면으로 전환
            GetUser()
            if(char < 0){
                val intent2 = Intent(this, com.example.underthesea_aos.main.MainActivity::class.java)
                startActivity(intent2)
            } else {
                //캐릭터 선택 페이지 전환활 intent
                val intent1 =
                    Intent(this, com.example.underthesea_aos.character.MainActivity::class.java)
                startActivity(intent1)
            }
        }

        //accesstoken(, refreshtoken) 발급 과정 성공 or 실패
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            //발급 실패
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //발급 성공
            else if (token != null) {
                Log.d(ContentValues.TAG, "token : ${token}")

                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()

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

        //google
        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        SignInBtn.setOnClickListener {
            signInGoogle()
        }
    }

    //백엔드와의 통신 성공 or 실패 (구글)
    fun Login2(googleUserInfo: GoogleUserInfo){
        val call = RetrofitBuilder().retrofit().postGoogleLoginResponse(googleUserInfo)
        //비동기 방식의 통신
        call.enqueue(object : Callback<BaseResponse<KakaoResponse>> {
            //통신 성공
            override fun onResponse(call: Call<BaseResponse<KakaoResponse>>, response: Response<BaseResponse<KakaoResponse>>) {
                //응답 성공
                if(response.isSuccessful()){
                    //Log.d("Response1: ", Gson().toJson(response.body()))
                    // jwt token 저장
                    jwtToken = response.headers().value(0).toString().split(" ")[1]
                    GlobalApplication.prefs.token = jwtToken

                    Log.d("jwt", GlobalApplication.prefs.token.toString())
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
        val jsonObject = JSONObject()
        val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

        //캐릭터 선택 페이지 전환활 intent
        val intent1 = Intent(this, com.example.underthesea_aos.character.MainActivity::class.java)
        startActivity(intent1)
        //com.example.underthesea_aos.character.MainActivity::class.java
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
            result -> if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential((account).idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {//로그인 성공 시
                //val intent: Intent = Intent(this, SecondActivity::class.java)
                val googleUser = GoogleUserInfo()
                googleUser.email = account.email
                googleUser.nickname = account.displayName
                googleUser.profileImgUrl = account.photoUrl.toString()
                //구글 사용자 서버에도 저장
                Login2(googleUser)
                intent.putExtra("email", account.email)
                intent.putExtra("name", account.displayName)
                //startActivity(intent)

            } else {//로그인 실패 시
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    //사용자 정보 받아오기
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
                    if(response.body()?.result?.characterId == null){
                        char = -1
                    }
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
}