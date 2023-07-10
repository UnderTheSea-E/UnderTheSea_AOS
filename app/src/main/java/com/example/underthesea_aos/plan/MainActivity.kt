package com.example.underthesea_aos.plan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.databinding.ActivityPlanMainBinding
import com.example.underthesea_aos.recyclerview.HorizontalItemDecorator
import com.example.underthesea_aos.recyclerview.VeritcalItemDecorator
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_plan_add.*
import kotlinx.android.synthetic.main.activity_plan_main.*
import kotlinx.android.synthetic.main.activity_plan_main.date
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

/*
  계획 조회 페이지
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlanMainBinding
    private val datas = mutableListOf<Plan>()
    lateinit var  myAdapter: MyAdapter
    var strDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent1 = Intent(this, AddActivity::class.java)

        if (intent.hasExtra("date")) {
            strDate = intent.getStringExtra("date").toString()
            date.text = strDate
            intent1.putExtra("date", strDate)
        }

        add_btn.setOnClickListener {
            startActivity(intent1)
        }

        initRecycler()

        GetPlans(strDate)
    }

    private fun GetPlans(date: String){

        val call = RetrofitBuilder().retrofit().getPlansResponse(date)
        //비동기 방식의 통신
        call.enqueue(object : retrofit2.Callback<BaseResponse<GetPlanRes>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<GetPlanRes>>,
                response: Response<BaseResponse<GetPlanRes>>
            ) {
                if(response.isSuccessful()){
                    if(response.body()?.result?.plans?.size != null){
                        for(i in 0..response.body()!!.result!!.plans!!.size-1) {
                            datas!!.add(response.body()!!.result!!.plans!!.get(i))
                        }
                        main_recyclerView.adapter = myAdapter
                        myAdapter.datas = datas
                        myAdapter.notifyDataSetChanged()
                    }
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }

            override fun onFailure(call: Call<BaseResponse<GetPlanRes>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    private fun initRecycler(){
        myAdapter = MyAdapter(this)

        main_recyclerView.adapter = myAdapter
        main_recyclerView.addItemDecoration(VeritcalItemDecorator(20))
        //main_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        datas.apply { }

        myAdapter.datas = datas
        myAdapter.notifyDataSetChanged()

        back_img.setOnClickListener{
            val intent = Intent(this, com.example.underthesea_aos.calendar_plan.MainActivity::class.java)
            startActivity(intent)
        }
    }
}
