package com.example.chapter1_kotlin.lesson3

/**
 * @Author: JULIANO
 * @CreateDate: 2026/3/9 15:26
 * @Description:
 */

enum class ConcurrencyType(val title: String) {
  PRODUCER_CONSUMER("生产者-消费者"),
  READER_WRITER("读者-写者"),
  PHILOSOPHER("哲学家就餐"),
  BARBER("理发师睡眠"),
  SMOKER("吸烟者"),
  SWITCH_PROCESS("交替执行")
}

// 动态参数配置实体
data class ParamConfig(
  val key: String,
  val name: String,
  val defaultValue: Int
)

// 定义每个模型所需的参数项
val ConcurrencyType.defaultParams: List<ParamConfig>
  get() = when (this) {
    ConcurrencyType.PRODUCER_CONSUMER -> listOf(
      ParamConfig("producers", "生产者数量", 3),
      ParamConfig("consumers", "消费者数量", 2),
      ParamConfig("target", "目标消费数", 100)
    )
    ConcurrencyType.READER_WRITER -> listOf(
      ParamConfig("readers", "读者数量", 5),
      ParamConfig("writers", "写者数量", 2)
    )
    // 其他模型可在此补充配置规模
    ConcurrencyType.PHILOSOPHER -> listOf(ParamConfig("count", "哲学家数量", 5))
    ConcurrencyType.BARBER -> listOf(ParamConfig("chairs", "等候椅数量", 3), ParamConfig("customers", "顾客总数", 10))
    ConcurrencyType.SMOKER -> listOf(ParamConfig("rounds", "演示轮数", 5))
    ConcurrencyType.SWITCH_PROCESS -> listOf(ParamConfig("target", "目标次数", 20))
  }