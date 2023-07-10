package com.example.underthesea_aos.plan

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.R

class MyAdapter(private val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>()
{
    var datas = mutableListOf<Plan>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_plan_preview_recyclerview, parent, false)
        return ViewHolder(view) //view 객체는 한개의 사진이 디자인 된 레이아웃
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //onBindViewHolder는 스크롤을 해서 데이터 바인딩이 새롭게 필요할 때 마다 호출된다.
        //Get element from your dataset at this position and replace the
        //contents of the view with that element
        viewHolder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title : TextView = itemView.findViewById(R.id.plan_title)
        fun bind(item: Plan){
            title.text = item.title

            title.setOnClickListener {
                //상세 조회를 위해 클릭한 계획의 식별자 함께 넘겨주기
                val intent1 = Intent(context, UpdateActivity::class.java)
                intent1.putExtra("plan_id", item.planId)
                context.startActivity(intent1)
            }
        }

    }
}
