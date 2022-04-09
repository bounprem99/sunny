package com.sunnyweather.android.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

//定义一个统一的网络数据源访问入口，对所有网络请求的API进行封装
object SunnyWeatherNetwork {

    //创建PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    //调用PlaceService接口的searchPlaces()方法发起搜索城市数据请求
    suspend fun searchPlaces(query:String) = placeService.searchPlaces(query).await()

    //await()函数：挂起函数，将它声明为一个泛型T，并将await()函数定义成Call<T>的扩展函数，这样所有有返回值是Call类型的Retrofit网络请求接口都可以直接调用await()函数
    private suspend fun <T> Call<T>.await() :T {
        //suspendCoroutine函数：必须再协程作用域或挂起函数中才能调用，接受一个Lambda表达式参数，主要作用是将当前协程立即挂起，然后在一个普通的线程中执行Lambda表达式中的代码
        //Lambda表达式的参数列表上会传入一个continuation参数，调用它的resume()方法或resumeWithException()方法可以让协程恢复执行
        return suspendCoroutine {
            //调用enqueue()方法让Retrofit发起网络请求
            continuation -> enqueue(object : Callback<T>{
            //得到服务器返回的数据
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }
            //对异常情况进行处理
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }
            })
        }
    }
}