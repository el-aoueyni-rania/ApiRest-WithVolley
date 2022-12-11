package com.example.apirestwithvolley

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class addPhoto : AppCompatActivity() {
    lateinit var btnAdd : Button
    lateinit var albumId1 :EditText
    lateinit var id1 :EditText
    lateinit var title1 :EditText
    lateinit var url1 :EditText
    lateinit var thumbnailUrl1 :EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        albumId1 = findViewById(R.id.albumId1)
        id1 = findViewById(R.id.id1)
        title1 = findViewById(R.id.title1)
        url1 = findViewById(R.id.url1)
        thumbnailUrl1 = findViewById(R.id.thumbnailUrl1)


        btnAdd = findViewById(R.id.add)
        btnAdd.setOnClickListener {

            if (albumId1.text.toString().isEmpty()||id1.text.toString().isEmpty()|| title1.text.toString().isEmpty()||url1.text.toString().isEmpty() ||thumbnailUrl1.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter the new value", Toast.LENGTH_SHORT).show();
                }
            postVolley(albumId1.text.toString().toInt(),id1.text.toString().toInt(),title1.text.toString(),url1.text.toString(),thumbnailUrl1.text.toString());
        }
    }
    fun postVolley(albumId1:Int , id1 : Int , title1 : String , url1 : String , thumbnailUrl1 : String) {
        val queue = Volley.newRequestQueue(this)
        var url = "https://jsonplaceholder.typicode.com/photos"

        val data = JSONObject();
        try {
            data.put("albumId", albumId1)
            data.put("id", id1)
            data.put("title", title1)
            data.put("url", url1)
            data.put("thumbnailUrl", thumbnailUrl1)

        } catch (e : JSONException) {

            e.printStackTrace()
        }
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, data, {
            Toast.makeText(this@addPhoto, "added successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@addPhoto, MainActivity::class.java)
            startActivity(intent) })
        { Toast.makeText(this@addPhoto, "add failed", Toast.LENGTH_SHORT).show() }

        queue.add(jsonObjectRequest)
    }


}