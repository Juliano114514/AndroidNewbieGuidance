package com.example.chapter1_kotlin.lesson4proxy.annotation

// 作用于方法上，保留到运行时（因为我们要用反射读取）
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyGET(val value: String)

// 作用于参数上
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyQuery(val value: String)