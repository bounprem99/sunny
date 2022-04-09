package com.sunnyweather.android.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//创建Retrofit构建器使用PlaceService接口
object ServiceCreator {
    //BASE_URL：指定Retrofit的根路径
    private const val BASE_URL = "https://api.caiyunapp.com/"

    //Retrofit.Builder()：创建一个Retrofit对象
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //提供一个外部可见的create()方法，并接收一个Class类型的参数
    //当外部调用这个方法时，实际上就是调用了Retrofit对象的create()方法，从而创建出相应Service接口的动态代理对象
    fun <T> create(serviceClass:Class<T>) : T = retrofit.create(serviceClass)

    inline fun <reified T> create() : T = create(T::class.java)
}