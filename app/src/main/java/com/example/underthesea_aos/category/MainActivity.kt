package com.example.underthesea_aos.category

import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.underthesea_aos.R

class MainActivity : AppCompatActivity() {
    val image1 = findViewById<ImageButton>(R.id.image01)
    val image2 = findViewById<ImageButton>(R.id.image02)
    val image3 = findViewById<ImageButton>(R.id.image03)
    val image4 = findViewById<ImageButton>(R.id.image04)
    val recom = findViewById<RecyclerView>(R.id.recommendation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        image1.setOnClickListener{ showRecomm() }
        image2.setOnClickListener{ showRecomm() }
        image3.setOnClickListener{ showRecomm() }
        image4.setOnClickListener{ showRecomm() }
    }

    fun showRecomm(){

    }
}