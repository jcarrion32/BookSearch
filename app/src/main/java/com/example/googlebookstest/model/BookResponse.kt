package com.example.googlebookstest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookResponse(
    val items: List<BookElement>?
):Parcelable

//for json object
@Parcelize
data class BookElement(
    val volumeInfo: VolumeInfoElement?
):Parcelable


@Parcelize
data class VolumeInfoElement(
    val title:String?,
    val authors: List<String>?
):Parcelable


//Data prefix provides:
//toString()
//equals()
//hashCode()
//copy()
//componentN ->
// data class name(name, lastName)
// val (name, lastName) = Name("tony", "ar")