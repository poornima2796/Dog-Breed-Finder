package com.poorni.breedfinder

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.poorni.breedfinder.Model.BreedObj
import com.poorni.breedfinder.Services.WebServices
import kotlinx.android.synthetic.main.activity_random_dog.*
import kotlinx.android.synthetic.main.activity_splash.*
import org.json.JSONException
import org.json.JSONObject

class RandomDog : AppCompatActivity() {

    var img_dog: ImageView? = null
    var dogObj: BreedObj? = null
    var context: Context? = null

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog)
        context = this;
        val getDetails = getDetails()
        getDetails.execute()
        img_dog = findViewById<View>(R.id.dog_img) as ImageView
       btn_guess.setOnClickListener {

           val getDetails = getDetails()
           getDetails.execute()
        }

        btn_quit.setOnClickListener {
            val intent = Intent(this@RandomDog, HomeActivity::class.java)
            startActivity(intent)
            this.finish()
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
                placeholder(R.drawable.loading)
                transformations()
            }

            dog_name_txt.text = dogObj!!.breed.split(' ','-').joinToString(" ") { it.capitalize() }

        }
    }


    //setting the text in the screen
    @Throws(JSONException::class)
    fun  dispResults(results: String?): BreedObj {
        val jsonObject = JSONObject(results)
        val img = jsonObject.getString("message")
        val status = jsonObject.getString("status")
        val dog = img.split("https://images.dog.ceo/breeds/", "/")

        var breedObj: BreedObj =
            BreedObj(img, dog[1], "", "");

        return breedObj;

    }
}