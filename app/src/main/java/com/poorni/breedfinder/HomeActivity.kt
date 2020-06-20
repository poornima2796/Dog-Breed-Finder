package com.poorni.breedfinder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.poorni.breedfinder.QuizActivity

class HomeActivity : AppCompatActivity() {
    var btn_quiz: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        btn_quiz = findViewById<View>(R.id.btn_start) as Button
        btn_quiz!!.setOnClickListener {
            val intent = Intent(this@HomeActivity, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}