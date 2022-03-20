package com.yjh.okhttp

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.junit.Test
import java.io.File

//上传数据
//上传的数据类型contentType查询：https://www.runoob.com/http/http-content-type.html

class UploadFileUnitTest {

    //上传两个文件,用MultipartBody
    @Test
    fun uploadFileTest() {
        val okHttpClient = OkHttpClient()
        val file1 = File("E:\\learn\\AndroidProject\\OkHttp\\1.txt")
        val file2 = File("E:\\learn\\AndroidProject\\OkHttp\\2.txt")
        val multipartBody = MultipartBody.Builder()
            .addFormDataPart(
                "file1", file1.name, RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), file1 //这里的contentType（create的第一个参数）取决于上传的数据
                )
            )
            .addFormDataPart(
                "file2", file2.name, RequestBody.create(
                    "text/plain".toMediaTypeOrNull(), file2
                )
            )
            .build()
        val request =
            Request.Builder().url("https://www.httpbin.org/post").post(multipartBody).build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        System.out.println(response.body?.string())
    }


    //上传Json数据，用RequestBody
    @Test
    fun uploadJsonTest() {
        val okHttpClient = OkHttpClient()
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), "{\"a\":1,\"b\":2}")
        val request = Request.Builder().url("https://www.httpbin.org/post").post(requestBody).build()
        val call = okHttpClient.newCall(request)
        val response = call.execute()
        System.out.println(response.body?.string())
    }
}