package com.example.chapter1_kotlin.lesson4.api

import com.example.chapter1_kotlin.lesson4.annotation.MyGET
import com.example.chapter1_kotlin.lesson4.annotation.MyQuery
import com.example.chapter1_kotlin.lesson4.data.WeatherData

interface MyWeatherApi {
    @MyGET("free/day")
    fun getWeather(
        @MyQuery("appid") appid: String,
        @MyQuery("appsecret") appsecret: String,
        @MyQuery("city") city: String,
        @MyQuery("unescape") unescape: String = "1"
    ): WeatherData?
}