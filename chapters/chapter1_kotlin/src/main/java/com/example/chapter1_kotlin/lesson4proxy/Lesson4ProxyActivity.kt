package com.example.chapter1_kotlin.lesson4proxy

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.chapter1_kotlin.databinding.Lesson4ProxyLayoutBinding
import com.example.chapter1_kotlin.lesson4proxy.api.MyWeatherApi
import com.example.chapter1_kotlin.lesson4proxy.core.MiniRetrofit
import com.example.foundation.base.BaseActivity
import com.example.foundation.utils.toast.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Lesson4ProxyActivity : BaseActivity() {
    private lateinit var binding: Lesson4ProxyLayoutBinding

    private lateinit var miniRetrofit: MiniRetrofit
    private lateinit var api: MyWeatherApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Lesson4ProxyLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initProxyConfig()
        initListener()
    }

    private fun initProxyConfig() {
        // 返回null的话去baseUrl那里更新下key
        miniRetrofit = MiniRetrofit("https://v1.yiketianqi.com/")
        api = miniRetrofit.create(MyWeatherApi::class.java)
    }

    private fun initListener() {
        // 按钮点击事件
        binding.btnSendProxyRequest.setOnClickListener {
            val city = binding.etCityInput.text.toString().trim()

            if (city.isEmpty()) {
                ToastUtil.showWarn("请输入城市名称")
                return@setOnClickListener
            }

            // 更新 UI 为加载状态
            binding.tvProxyResult.text = "正在经过动态代理拦截组装请求，请稍候..."

            // 启动协程发起网络请求 (IO 线程)
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    // 会触发 InvocationHandler.invoke()
                    // 相当于一个通话转接的作用
                    val weatherData = api.getWeather(
                        appid = "97413444",
                        appsecret = "v9TSIPws",
                        city = city,
                        unescape = "1"
                    )

                    // 请求完毕，切回主线程更新 UI
                    withContext(Dispatchers.Main) {
                        if (weatherData != null) {
                            // 格式化输出数据 (仿照 NetUtil.setResult)
                            val resultText = buildString {
                                appendLine("=== MiniRetrofit 动态代理请求成功 ===")
                                appendLine("今日实时请求次数: ${weatherData.nums}")
                                appendLine("城市ID: ${weatherData.cityid}")
                                appendLine("城市: ${weatherData.city}")
                                appendLine("日期: ${weatherData.date}")
                                appendLine("星期: ${weatherData.week}")
                                appendLine("更新时间: ${weatherData.update_time}")
                                appendLine("天气情况: ${weatherData.wea}")
                                appendLine("天气标识: ${weatherData.wea_img}")
                                appendLine("实况温度: ${weatherData.tem}°C")
                                appendLine("白天温度(高温): ${weatherData.tem_day}°C")
                                appendLine("夜间温度(低温): ${weatherData.tem_night}°C")
                                appendLine("风向: ${weatherData.win}")
                                appendLine("风力: ${weatherData.win_speed}")
                                appendLine("风速: ${weatherData.win_meter}")
                                appendLine("空气质量: ${weatherData.air}")
                                appendLine("气压: ${weatherData.pressure}")
                                appendLine("湿度: ${weatherData.humidity}")
                            }
                            binding.tvProxyResult.text = resultText
                        } else {
                            binding.tvProxyResult.text = "请求失败，可能是 Gson 解析异常或网络不通"
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        binding.tvProxyResult.text = "请求发生异常：\n${e.message}"
                    }
                }
            }
        }
    }
}