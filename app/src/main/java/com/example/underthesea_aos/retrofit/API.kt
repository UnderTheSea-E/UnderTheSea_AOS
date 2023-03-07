package com.example.underthesea_aos.retrofit

import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.character.CharacterInfo
import com.example.underthesea_aos.kakaoLogIn.KakaoToken
import com.example.underthesea_aos.plan.GetPlanRes
import com.example.underthesea_aos.plan.Plan
import com.example.underthesea_aos.record.PostRecordRes
import com.example.underthesea_aos.record.RecordInfo
import com.example.underthesea_aos.user.KakaoResponse
import com.example.underthesea_aos.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

public interface API {
    //kakao login
    @POST("login/kakao")
    fun postKakaoLoginResponse(
        @Body token: KakaoToken
    ): Call<BaseResponse<KakaoResponse>>

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
    ): Call<PostRecordRes>

    //get records
    @GET("records")
    fun getRecordsResponse(
        @Query("date") date: String
    ): Call<String>

    //get plans
    @GET("plans")
    fun getPlansResponse(
        @Query("date") date: String
    ): Call<BaseResponse<GetPlanRes>>

    //get plan
    @GET("plan")
    fun getPlanResponse(
        @Query("plan_id") plan_id: Long
    ): Call<Plan>
}