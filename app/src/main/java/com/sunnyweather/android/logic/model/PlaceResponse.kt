package com.sunnyweather.android.logic.model

import com.google.gson.annotations.SerializedName

//数据模型

//PlaceResponse：城市信息（status：请求的状态(ok表示成功)     places：与城市名关系度较高的地区信息）
data class PlaceResponse(val status:String, val places:List<Place>)

//Place：地区信息（name：地区名     location：坐标     address：该地区地址）  @SerializedName：让JSON字段和Kotlin字段之间建立映射关系
data class Place(val name: String, val location:Location, @SerializedName("formatted_address") val address:String)

//Location:坐标（lat:经度   lng：纬度  ）
data class Location(val lng:String, val lat:String)