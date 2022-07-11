package com.example.googlebookstest.model.remote

import com.example.googlebookstest.model.BookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("books/v1/volumes")
    fun getBook(
        @Query("q") userBookName: String,
        @Query("maxResult") userMaxResult: String,
        @Query("printType") userPrintType: String
    ):Call<BookResponse>

}
//first add retrofit libraries (com.squareup.retrofit2, converter-*)
//create a Interface service
//define the http verb
// define the function to execute the network request