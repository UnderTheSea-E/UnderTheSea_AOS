package com.example.underthesea_aos.retrofit

import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.character.CharacterInfo
import com.example.underthesea_aos.firebase.PostFirebaseReq
import com.example.underthesea_aos.kakaoLogIn.KakaoToken
import com.example.underthesea_aos.plan.GetPlanRes
import com.example.underthesea_aos.plan.Plan
import com.example.underthesea_aos.record.GetRecordRes
import com.example.underthesea_aos.record.PostRecordRes
import com.example.underthesea_aos.record.RecordInfo
import com.example.underthesea_aos.user.GetFriendRes
import com.example.underthesea_aos.user.GoogleUserInfo
import com.example.underthesea_aos.user.KakaoResponse
import com.example.underthesea_aos.user.UserResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.Objects

public interface API {
    //kakao login
    @POST("login/kakao")
    fun postKakaoLoginResponse(
        @Body token: KakaoToken
    ): Call<BaseResponse<KakaoResponse>>

    //kakao login
    @POST("login/google")
    fun postGoogleLoginResponse(
        @Body googleUserInfo: GoogleUserInfo
    ):Call<BaseResponse<KakaoResponse>>

    //get user
    @GET("userInfo")
    fun getUserResponse(
    ): Call<BaseResponse<UserResponse>>

    //put character
    @PUT("character")
    fun putCharacterResponse(
        @Body character: CharacterInfo
    ): Call<BaseResponse<Long>>

    //post records
    @POST("records")
    fun postRecordsResponse(
        @Body record: RecordInfo
    ): Call<BaseResponse<Long>>

    //get records
    @GET("records")
    fun getRecordsResponse(
        @Query("date") date: String
    ): Call<BaseResponse<GetRecordRes>>

    //get record
    @GET("record")
    fun getRecordResponse(
        @Query("record_id") record_id: Long
    ): Call<BaseResponse<RecordInfo>>

    //post plans
    @POST("plans")
    fun postPlanResponse(
        @Body plan: Plan
    ):Call<BaseResponse<Long>>

    //get plans
    @GET("plans")
    fun getPlansResponse(
        @Query("date") date: String
    ): Call<BaseResponse<GetPlanRes>>

    //get plan
    @GET("plan")
    fun getPlanResponse(
        @Query("plan_id") plan_id: Long
    ): Call<BaseResponse<Plan>>

    //put plan
    @PUT("plans")
    fun putPlanResponse(
        @Body plan: Plan
    ): Call<BaseResponse<Long>>

    //get friend
    @GET("friends")
    fun getFriendResponse(
    ): Call<BaseResponse<GetFriendRes>>

    @POST("friends")
    fun postFriendResponse(
        @Query("email") friendEmail: String
    ): Call<BaseResponse<Long>>

    //get friend
    @POST("api/fcm")
    fun postFriendFirebaseResponse(
        @Body postFirebaseReq: PostFirebaseReq
    ): Call<Objects>
}