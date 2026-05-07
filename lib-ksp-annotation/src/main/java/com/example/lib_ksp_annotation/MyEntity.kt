package com.example.lib_ksp_annotation
/**
 * 标记一个类为数据库实体表
 */
@Target(AnnotationTarget.CLASS) // 对类作用的注解
@Retention(AnnotationRetention.SOURCE) // SOURCE 级别，不会编译进最终的 APK，零运行时损耗
annotation class MyEntity(val tableName: String = "")

/**
 * 标记一个属性为表字段
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class MyColumn(val columnName: String = "", val type: String = "TEXT")