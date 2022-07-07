package com.example.googlebookstest.model.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import com.example.googlebookstest.model.BookElement
import com.example.googlebookstest.model.BookResponse
import com.example.googlebookstest.model.VolumeInfoElement
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Network (private val context: Context) {
    // Check current network state
    // Build the URI using yh user input
    // Do request
    // Return the PARSE response

    //field injection
    //@Inject
    //private val context: Context

    private fun checkNetworkState(): Boolean{
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val info = connectivityManager.activeNetworkInfo

        //return if(info == null)false else true
        info?.let {
            return it.isConnected
        }
        return false
        //Java way  info == null ? true : false
    }

    fun searchBookCriteria(bookName: String, bookResult: Int): BookResponse{
        // base url   https://www.googleapis.com/
        // end point  books/v1/volumes
        // parameters ?q=pride+prejudice   &maxResults=5   &printType=books

        val baseUrl = "https://www.googleapis.com/"
        val endpoint = "books/v1/volumes"
        val paramQ = "q"
        val paramMaxResult = "maxResults"
        val paramBookType = "printType"

        val uri = Uri.parse("$baseUrl$endpoint")
            .buildUpon()
            .appendQueryParameter(paramQ, bookName)
            .appendQueryParameter(paramMaxResult, bookResult.toString())
            .appendQueryParameter(paramBookType, "books")
            .build()

        val requestURL = URL(uri.toString())
        val httpURLConnection = requestURL.openConnection() as HttpURLConnection
        httpURLConnection.connectTimeout = 10000
        httpURLConnection.readTimeout = 15000
        httpURLConnection.requestMethod = "GET"
        httpURLConnection.doInput = true
        httpURLConnection.connect()

        val stringResult = parseInputStream(httpURLConnection.inputStream)

        httpURLConnection.responseCode
        httpURLConnection.responseMessage

        return parseStringResponse(stringResult)
    }

    private fun parseInputStream (inputStream: InputStream): String{
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()

        while (line != null){
            builder.append("$line\n")
            line = reader.readLine()
        }
        if (builder.length == 0) {
            return ""
        }
        return builder.toString()

    }

    private fun parseStringResponse(stringResult: String): BookResponse{
        val root = JSONObject(stringResult)
        val jsonItem = root.getJSONArray("items")
        val itemList = ArrayList<BookElement>()

        for(i in 0 until jsonItem.length()){
            val element = jsonItem.getJSONObject(i)
            val volumeInfo = element.getJSONObject("volumeInfo")
            val title = volumeInfo.getString("title")
            val authors = volumeInfo.getJSONArray("authors")
            val authorList = ArrayList<String>()

            for(j in 0 until authors.length()) {
                authors.getString(j)
            }
            val volumeInfoElement = VolumeInfoElement(title, authorList)
            val bookElement = BookElement(volumeInfoElement)
            itemList.add(bookElement)
        }
        return BookResponse(itemList)
    }
}

//Java
//public class N(){}
//public N(String something){}
//public N(String something, String another){
//   this.mSomething = something;
//}


//Kotlin
//primary constructor  [class] [ClassName] (optional constructor){}
//Class NK(something: String){
//   constructor(one: String, another: String): this(one){}
//}

//init{
// val i1= NK(something:"tony")
//val i2= NK ("tony", "ardines")
// }