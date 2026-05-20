package com.example.chapter2_core_and_view.lesson2room.utils

import kotlin.random.Random.Default.nextFloat

// 带频率的姓名项数据类
data class NameItem(val name: String, val frequency: Float)

/**
 * 基于公安部2020-2025年全国姓名统计数据
 * 我超，盒！
 */
object NameGenerator {
  // 姓氏数据（50个单姓+10个复姓，频率基于全国人口比例）
  private val lastNames = listOf(
    NameItem("王", 0.0794f), NameItem("李", 0.0741f), NameItem("张", 0.0707f),
    NameItem("刘", 0.0538f), NameItem("陈", 0.0471f), NameItem("杨", 0.0344f),
    NameItem("黄", 0.0251f), NameItem("赵", 0.0213f), NameItem("吴", 0.0211f),
    NameItem("周", 0.0200f), NameItem("徐", 0.0144f), NameItem("孙", 0.0130f),
    NameItem("马", 0.0126f), NameItem("朱", 0.0121f), NameItem("胡", 0.0117f),
    NameItem("郭", 0.0112f), NameItem("何", 0.0106f), NameItem("高", 0.0101f),
    NameItem("林", 0.0096f), NameItem("罗", 0.0086f), NameItem("郑", 0.0082f),
    NameItem("梁", 0.0078f), NameItem("谢", 0.0072f), NameItem("宋", 0.0068f),
    NameItem("唐", 0.0065f), NameItem("许", 0.0063f), NameItem("韩", 0.0061f),
    NameItem("冯", 0.0059f), NameItem("邓", 0.0056f), NameItem("曹", 0.0055f),
    NameItem("彭", 0.0053f), NameItem("曾", 0.0050f), NameItem("萧", 0.0049f),
    NameItem("田", 0.0048f), NameItem("董", 0.0047f), NameItem("潘", 0.0045f),
    NameItem("袁", 0.0044f), NameItem("蔡", 0.0043f), NameItem("蒋", 0.0042f),
    NameItem("余", 0.0041f), NameItem("于", 0.0040f), NameItem("杜", 0.0039f),
    NameItem("叶", 0.0038f), NameItem("程", 0.0037f), NameItem("苏", 0.0036f),
    NameItem("魏", 0.0035f), NameItem("吕", 0.0034f), NameItem("丁", 0.0033f),
    NameItem("沈", 0.0032f), NameItem("任", 0.0031f),
    NameItem("欧阳", 0.00064f), NameItem("上官", 0.000053f), NameItem("皇甫", 0.000038f),
    NameItem("司徒", 0.000032f), NameItem("诸葛", 0.000026f), NameItem("司马", 0.000020f),
    NameItem("夏侯", 0.000008f), NameItem("尉迟", 0.000005f), NameItem("公孙", 0.000003f),
    NameItem("慕容", 0.000002f)
  )

  private val firstNames = listOf(
    NameItem("伟", 0.0233f), NameItem("敏", 0.0197f), NameItem("静", 0.0171f),
    NameItem("杰", 0.0167f), NameItem("丽", 0.0163f), NameItem("勇", 0.0147f),
    NameItem("涛", 0.0144f), NameItem("艳", 0.0142f), NameItem("军", 0.0145f),
    NameItem("强", 0.0134f), NameItem("宇", 0.0080f), NameItem("泽", 0.0075f),
    NameItem("轩", 0.0070f), NameItem("浩", 0.0065f), NameItem("博", 0.0060f),
    NameItem("睿", 0.0055f), NameItem("晨", 0.0050f), NameItem("辰", 0.0048f),
    NameItem("涵", 0.0045f), NameItem("翰", 0.0042f), NameItem("欣", 0.0040f),
    NameItem("怡", 0.0038f), NameItem("佳", 0.0036f), NameItem("琪", 0.0034f),
    NameItem("瑶", 0.0032f), NameItem("雨", 0.0030f), NameItem("思", 0.0028f),
    NameItem("彤", 0.0026f), NameItem("萱", 0.0024f), NameItem("馨", 0.0022f),
    NameItem("秀英", 0.0620f), NameItem("桂英", 0.0560f), NameItem("秀兰", 0.0547f),
    NameItem("玉兰", 0.0524f), NameItem("婷婷", 0.0481f), NameItem("建华", 0.0448f),
    NameItem("桂兰", 0.0436f), NameItem("玉梅", 0.0415f), NameItem("秀珍", 0.0406f),
    NameItem("海燕", 0.0292f), NameItem("子轩", 0.0250f), NameItem("雨泽", 0.0240f),
    NameItem("浩宇", 0.0230f), NameItem("博文", 0.0220f), NameItem("睿泽", 0.0210f),
    NameItem("晨曦", 0.0200f), NameItem("梓涵", 0.0190f), NameItem("思远", 0.0180f),
    NameItem("俊豪", 0.0170f), NameItem("明轩", 0.0160f), NameItem("欣怡", 0.0150f),
    NameItem("佳琪", 0.0140f), NameItem("梦瑶", 0.0130f), NameItem("雨桐", 0.0120f),
    NameItem("思彤", 0.0110f), NameItem("雅萱", 0.0100f), NameItem("雨馨", 0.0090f),
    NameItem("梓萱", 0.0080f), NameItem("诗涵", 0.0070f), NameItem("若曦", 0.0060f)
  )

  // 预计算前缀和，走轮盘法摇号抽名字
  private val lastNamePrefixSum by lazy { calculatePrefixSum(lastNames) }
  private val firstNamePrefixSum by lazy { calculatePrefixSum(firstNames) }


  private fun calculatePrefixSum(items: List<NameItem>): FloatArray {
    val prefixSum = FloatArray(items.size)
    var sum = 0f
    items.forEachIndexed { index, item ->
      sum += item.frequency
      prefixSum[index] = sum
    }
    return prefixSum
  }


  private fun weightedRandom(items: List<NameItem>, prefixSum: FloatArray): NameItem {
    val random01 = nextFloat() // 生成01之间的浮点数（摇号）
    val randomNow = random01 * prefixSum.last()  // 加权后为本次摇号
    val index = prefixSum.binarySearch(randomNow).let { pos ->
      // 查不到时，返回 -pos - 1, pos为 最后一个 小于 element 的位置
      // 因此 实际的返回值为 -(pos + 1)，即 upper_bound，并用负数表明未搜到
      // -[-(pos + 1)]-1 = (pos+1)-1 = pos
      // 所以实际返回给 index 就一定是 lowerbound 的 pos
      if (pos < 0) -pos - 1 else pos
    }
    return items[index.coerceIn(0, items.lastIndex)]
  }

  /**
   * 生成一个随机姓名（符合真实人口分布）
   */
  fun generateRandomName(): String {
    val lastName = weightedRandom(lastNames, lastNamePrefixSum).name
    val firstName = weightedRandom(firstNames, firstNamePrefixSum).name
    return "$lastName$firstName"
  }

  /**
   * 生成指定数量的随机姓名
   */
  fun generateNames(count: Int): List<String> {
    require(count >= 0) { "Count must be non-negative" }
    return List(count) { generateRandomName() }
  }

  /**
   * 生成带频率信息的随机姓名项
   */
  fun generateRandomNameItem(): NameItem {
    val lastNameItem = weightedRandom(lastNames, lastNamePrefixSum)
    val firstNameItem = weightedRandom(firstNames, firstNamePrefixSum)
    val combinedFrequency = lastNameItem.frequency * firstNameItem.frequency
    return NameItem("${lastNameItem.name}${firstNameItem.name}", combinedFrequency)
  }
}