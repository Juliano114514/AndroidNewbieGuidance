## Lesson 2 扩展函数

### 1.2.1 原理

- 扩展函数是一种语法糖，允许为已有类 (kotlin或java类)添加新函数，无需继承该类，也无需使用装饰器模式
- 扩展函数不会**修改目标类的字节码**，也不会成为目标类的成员方法，编译器会把扩展函数**编译为普通的静态方法**

#### 示例：

``` kotlin
// 给String扩展一个统计数字个数的函数
fun String.countDigits(): Int {
    var count = 0
    for (c in this) { // this指代接收者（String实例）
        if (c.isDigit()) count++
    }
    return count
}

// 使用扩展函数
fun main() {
    val str = "abc123def456"
    println(str.countDigits()) // 输出6
}
```



``` java
// 编译后的等效 Java 代码：
public final class ExtensionFunctionKt{
    // 扩展函数被编译为静态方法，第一个参数是接收者（String）
    public static final int countDigits(String $this){
        int count = 0;
        for (char c : $this.toCharArray()){
            if (Character.isDigit(c)) count++;
        }
        return count;
    }
    
    public static final void main() {
        String str = "abc123def456";
        System.out.println(countDigits(str)); // 调用静态方法
    }
    
    public static void main(String[] args){
        main();
    }
}
```



#### 关键特性

-  **无法访问 `private / protected` 成员**：扩展函数本质是外部静态方法，只能访问目标类的`public`成员
-  **优先级低于类内同名函数**：如果目标类内已有同名同参数的成员函数，优先调用类内成员函数
-  **支持可空接收者**：可以为可空类型定义扩展函数（如`fun String?.isEmptyOrNull(): Boolean`），在函数内部处理 null；
-  **扩展属性同理**：扩展属性是 get/set 方法的语法糖，无幕后字段（backing field），必须显式实现 getter（var 类型还需 setter）。