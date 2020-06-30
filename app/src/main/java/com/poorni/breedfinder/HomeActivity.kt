package com.poorni.breedfinder

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.poorni.breedfinder.Model.AppDAO
import com.poorni.breedfinder.Model.DB
import com.poorni.breedfinder.Model.DogObj
import com.poorni.breedfinder.Services.WebServices
import kotlinx.android.synthetic.main.activity_home_screen.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class HomeActivity : AppCompatActivity() {
    var prefs: SharedPreferences? = null
    var db: DB? = null

    var btn_quiz: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        db = Room.databaseBuilder(
            this,
            DB::class.java, "db_doglisting"
        )
            .fallbackToDestructiveMigration()
            .build()
        prefs = getSharedPreferences("com.poorni.breedfinder", MODE_PRIVATE);
        btn_quiz = findViewById<View>(R.id.btn_start) as Button
        btn_quiz!!.setOnClickListener {
            val intent = Intent(this@HomeActivity, QuestionSelector::class.java)
            startActivity(intent)
        }

        btn_random.setOnClickListener {
            val intent = Intent(this@HomeActivity, RandomDog::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (prefs!!.getBoolean("firstrun",true ) )
        {

            //on the first run of the app, the data gets installed in the application
            val getDogListing = getDogListing()
            getDogListing.execute()
            prefs!!.edit().putBoolean("firstrun", false).commit()
        }
    }


    //reading from the JSON file
    @Throws(IOException::class)
    fun Context.readJsonAsset(fileName: String): String {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

    //inserting all the dogtypes to the DB
    private inner class getDogListing :
        AsyncTask<Void?, Void?, String>() {

        override fun doInBackground(vararg p0: Void?): String {
            var data = readJsonAsset("DogNames.json")
            var jsonArray: JSONArray? = null
            try {
                jsonArray = JSONArray(data)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val id = obj.getInt("id")
                    val name = obj.getString("name")
                    var dog: DogObj =DogObj(id,name)
                    db?.AppDAO()?.insertDogObj(dog)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return data
        }

        @Throws(JSONException::class)
        override fun onPostExecute(result: String) {

        }
    }


}