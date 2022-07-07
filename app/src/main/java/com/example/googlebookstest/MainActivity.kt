package com.example.googlebookstest

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.googlebookstest.model.remote.Network
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var btnSearch: Button
    private lateinit var tilBookName: TextInputLayout
    private lateinit var tilBookResult: TextInputLayout
    private lateinit var tvDisplay: TextView

//    private val handler: Handler by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//        Handler
//        .createAsync(Looper.getMainLooper(),)}else{
//            Handler()
//        }
//    }

    private val handler = object: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            tvDisplay.text = msg.data.getString("BookResponse")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById(R.id.tv_display)
        if (::btnSearch.isInitialized)//methods reference
            btnSearch.setOnClickListener(this)
        else
            btnSearch = findViewById(R.id.btn_search_book)

        tilBookName = findViewById(R.id.til_book_name_search)
        tilBookResult = findViewById(R.id.til_book_result_search)
        
        //Interface Onclick -> work with the onClick function
        btnSearch.setOnClickListener(this)
        
        //lambdas
//        btnSearch.setOnClickListener {
//
//        }
        //anonymous implementation
//        btnSearch.setOnClickListener(
//            object : View.OnClickListener{
//                override fun onClick(p0: View?) {
//                    TODO("Not yet implemented")
//                }
//            }
//        )
        // methods reference 
        btnSearch.setOnClickListener(::doo)
    }

    private fun doo(view: View?) {
        if (tilBookName.editText?.text.toString().isNotEmpty() &&
                tilBookResult.editText?.text.toString().isNotEmpty())
            networkRequest(tilBookName.editText?.text.toString(),
            tilBookResult.editText?.text.toString())
        else
            Toast.makeText(this,
                "no empty values",
                Toast.LENGTH_LONG).show()

    }

    private fun networkRequest(bookName: String, bookResult: String) {
        val network = Network(applicationContext)

        Thread(Runnable {
            val response = network.searchBookCriteria(bookName, bookResult.toInt())
            Log.d("MainActivity", "networkRequest: $response")
            // tvDisplay.text = response.toString() Only the original thread that created a view hierarchy can touch its views

            val message = handler.obtainMessage()
            /*
             val b = Bundle()
             b.put = ("", response.toString())
             message.data = b
             */
            message.data = Bundle().apply {
                putString("BookResponse", response.toString())
            }
            handler.sendMessage(message)
        }).start()


    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}
