package com.example.volleyapidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    var url = "https://api.github.com/users"
    private var userInfoItem = emptyArray<userInfoItem>()
    var userInfo = userInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rev)

        val stringRequest = StringRequest(url, {
            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()
            userInfoItem = gson.fromJson(it,Array<userInfoItem>::class.java)

            userInfoItem.forEach{
                userInfo.add(it)
            }

            val adapter = Adapter(this,userInfo)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
        }, Response.ErrorListener{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        })
        val volleyQueue = Volley.newRequestQueue(this)
        volleyQueue.add(stringRequest)
    }
}