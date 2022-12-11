package com.example.apirestwithvolley

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() ,MyAdapter.OnItemClickListener {
    lateinit var GovList : MutableList<photo>
    lateinit var btnAddF : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            getData()
        btnAddF = findViewById(R.id.fab)
        btnAddF.setOnClickListener{
            intent = Intent(applicationContext, addPhoto::class.java)
            startActivity(intent)
        }

    }
        private fun getData() {
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            var url = "https://jsonplaceholder.typicode.com/photos"
            val queue = Volley.newRequestQueue(this)
            val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
                GovList = mutableListOf()
                try {
                    for (i in 0 until response.length()) {
                        val photo1 = photo()
                        val respObj = response.getJSONObject(i)
                        Log.i("Success", respObj.toString());
                        recyclerView.apply {
                            photo1.albumId = respObj.getInt("albumId")
                            photo1.id = respObj.getInt("id")
                            photo1.title = respObj.getString("title")
                            photo1.url = respObj.getString("url")
                            photo1.thumbnailUrl = respObj.getString("thumbnailUrl")
                            GovList.add(photo1)
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = MyAdapter(GovList, this@MainActivity)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, { error ->
                Log.e("request-error", error.toString());
            })
            queue.add(request)
        }

    override fun OnItemClick(position: Int) {

        Toast.makeText(this, "Item " + position + " clicked" , Toast.LENGTH_LONG).show()

    }

}


