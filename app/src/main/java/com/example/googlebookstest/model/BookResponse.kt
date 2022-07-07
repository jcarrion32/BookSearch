package com.example.googlebookstest.model

data class BookResponse(
    val items: List<BookElement>?
)

//for json object
data class BookElement(
    val volumeInfo: VolumeInfoElement?
)


data class VolumeInfoElement(
    val title:String?,
    val authors: List<String>?
)


//Data prefix provides:
//toString()
//equals()
//hashCode()
//copy()
//componentN ->
// data class name(name, lastName)
// val (name, lastName) = Name("tony", "ar")