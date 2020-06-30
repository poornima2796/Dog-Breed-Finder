package com.poorni.breedfinder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.room.Room
import coil.api.load
import com.poorni.breedfinder.Model.BreedObj
import com.poorni.breedfinder.Model.DB
import com.poorni.breedfinder.Services.WebServices
import kotlinx.android.synthetic.main.activity_trivia.*
import org.json.JSONObject


class TriviaActivity : AppCompatActivity() {
    var img_dog: ImageView? = null
    var questionsTotal:Int = 0
    var currentQuestions:Int = 1
    var correctAns: Int = 0
    var incorrectAns: Int = 0
    var db: DB? = null
    var options= mutableListOf("")
    var context: Context? = null
    private val WAIT_TIME:Long = 1000



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)
        context = this;
        db = Room.databaseBuilder(
            this,
            DB::class.java, "db_doglisting"
        )
            .fallbackToDestructiveMigration()
            .build()

        img_dog = findViewById<View>(R.id.dogImg) as ImageView
        questionsTotal=intent.getStringExtra("no_of_questions").toInt()
        questionTxt.text = currentQuestions.toString() + " of "+questionsTotal.toString()

        val getDetails = getDetails()
        getDetails.execute()
    }

    fun setBorderColor(clr: Int): ShapeDrawable{
        val shapedrawable = ShapeDrawable()
        shapedrawable.shape = RectShape()
        shapedrawable.paint.color = clr
        shapedrawable.paint.strokeWidth = 10f
        shapedrawable.paint.style = Paint.Style.STROKE

        return  shapedrawable
    }
    private inner class getDetails :
        AsyncTask<Void?, Void?, String>() {

        override fun doInBackground(vararg p0: Void?): String {
            var res = WebServices.dogImages
            var option1: String?
            var option2: String?
            options.clear()

            for (i in 0 until 2) {
                //TODO get

                var idNo = (1..137).random()
                var dogObj = db?.AppDAO()?.findRandomDog(idNo);
                options.add(dogObj?.name.toString())
            }

            return WebServices.dogImages
        }
        @SuppressLint("ResourceAsColor")
        override fun onPostExecute(result: String) {

            option1.setBackgroundResource(R.color.colorPrimaryDark)
            option2.setBackgroundResource(R.color.colorPrimaryDark)
            option3.setBackgroundResource(R.color.colorPrimaryDark)

            option1.setTextColor(Color.WHITE)
            option2.setTextColor(Color.WHITE)
            option3.setTextColor(Color.WHITE)

           // dogObj= dispResults(result)
            val jsonObject = JSONObject(result)
            val img = jsonObject.getString("message")
            val dog = img.split("https://images.dog.ceo/breeds/", "/")
            var breedObj: BreedObj = BreedObj(img, dog[1], options.get(0), options.get(1));
            var optionValues= mutableListOf(breedObj.breed, breedObj.option2, breedObj.option1)
            optionValues.shuffle()


            img_dog?.load(breedObj?.img) {
                crossfade(true)
                placeholder(R.drawable.loading)
                transformations()
            }

            option1.setText(optionValues.get(0).split(' ','-').joinToString(" ") { it.capitalize() })
            option2.setText(optionValues.get(1).split(' ','-').joinToString(" ") { it.capitalize() })
            option3.setText(optionValues.get(2).split(' ','-').joinToString(" ") { it.capitalize() })



            option1.setOnClickListener{
                if(option1.text.equals(breedObj.breed.split(' ','-').joinToString(" ") { it.capitalize() })){
                    Log.d("RES----","Correct")
                    var sd = setBorderColor(Color.GREEN)
                    option1.setBackground(sd)
                    option1.setTextColor(Color.BLACK)
                    correctAns++

                } else {
                    Log.d("RES----","Incorrect")
                    //option1.setBackgroundColor(Color.RED);
                    var sd = setBorderColor(Color.RED)
                    option1.setTextColor(Color.BLACK)
                    option1.setBackground(sd)
                    incorrectAns++
                }
                Handler().postDelayed({
                    onSelectOption()
                }, WAIT_TIME)

            }
            option2.setOnClickListener{
                if(option2.text.equals(breedObj.breed.split(' ','-').joinToString(" ") { it.capitalize() })){
                    var sd = setBorderColor(Color.GREEN)
                    option2.setBackground(sd)
                    option2.setTextColor(Color.BLACK)
                    correctAns++

                } else{

                    var sd = setBorderColor(Color.RED)
                    option2.setBackground(sd)
                    option2.setTextColor(Color.BLACK)
                    incorrectAns++
                }
                Handler().postDelayed({
                    onSelectOption()
                }, WAIT_TIME)

            }
            option3.setOnClickListener{
                if(option3.text.equals(breedObj.breed.split(' ','-').joinToString(" ") { it.capitalize() })){
                    var sd = setBorderColor(Color.GREEN)
                    option3.setBackground(sd)
                    option3.setTextColor(Color.BLACK)
                    correctAns++

                } else
                {
                    var sd = setBorderColor(Color.RED)
                    option3.setBackground(sd)
                    option3.setTextColor(Color.BLACK)
                    incorrectAns++
                }
                Handler().postDelayed({
                   onSelectOption()
                }, WAIT_TIME)
            }

        }
        fun onSelectOption(){
            if(currentQuestions<questionsTotal){
                currentQuestions++
                questionTxt.text = currentQuestions.toString() + " of "+questionsTotal.toString()
                val getDetails = getDetails()
                getDetails.execute()
                //TODO - Add else block

            } else{
                val intent = Intent(this@TriviaActivity, SummaryActivity::class.java)
                intent.putExtra("correct_ans",correctAns.toString())
                intent.putExtra("ques_total",questionsTotal.toString())
                intent.putExtra("wrong_ans",incorrectAns.toString())
                startActivity(intent)
                finish()
            }
        }
    }


}
