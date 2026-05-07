## 什么是注解
    - 在Kotlin中，注解（annotation）是一种元数据
    - 注解主要是给机器看的，影响编译和运行
    - 注解可以附加到代码的 类/函数/属性 等地方，以提供额外的信息，注解本身不执行任何操作
    - 通过注解，开发者可以在代码中嵌入额外的信息，在编译时或运行时处理，以实现更灵活和动态的功能

## 注解的传参
    - 注解的传参可以是基本的数据类型（Int,String）、枚举类型，其他注解类型，数组类型
    - 参数必须是编译时常量，因此不能使用动态计算的值或者可变变量
    - 传参时可以指定参数，用的时候记得传参（或者设好缺省值）
    - 可以通过反射读取注解的参数值，从而读取附加在代码上的信息

## 注解有啥用
    - 编译时可以执行特定的检查/生成代码
    - 如果保留策略为`RUNTIME`，在运行时可以通过反射读取注解
    - 通过反射库，可以拿到类/方法/属性上的注解与参数值

## 使用例
```kotlin
@MyAnnotation(name = "John")
fun myFunction() { /* ... */ }
val method = MyClass::class.java.getMethod("myFunction")  // 获取方法对象
val annotation = method.getAnnotation(MyAnnotation::class.java) // 获取注解
println(annotation?.name)  // 输出 "John"
```