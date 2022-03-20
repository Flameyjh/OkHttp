package com.yjh.okhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        okHttpClient = OkHttpClient()
    }

    //get同步请求
    fun getSync(view: View){
        Thread {
            run {
                val request = Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build()
                //准备好请求的Call对象
                val call = okHttpClient.newCall(request)
                try{
                    val response = call.execute() //execute发起同步请求
                    Log.i(TAG, "getSync:" + response.body?.string())
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }.start()
    }

    //get异步请求
    fun getASync(view: View) {
        val request = Request.Builder().url("https://www.httpbin.org/get?a=1&b=2").build()
        //准备好请求的Call对象
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback{ //enqueue发起异步请求，内部自动创建子线程
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    Log.i(TAG, "getAsync:" + response.body?.string())
                }
            }
        })
    }

    //post同步请求
    fun postSync(view: View) {
        Thread {
            run {
                val formBody = FormBody.Builder().add("a", "1").add("b", "2").build()
                val request = Request.Builder().url("https://www.httpbin.org/post").post(formBody).build()
                //准备好请求的Call对象
                val call = okHttpClient.newCall(request)
                try{
                    val response = call.execute() //execute发起同步请求
                    Log.i(TAG, "postSync:" + response.body?.string())
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }.start()
    }

    //post异步请求
    fun postAsync(view: View) {
        val formBody = FormBody.Builder().add("a", "1").add("b", "2").build()
        val request = Request.Builder().url("https://www.httpbin.org/post").post(formBody).build()
        //准备好请求的Call对象
        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback{ //enqueue发起异步请求，内部自动创建子线程
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    Log.i(TAG, "postAsync:" + response.body?.string())
                }
            }
        })
    }
}