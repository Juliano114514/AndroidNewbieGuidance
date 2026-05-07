package com.example.lib_ksp_processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.validate
import java.io.OutputStream

// Processor提供者，KSP 框架通过它来实例化 Processor
class MyEntityProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return MyEntityProcessor(environment.codeGenerator, environment.logger)
  }
}

// 核心Processor，负责过滤注解修饰的类
class MyEntityProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger
) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    // 找到所有注解修饰的符号
    val symbols = resolver.getSymbolsWithAnnotation("com.example.lib_ksp_annotation.MyEntity")
    val unableToProcess = symbols.filterNot { it.validate() }.toList()

    // 遍历所有合法的类声明
    symbols.filter { it is KSClassDeclaration && it.validate() }
      .forEach { it.accept(MyEntityProcessorVisitor(codeGenerator, logger), Unit) }

    return unableToProcess
  }

  class MyEntityProcessorVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
  ) : KSVisitorVoid() {
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
      // 获取包名和类名
      val packageName = classDeclaration.packageName.asString()
      val className = classDeclaration.simpleName.asString()
      val generatedClassName = "${className}_TableInfo"

      // 获取 @MyEntity 注解中的 tableName 参数
      val entityAnnotation = classDeclaration.annotations.find { it.shortName.asString() == "MyEntity" }
      val tableNameArg = entityAnnotation?.arguments?.find { it.name?.asString() == "tableName" }?.value as? String
      val tableName = if (tableNameArg.isNullOrEmpty()) className else tableNameArg

      // 创建生成文件
      val file: OutputStream = codeGenerator.createNewFile(
        dependencies = Dependencies(true, classDeclaration.containingFile!!),
        packageName = packageName,
        fileName = generatedClassName
      )

      // 拼接要生成的 Kotlin 代码
      file.write("package $packageName\n\n".toByteArray())
      file.write("object $generatedClassName {\n".toByteArray())
      file.write("    const val TABLE_NAME = \"$tableName\"\n\n".toByteArray())
      file.write("    fun printInfo() {\n".toByteArray())
      file.write("        println(\"==== 表名: \$TABLE_NAME ====\")\n".toByteArray())

      // 遍历类中的所有属性，寻找 @MyColumn
      classDeclaration.getAllProperties().forEach { prop ->
        val columnAnn = prop.annotations.find { it.shortName.asString() == "MyColumn" }
        if (columnAnn != null) {
          val propName = prop.simpleName.asString()
          val colNameArg = columnAnn.arguments.find { it.name?.asString() == "columnName" }?.value as? String
          val colTypeArg = columnAnn.arguments.find { it.name?.asString() == "type" }?.value as? String

          val colName = if (colNameArg.isNullOrEmpty()) propName else colNameArg
          val colType = colTypeArg ?: "TEXT"

          file.write("        println(\"字段: $colName, 类型: $colType\")\n".toByteArray())
        }
      }

      file.write("    }\n".toByteArray())
      file.write("}\n".toByteArray())
      file.close()
    }

  }
}
