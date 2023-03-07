package com.example.underthesea_aos.plan

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.underthesea_aos.R
import com.example.underthesea_aos.databinding.ActivityMainBinding
import com.example.underthesea_aos.databinding.ActivityPlanMainBinding
import com.example.underthesea_aos.recyclerview.HorizontalItemDecorator
import com.example.underthesea_aos.recyclerview.VeritcalItemDecorator
import kotlinx.android.synthetic.main.activity_plan_main.*
import kotlinx.android.synthetic.main.activity_plan_main.date
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.*
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.view.*
import kotlinx.android.synthetic.main.activity_record.*

/*
  계획 조회 페이지
 */

class MainActivity : AppCompatActivity() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var myAdapter: MyAdapter
    //private val datas = mutableListOf<PlanPreviewData>()
    lateinit var binding: ActivityPlanMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent1 = Intent(this, AddActivity::class.java)

        if (intent.hasExtra("date")) {  //date라는 키값을 가진 intent가 정보를 가지고 있다면 실행
            // date라는 id의 textview의 문구를 date라는 키값을 가진 intent의 정보로 변경
            val strDate = intent.getStringExtra("date").toString()
            Log.d("date", intent.getStringExtra("date").toString())
            date.text = strDate
            intent1.putExtra("date",strDate)
        }

        add_btn.setOnClickListener {
            startActivity(intent1)
        }

        //initializeViews()


      /*  fun initializeViews() {
            binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.mainRecyclerView.adapter = MyAdapter(datas)
        }*/
    }
}
