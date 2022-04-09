package com.sunnyweather.android.logic.network

import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//用于访问彩云天气城市搜索API的Retrofit接口
interface PlaceService {

    //当调用searchPlaces()方法时，Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zn_CN")
    //搜索城市数据的API中只有query这个参数是需要动态指定的，所以使用@Query注解的方式实现
    //searchPlaces()方法的返回值被声明为Call<PlaceService>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象
    fun searchPlaces(@Query("query") query:String) :Call<PlaceResponse>
}