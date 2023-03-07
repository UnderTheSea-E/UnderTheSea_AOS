package com.example.underthesea_aos.plan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.R
import com.example.underthesea_aos.databinding.ActivityPlanMainBinding
import com.example.underthesea_aos.databinding.ActivityPlanPreviewRecyclerviewBinding

class MyAdapter(){

/*    var datas = mutableListOf<PlanPreviewData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder{
        *//*val view = LayoutInflater.from(context).inflate(R.layout.activity_plan_preview_recyclerview,parent,false)
        return ViewHolder(view)*//*

        return ViewHolder(
            ActivityPlanPreviewRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, positon:Int){
        viewHolder.bind(todos[positon])
    }

    override fun getItemCount(): Int = todos .size

    class ViewHolder(private val binding: ActivityPlanPreviewRecyclerviewBinding):RecyclerView.ViewHolder(binding.root){
        private val txtView : TextView = itemView.findViewById(R.id.add_editView)
        fun bind(item: PlanPreviewData){
            txtView.text = item.title
        }
    }*/
}
