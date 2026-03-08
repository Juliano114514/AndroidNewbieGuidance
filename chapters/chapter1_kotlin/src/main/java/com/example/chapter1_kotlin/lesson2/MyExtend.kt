package com.example.chapter1_kotlin.lesson2

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/1 16:01
 * @Description:
 */


/**
 * myLet
 * 传参方式 (T) -> R, 调用自身用it
 * 返回 R （lambda 的最后一行结果）
 */
inline fun <T, R> T.myLet(block: (T) -> R): R {
  // 传入this，调用 it
  return block(this)
}

/**
 * myRun
 * 传参方式 T.() -> R，调用自身用this
 * 返回 R （最后一行结果）
 */
inline fun <T,R> T.myRun(block: T.() -> R): R{
  // 在this的上下文中直接执行 block
  return this.block()
}

/**
 * myAlso
 * 传参方式 (T) -> Unit  普通参数，用it
 * block 没有返回值， also 的返回值为调用对象本身 this
 * 因此模板参数只有一个T，不需要返回泛型R
 */
inline fun <T> T.myAlso(block: (T) -> Unit): T{
  block(this) // block 是类似装饰器/代理一样的实现
  return this // 返回调用对象本身
}

/**
 * myApply
 * 传参方式 T.() -> Unit 带接收者的函数，用this
 * 返回值T，方便链式调用
 */
inline fun <T> T.myApply(block: T.() -> Unit): T{
  this.block()
  return this
}

/**
 * 将集合中的每一个元素 T 转换为 R
 */
inline fun <T, R> Iterable<T>.myMap(transform: (T) -> R): List<R> {
  val destination = ArrayList<R>()
  for(item in this){
    destination.add(transform(item))
  }
  return destination
}

/**
 * myFilter
 * 将集合中满足 predicate 条件的元素筛选出来组成新 List
 * 传参方式 (T) -> Boolean
 */
inline fun <T> Iterable<T>.myFilter(predicate: (T) -> Boolean): List<T>{
  val destination = ArrayList<T>()
  for(item in this){
    if(predicate(item)){
      destination.add(item)
    }
  }
  return destination
}
