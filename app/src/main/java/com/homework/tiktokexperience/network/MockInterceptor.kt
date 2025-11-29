package com.homework.tiktokexperience.network

import android.content.Context
import android.util.JsonReader
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.*
import org.json.JSONObject
import kotlin.random.Random

class MockInterceptor(private val context: Context):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()
        if(url.contains("page.json")){
            Log.d("MockInterceptor", "intercept: page.json")

            var json = readJsonFromAssets("test.json")
            var jsonObject = JSONObject(json)
            var jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                item.put("id", Random.nextInt(10000000,99999999).toString())
            }
            json = jsonObject.toString()
            //修改json，模拟唯一的信息id
            return Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .request(request)
                .body(ResponseBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    json
                )).build()
        }
        if(url.contains("love")){
            Log.d("MockInterceptor", "intercept: love")
            val json = readJsonFromAssets("click.json")
            return Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .request(request)
                .message("OK")
                .body(ResponseBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    json
                )).build()
        }
        return chain.proceed(request)
    }
    private fun readJsonFromAssets(fileName: String): String? {
        Log.d("MockInterceptor", "readJsonFromAssets: $fileName")
        return try {
            // 1. 通过 context 获取 AssetManager
            val inputStream = context.assets.open(fileName)
            // 2. 读取输入流并转换为字符串
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            // 3. 如果发生异常（如文件未找到），打印错误并返回 null
            e.printStackTrace()
            ""
        }
    }
}