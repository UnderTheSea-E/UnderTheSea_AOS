package com.example.underthesea_aos.plan

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.*
import kotlinx.android.synthetic.main.activity_plan_preview_recyclerview.view.*

/*
  계획 조회 페이지
 */

class MainActivity : AppCompatActivity() {
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var myAdapter: MyAdapter
    private val datas = mutableListOf<PlanPreviewData>()
    lateinit var binding: ActivityPlanMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()

/*    private fun initRecycler(){
        myAdapter = MyAdapter(this)

        main_recyclerView.adapter = myAdapter
        main_recyclerView.addItemDecoration(VeritcalItemDecorator(20))
        main_recyclerView.addItemDecoration(HorizontalItemDecorator(10))

        data.apply {
            add(PlanPreviewData(title = MyApplication.preferences.getString("title","")))
        }

        myAdapter.data = data
        myAdapter.notifyDataSetChanged()
    }*/
    }

    private fun initializeViews(){
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.adapter = MyAdapter(datas)
    }
}
