package com.yjh.okhttp

import okhttp3.*
import org.junit.Test
import java.io.IOException

class CookieUnitTest {
    var cookieList: HashMap<String, List<Cookie>> = HashMap()

    //利用cookie保存用户登陆状态
    @Test
    fun cookieTest(){
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(object : CookieJar{ //添加cookie关键代码
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookies = cookieList.get(url.host)
                    if (cookies == null){
                        return ArrayList()
                    }
                    return cookies
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieList.put(url.host, cookies)
                }

            })
            .build()
        val formBody = FormBody.Builder().add("username", "yjhyjh").add("password", "123456").build()
        var request = Request.Builder().url("https://www.wanandroid.com/user/login").post(formBody).build()
        var call = okHttpClient.newCall(request)
        try {
            val response = call.execute()
            System.out.println("yjh1: " + response.body?.string())
        }catch (e: IOException){
            e.printStackTrace()
        }

        //发起请求，获取收藏文章列表
        request = Request.Builder().url("https://www.wanandroid.com/lg/collect/list/0/json").build()
        call = okHttpClient.newCall(request)
        try {
            val response = call.execute()
            System.out.println("yjh2: " + response.body?.string())
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}