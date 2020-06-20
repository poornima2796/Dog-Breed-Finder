package com.poorni.breedfinder

import com.poorni.breedfinder.R
import com.poorni.breedfinder.WebServices
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import coil.transform.CircleCropTransformation
import org.json.JSONException
import com.poorni.breedfinder.BreedObj
import org.json.JSONObject

class QuizActivity : AppCompatActivity() {

    var img_dog: ImageView? = null
    var editText_breed_name: EditText? = null
    var btn_submit: Button? = null
    var dogObj: BreedObj? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val getDetails = getDetails()
        getDetails.execute()
        img_dog = findViewById<View>(R.id.imageView4) as ImageView
        editText_breed_name = findViewById<View>(R.id.name_txt) as EditText

        btn_submit = findViewById<View>(R.id.btn_submit) as Button
        btn_submit!!.setOnClickListener {

            checkResults()
        }
    }

    fun checkResults(){
        val breed = editText_breed_name?.text

        if(breed!=null){
            if(breed.equals(dogObj?.breed)){
                Log.d("RES---", "Match")
            } else{
                Log.d("RES---", "no match-----"+dogObj?.breed + "-------"+breed)
            }
        }
    }

    private inner class getDetails :
        AsyncTask<Void?, Void?, String>() {




        override fun doInBackground(vararg p0: Void?): String {
            return WebServices.dogImages
        }

        override fun onPostExecute(result: String) {

            Log.d("RES---", result)
            dogObj= dispResults(result)
            img_dog?.load(dogObj?.img) {
                crossfade(true)
                placeholder(R.drawable.logo)
                transformations()
            }


        }
    }


    //setting the text in the screen
    @Throws(JSONException::class)
    fun  dispResults(results: String?): BreedObj {
        val jsonObject = JSONObject(results)
        val img = jsonObject.getString("message")
        val status = jsonObject.getString("status")
        val dog = img.split("https://images.dog.ceo/breeds/", "/")

        var breedObj:BreedObj= BreedObj(img, dog[1], status);

        return breedObj;

    }
}