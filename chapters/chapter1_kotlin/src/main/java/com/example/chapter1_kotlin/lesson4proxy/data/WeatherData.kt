package com.example.chapter1_kotlin.lesson4proxy.data

import java.io.Serializable

class WeatherData(
    var nums: Int = 0,                    // 今日实时请求次数
    var cityid: String = "",             // 城市ID
    var city: String = "",               // 城市
    var date: String = "",               // 日期
    var week: String = "",               // 星期
    var update_time: String = "",        // 更新时间
    var wea: String = "",                // 天气情况
    var wea_img: String = "",            // 天气标识
    var tem: String = "",                // 实况温度
    var tem_day: String = "",            // 白天温度(高温)
    var tem_night: String = "",          // 夜间温度(低温)
    var win: String = "",                // 风向
    var win_speed: String = "",          // 风力
    var win_meter: String = "",          // 风速
    var air: String = "",                // 空气质量
    var pressure: String = "",          // 气压
    var humidity: String = ""           // 湿度
) : Serializable