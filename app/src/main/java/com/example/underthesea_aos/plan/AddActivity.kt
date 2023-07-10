package com.example.underthesea_aos.plan

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.underthesea_aos.BaseResponse.BaseResponse
import com.example.underthesea_aos.R
import com.example.underthesea_aos.map.FoodHelper
import com.example.underthesea_aos.map.PlaceHelper
import com.example.underthesea_aos.map.PromotionHelper
import com.example.underthesea_aos.recyclerview.HorizontalItemDecorator
import com.example.underthesea_aos.recyclerview.VeritcalItemDecorator
import com.example.underthesea_aos.retrofit.RetrofitBuilder
import com.example.underthesea_aos.user.GetFriendRes
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_plan_add.*
import retrofit2.Call
import retrofit2.Response

/*
     계획 상세 페이지
 */

class AddActivity : AppCompatActivity() {
    lateinit var planAdapter: PlanAdapter
    private val dataSet = mutableListOf<RecommendationData>()
    //lateinit var binding: ActivityPlanAddBinding
    var strDate = ""

    lateinit var dbHelper: FoodHelper
    lateinit var dbHelper1: PromotionHelper
    lateinit var dbHelper2 : PlaceHelper
    lateinit var  database: SQLiteDatabase
    var nameSet = mutableListOf<RecommendationData>()
    var nameSet1 = mutableListOf<RecommendationData>()
    var nameSet2 = mutableListOf<RecommendationData>()
    lateinit var spinner: Spinner
    var friendNames =  ArrayList<String>()
    var friendIdx =  ArrayList<Long>()
    var friendId = 0.toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_add)

        //친구 목록을 위한 스피너
        spinner = findViewById(R.id.spinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friendNames)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                friendId = friendIdx[i]
                friend_name.text = friendNames[i]
                Log.d("friend: ", friendId.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        if (intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            date.text = strDate
            intent.putExtra("date", strDate)
        }

        initRecycler()

        //save 저장하기 버튼
        val planInfo = Plan()
        save_button.setOnClickListener {
            PostPlan(planInfo)
            val intent3 = Intent(this, com.example.underthesea_aos.calendar_plan.MainActivity::class.java)
            intent3.putExtra("date",strDate)
            startActivity(intent3)
            finish()
            Toast.makeText(this, "저장이 완료되었습니다", Toast.LENGTH_SHORT).show()
        }

        //뒤로 가기 버튼
        back_btn.setOnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.putExtra("date",strDate)
            startActivity(intent1)
        }

        //cancel 버튼
        cancel_button.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            intent2.putExtra("date",strDate)
            startActivity(intent2)
        }

        //친구 목록을 보여주기
        GetFriends()

        //제로웨이스트 식당 recommendation
        image02.setOnClickListener {
            //food db에 접근
            dbHelper = FoodHelper(this, "food.db", null, 2);
            database = dbHelper.writableDatabase
            //place 정보 insert
            dbHelper.insertFood()
            database = dbHelper.readableDatabase
            val select = "select * from Food"
            //db 데이터에 접근하기 위한 커서
            val cursor = database.rawQuery(select, null)
            while (cursor.moveToNext()) {
                nameSet.add(
                    RecommendationData(
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                    )
                )
            }

            recommendation.adapter = planAdapter
            planAdapter.dataSet = nameSet
            planAdapter.notifyDataSetChanged()

            //res_url
        }

        //홍보물
        image03.setOnClickListener{
            dbHelper1 = PromotionHelper(this, "promotion.db", null, 2);
            database = dbHelper1.writableDatabase
            //place 정보 insert
            dbHelper1.insertPromotion()
            database = dbHelper1.readableDatabase
            val select1 = "select * from Promotion"
            //db 데이터에 접근하기 위한 커서
            val cursor1 = database.rawQuery(select1,null)
            while(cursor1.moveToNext()) {
                nameSet1.add(
                    RecommendationData(
                        cursor1.getString(1),
                        cursor1.getString(2),
                        cursor1.getString(3)
                    )
                )
            }

            recommendation.adapter = planAdapter
            planAdapter.dataSet = nameSet1
            planAdapter.notifyDataSetChanged()
        }

        image01.setOnClickListener{
            //place db에 접근
            dbHelper2 = PlaceHelper(this, "place.db", null, 2);
            database = dbHelper2.writableDatabase
            //place 정보 insert
            dbHelper2.insertPlace()
            database = dbHelper2.readableDatabase
            val select2 = "select * from Place"
            //db 데이터에 접근하기 위한 커서
            val cursor2 = database.rawQuery(select2,null)
            while(cursor2.moveToNext()) {
                nameSet2.add(
                    RecommendationData(
                        cursor2.getString(3),
                        cursor2.getString(4),
                        cursor2.getString(5)
                    )
                )
            }

            recommendation.adapter = planAdapter
            planAdapter.dataSet = nameSet2
            planAdapter.notifyDataSetChanged()
        }

        image04.setOnClickListener{
            //place db에 접근
            dbHelper2 = PlaceHelper(this, "place.db", null, 2);
            database = dbHelper2.writableDatabase
            //place 정보 insert
            dbHelper2.insertPlace()
            database = dbHelper2.readableDatabase
            val select2 = "select * from Place"
            //db 데이터에 접근하기 위한 커서
            val cursor2 = database.rawQuery(select2,null)
            while(cursor2.moveToNext()) {
                nameSet2.add(
                    RecommendationData(
                        cursor2.getString(3),
                        cursor2.getString(4),
                        cursor2.getString(5)
                    )
                )
            }

            recommendation.adapter = planAdapter
            planAdapter.dataSet = nameSet2
            planAdapter.notifyDataSetChanged()
        }

        //친구 추가를 위한 버튼
        friend_btn.setOnClickListener{
            val freindEmail = addFriends.text
            PostFriends(freindEmail.toString())
            GetFriends()
        }
    }

//    override fun onPause() {
//        super.onPause();
//        // 화면에서 보이지 않도록 하는 과정
//        // 화면의 상태를 임시 저장
//        Toast.makeText(getApplicationContext(), "onPause() 호출됨", Toast.LENGTH_LONG).show();
//    }

    //친구 목록 조회를 위한 백엔드 통신
    private fun GetFriends(){
        val call = RetrofitBuilder().retrofit().getFriendResponse()
        //비동기 방식의 통신
        call.enqueue(object : retrofit2.Callback<BaseResponse<GetFriendRes>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<GetFriendRes>>,
                response: Response<BaseResponse<GetFriendRes>>
            ) {
                if(response.isSuccessful()){
                    Log.d("Response: ", response.body().toString())
                    Log.d("length: ", response.body()?.result?.friend?.size.toString())
                    if(response.body()?.result?.friend?.size != null){
                        for(i in 0..response.body()!!.result!!.friend!!.size-1) {
                            friendNames.add(response.body()!!.result!!.friend!![i].nickname.toString())
                            friendIdx.add(response.body()!!.result!!.friend!![i].userId!!)
                            //Log.d("friend", friendIdx[0].toString())
                        }
                    }
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }

            override fun onFailure(call: Call<BaseResponse<GetFriendRes>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    //친구 등록을 위한 백엔드 통신
    private fun PostFriends(email: String){
        val call = RetrofitBuilder().retrofit().postFriendResponse(email)
        //비동기 방식의 통신
        call.enqueue(object : retrofit2.Callback<BaseResponse<Long>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<Long>>,
                response: Response<BaseResponse<Long>>
            ) {
                if(response.isSuccessful()){
                    if(response.body()?.result != null) {
                        Log.d("Response: ", "success")
                    }
                }
                //응답 실패
                else{
                    Log.d("Response: ", "failure")
                }
            }

            override fun onFailure(call: Call<BaseResponse<Long>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    private fun PostPlan(plan: Plan) {
        plan.title = title_plan.text.toString()
        plan.content = contents_memo.text.toString()
        plan.date = strDate
        plan.friend = 3

        val call = RetrofitBuilder().retrofit().postPlanResponse(plan)
        //비동기 방식의 통신
        call.enqueue(object : retrofit2.Callback<BaseResponse<Long>> {
            //통신 성공
            override fun onResponse(
                call: Call<BaseResponse<Long>>,
                response: Response<BaseResponse<Long>>
            ) {
                if (response.isSuccessful()) {
                    Log.d("Response: ", response.body().toString())
                }
                //응답 실패
                else {
                    Log.d("Response: ", "failure")
                }
            }

            override fun onFailure(call: Call<BaseResponse<Long>>, t: Throwable) {
                Log.d("Connection Failure", t.localizedMessage)
            }
        })
    }

    private fun initRecycler(){
        planAdapter = PlanAdapter(this)

        recommendation.adapter = planAdapter
        recommendation.addItemDecoration(VeritcalItemDecorator(20))
        recommendation.addItemDecoration(HorizontalItemDecorator(10))

        dataSet.apply {
            /*
            add(RecommendationData( = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
            add(RecommendationData(img1 = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
            add(RecommendationData(img1 = R.drawable.rectangle1, img2 = R.drawable.rectangle1))
             */
        }

            planAdapter.dataSet = dataSet
            planAdapter.notifyDataSetChanged()
        }
}
