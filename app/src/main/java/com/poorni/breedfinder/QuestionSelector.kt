package com.poorni.breedfinder

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.jem.rubberpicker.RubberSeekBar
import kotlinx.android.synthetic.main.activity_question_selector.*

class QuestionSelector : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_selector)
        setTitle("Trivia")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        rubberSeekBar.setOnRubberSeekBarChangeListener(object: RubberSeekBar.OnRubberSeekBarChangeListener{
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                selectedTxt.text = value.toString()

            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        btn_question_selector.setOnClickListener{

            val intent = Intent(this@QuestionSelector, TriviaActivity::class.java)
            intent.putExtra("no_of_questions",selectedTxt.text.toString())
            startActivity(intent)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun setTitle(title: String?) {
        val font = ResourcesCompat.getFont(applicationContext, R.font.montserrat)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val textView = TextView(this)
        textView.text = title
        textView.setTypeface(font)
        textView.textSize = 20f
        textView.setTextColor(Color.WHITE)
        textView.gravity = Gravity.CENTER_HORIZONTAL
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.customView = textView
    }



}