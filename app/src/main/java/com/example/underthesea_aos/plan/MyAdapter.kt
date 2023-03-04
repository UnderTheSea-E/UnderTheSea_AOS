package com.example.underthesea_aos.plan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.R
import com.example.underthesea_aos.databinding.ActivityPlanMainBinding
import com.example.underthesea_aos.databinding.ActivityPlanPreviewBinding
import com.example.underthesea_aos.databinding.ActivityPlanPreviewRecyclerviewBinding

class MyAdapter(private val context: Context):
    RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    var datas = mutableListOf<PlanPreviewData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder{
        val layoutview = LayoutInflater.from(context).inflate(R.layout.activity_plan_preview_recyclerview,parent,false)
        return ViewHolder(layoutview)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, positon:Int){

    }

    override fun getItemCount(): Int {

    }
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){



    }
}
