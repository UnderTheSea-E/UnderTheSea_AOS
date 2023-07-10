package com.example.underthesea_aos.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.underthesea_aos.R
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_firebase.*

class FirebaseActivity : AppCompatActivity() {

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println(task.result)
                tokenTextView.text = task.result
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)

        initFirebase()
    }
}