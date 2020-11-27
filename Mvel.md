# Mvel

## Mvel是什么

简单来说是一种强大的表达式解析器。我们可以自己写一些表达式，交给mvel进行解析计算，得到这个表达式计算的值。

比如我们要进行一个加法运算。在java中我们这样写：

````java
int res = 1+1;  // 2
````

若我用mvel则这样写：

````java
Object res = MVEL.eval("1+1");  //2
````

是不是很吃惊😱。“1+1”就是一个表达式，第一种我们是硬编码实现的计算结果，但是第二种方案，直接给evel函数传递一个表达式字符串，直接能计算出结果。这样如果想计算1-1。直接传人不同的表达式即可。现在要计算'(2+2)*3+5/2'或'2>1?1+1:2+2'。来吧你硬编码试试这些计算？是不是又要多写几行代码，而且不便扩展。

你以为mvel只能做这些了？那就真的是太年轻了。目前mvel支持大量的语法，条件，循环等。还可以支持自定义函数，这就🐂了。那么我们工作中用这东西来干嘛？

- ### 在自定义数据流转中的使用

  数据流转就是不同对象间数据的转换。比如a对象数据通过某些规则转化为b对象数据。这说的是不是数据清洗？对，说的没错，但是数据清洗只是其中的一个具体项罢了。👍，来个图：

  ​       对象a        ---------------> 转换 -------------------> 对象b 

![image-20200908145817292](http://bed.thunisoft.com:9000/ibed/2020/09/08/AfzPNLivQ.png)

由图可以看出两个对象name和age都是一对一映射，但是目标对象不需要sex字段，但是多了一个出生年的字段，而且是通过年龄计算而来。下面我们就以代码来模拟一下这个转换过程,在这里我对象都用map来定义。

但是代码不够优雅。

````java
// 对象a
HashMap<Object, Object> a = Maps.newHashMap();
a.put("name", "zs");
a.put("age", 10);
a.put("sex", "女");

// 字段映射关系
HashMap<String, String> mapping = Maps.newHashMap();
mapping.put("name", "name");
mapping.put("age", "age");
mapping.put("birthYear", "2020-age");

// 目标对象
HashMap<Object, Object> b = Maps.newHashMap();
// k为目标表字段，v为转换规则
b.forEach((k, v) -> {
    Object reValue = MVEL.eval((String)v, a);
    b.put(k, reValue);
});

System.out.println("源对象" + a);
System.out.println("目标对象" + b);
````

- 将自定义函数注册

````java
static ParserContext context = new ParserContext();
static {
    //MvelTest是getCurrentYear函数的类
    Method[] declaredMethods = MvelTest.class.getDeclaredMethods();
    for(Method method : declaredMethods){
        context.addImport(method.getName(),method);
    }
}

````

```java
/**
 * 获取当前年份方法
 * @return
 */
public static Object getCurrentYear(){
    Calendar date = Calendar.getInstance();
    String year = String.valueOf(date.get(Calendar.YEAR));
    return year;
}
```

重构一下：

````java
// 对象a
HashMap<Object, Object> a = Maps.newHashMap();
a.put("name", "zs");
a.put("age", 10);
a.put("sex", "女");

// 字段映射关系
HashMap<String, String> mapping = Maps.newHashMap();
mapping.put("name", "name");
mapping.put("age", "age");
mapping.put("birthYear", "getCurrentYear()-age");

// 目标对象
HashMap<Object, Object> b = Maps.newHashMap();
// k为目标表字段，v为转换规则
mapping.forEach((k, v) -> {
    Object reValue = MVEL.executeExpression(MVEL.compileExpression(v, context), a);
    b.put(k, reValue);
});

System.out.println("源对象" + a);
System.out.println("目标对象" + b);

````

`compileExpression`的作用就是将我们的规则进行编译成`mvel`可以识别的一个过程

`birthYear`规则替换为`mapping.put("birthYear","getCurrentYear()-age");`执行得到相同的结果。

有了这些我们可以自定义更多的转换规则，还可以借此开发一套用户配置工具，根据用户自己的配置，进行相应的资源映射。得到想要的目标数据。



### 绩效考核项目中的运用

场景：绩效考核系统中有大量的计算指标是要做各式各样的统计计算，且这些指标的计算规则每年都要变

- 引入计算引擎表达式，大大减少人工维护成本

![image-20200908151646565](http://bed.thunisoft.com:9000/ibed/2020/09/08/AfzjDSunp.png)

没有引入之前：绩效系统的计算类

![image-20200908151816782](http://bed.thunisoft.com:9000/ibed/2020/09/08/AfzkXsZbV.png)

引入之后：

![image-20200908151930215](http://bed.thunisoft.com:9000/ibed/2020/09/08/Afzm8mxYP.png)

- 指标维护

  指标1：

  ![image-20200908152053822](http://bed.thunisoft.com:9000/ibed/2020/09/08/Afzneh64f.png)

指标2：

![image-20200908152129420](http://bed.thunisoft.com:9000/ibed/2020/09/08/AfzoGwLKb.png)