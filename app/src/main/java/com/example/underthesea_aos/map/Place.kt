package com.example.underthesea_aos.map

data class Place(
    var place_id:Long?,
    var latitude:String,
    var longitude:String,
    var name:String,
    var content:String,
    var page:String ) {}