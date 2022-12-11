package com.example.apirestwithvolley

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class updatePhoto : AppCompatActivity() {
    lateinit var albumId2 : EditText
    lateinit var id2 : EditText
    lateinit var title2 : EditText
    lateinit var url2 : EditText
    lateinit var thumbnailUrl2 : EditText
    lateinit var btnUpdate : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_photo)


        albumId2 = findViewById(R.id.albumId2)
        id2 = findViewById(R.id.id2)
        title2 = findViewById(R.id.title2)
        url2 = findViewById(R.id.url2)
        thumbnailUrl2 = findViewById(R.id.thumbnailUrl2)

        val extras = intent.extras
        val albumId1 = extras!!.getInt("albumId").toString()
        val id1 = extras!!.getInt("id").toString()
        val title1 = extras!!.getString("title")
        val url1 = extras!!.getString("url")
        val thumbnailUrl1 = extras!!.getString("thumbnailUrl")

        albumId2.setText(albumId1)
        id2.setText(id1)
        title2.setText(title1)
        url2.setText(url1)
        thumbnailUrl2.setText(thumbnailUrl1)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnUpdate.setOnClickListener {

            if (albumId2.text.toString().isEmpty()||id2.text.toString().isEmpty()|| title2.text.toString().isEmpty()||url2.text.toString().isEmpty() ||thumbnailUrl2.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter the new value", Toast.LENGTH_SHORT).show();
            }
            UpdatedVolley(albumId2.text.toString().toInt(),id2.text.toString().toInt(),title2.text.toString(),url2.text.toString(),thumbnailUrl2.text.toString());
        }

    }

    fun UpdatedVolley(albumId2:Int , id2 : Int , title2 : String , url2 : String , thumbnailUrl2 : String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://jsonplaceholder.typicode.com/photos/$id2"

        val data = JSONObject();
        try {
            data.put("albumId", albumId2)
            data.put("id", id2)
            data.put("title", title2)
            data.put("url", url2)
            data.put("thumbnailUrl", thumbnailUrl2)

        } catch (e : JSONException) {

            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT, url, data, {
            Toast.makeText(this@updatePhoto, "updated successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@updatePhoto, MainActivity::class.java)
            startActivity(intent) })
        { Toast.makeText(this@updatePhoto, "update failed", Toast.LENGTH_SHORT).show() }

        queue.add(jsonObjectRequest)
    }

}