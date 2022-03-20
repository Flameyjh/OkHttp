package com.yjh.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import java.io.IOException

class InterceptorUnitTest {

    //添加拦截器，完成一些发送请求时的统一处理
    //无论摆放顺序，addInterceptor比addNetworkInterceptor先执行
    @Test
    fun interceptorTest() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response { //intercept回调给我们一个chain对象
                    //前置处理，比如添加请求头
                    val request = chain.request().newBuilder().addHeader("os", "android")
                        .addHeader("version", "1.0").build()
                    val response = chain.proceed(request)
                    //后置处理
                    return response
                }

            })
            .addNetworkInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    System.out.println("yjh" + chain.request().header("version"))
                    return chain.proceed(chain.request())
                }

            }).build()

        //get请求
        val request = Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build()
        val call = okHttpClient.newCall(request)
        try {
            val response = call.execute() //execute发起同步请求
            System.out.println(response.body?.string())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}