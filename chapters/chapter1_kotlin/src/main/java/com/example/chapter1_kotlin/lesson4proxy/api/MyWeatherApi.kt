package com.example.chapter1_kotlin.lesson4proxy.api

import com.example.chapter1_kotlin.lesson4proxy.annotation.MyGET
import com.example.chapter1_kotlin.lesson4proxy.annotation.MyQuery
import com.example.chapter1_kotlin.lesson4proxy.data.WeatherData

interface MyWeatherApi {
    @MyGET("free/day")
    fun getWeather(
        @MyQuery("appid") appid: String,
        @MyQuery("appsecret") appsecret: String,
        @MyQuery("city") city: String,
        @MyQuery("unescape") unescape: String = "1"
    ): WeatherData?
}