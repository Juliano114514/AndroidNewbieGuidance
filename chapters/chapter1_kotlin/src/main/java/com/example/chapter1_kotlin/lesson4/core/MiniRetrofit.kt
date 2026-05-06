package com.example.chapter1_kotlin.lesson4.core

import com.example.chapter1_kotlin.lesson4.annotation.MyGET
import com.example.chapter1_kotlin.lesson4.annotation.MyQuery
import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class MiniRetrofit(private val baseUrl: String) {
   private val okHttpClient = OkHttpClient()
   private val gson = Gson()

   @Suppress("UNCHECKED_CAST")
   fun <T> create(service: Class<T>): T{
      // 动态代理这一块
      return Proxy.newProxyInstance(
         service.classLoader,
         arrayOf(service),
         object : InvocationHandler{
            override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? {
               // 如果调用的方法是来自 Object 类（比如 toString, equals），直接放行
               // 也就是放过自带方法，不做网络请求
               if(method.declaringClass == Any::class.java){
                  return method.invoke(this, *(args ?: emptyArray()))
               }

               // 解析方法注解，得到URL路径
               val myGetAnnotation = method.getAnnotation(MyGET::class.java)
                  ?: throw IllegalArgumentException("Method must have @MyGET annotation")
               val path = myGetAnnotation.value

               // 构建Url
               val urlBuilder = (baseUrl + path).toHttpUrlOrNull()?.newBuilder()
                  ?: throw IllegalArgumentException("Invalid Base URL")

               // 解析参数注解，拼装参数
               val paramsAnnotations = method.parameterAnnotations
               if(args != null && paramsAnnotations != null){
                  for(idx in paramsAnnotations.indices){
                     val annotations = paramsAnnotations[idx]
                     for(anno in annotations){
                        if(anno is MyQuery){
                           val key = anno.value
                           val value = args[idx].toString()
                           urlBuilder.addQueryParameter(key, value)
                        }
                     }
                  }
               }

               // 使用OkHttp发送真正请求（实际业务）
               val request = Request.Builder()
                  .url(urlBuilder.build())
                  .get()
                  .build()
               val response = okHttpClient.newCall(request).execute()
               val responseBodyString = response.body?.string()

               // Json结果转换类型
               if(response.isSuccessful){
                  val returnType = method.returnType
                  return gson.fromJson(responseBodyString, returnType)
               }

               return null
            }
         }
      ) as T
   }

}