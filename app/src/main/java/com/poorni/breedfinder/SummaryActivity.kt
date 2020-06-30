package com.poorni.breedfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_summary.*
import kotlin.math.roundToInt

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        var correct=intent.getStringExtra("correct_ans").toFloat()
        var wrong=intent.getStringExtra("wrong_ans").toInt()
        var questionsTotal=intent.getStringExtra("ques_total").toFloat()
        Log.d("RES----", correct.toString())

        txtCorrect.setText(correct.roundToInt().toString())
        txtWrong.setText(wrong.toString())

        var score = (((correct)/(questionsTotal)) * 100).roundToInt()
        txtScore.setText(score.toString())
        Log.d("RES----", score.toString())

        btn_menu.setOnClickListener {
            val intent = Intent(this@SummaryActivity, HomeActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}
