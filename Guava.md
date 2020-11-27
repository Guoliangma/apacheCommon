### Guava

#### Guava是什么？
* java开源库，提供用于集合，缓存，支持原语，并发性，常见注解，字符串处理，I/O和验证的实用方法。 
* 标准化-由google托管
* 高效、可靠
* 优化-经过高度优化

```java
<dependency> 
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
</dependency>
```

#### 常用类与接口
##### 1.集合的创建
```java
// 普通Collection的创建 
List<String> list = Lists.newArrayList();
Set<String> set = Sets.newHashSet(); 
Map<String, String> map = Maps.newHashMap(); 

// 不变Collection的创建
ImmutableList<String> iList = ImmutableList.of("a", "b", "c"); 
ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2"); 
ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");
```
***不可变集合***

* 在多线程操作下，是线程安全的。
* 所有不可变集合会比可变集合更有效的利用资源。
* 中途不可改变。

<u>**示例**</u>
```java
//以前
Map<String,List<Integer>> map = new HashMap<String,List<Integer>>(); 
List<Integer> list = new ArrayList<Integer>(); 
list.add(1); 
list.add(2); 
map.put("aa", list); S
System.out.println(map.get("aa"));//[1, 2]
```
```java
Multimap<String,Integer> map = ArrayListMultimap.create(); 
map.put("aa", 1); 
map.put("aa", 2); 
System.out.println(map.get("aa")); //[1, 2]
```
* **MultiSet:** 
  无序+可重复 count()方法获取重复的次数 增强了可读性+操作简单 
  创建方式: Multiset<String> set = HashMultiset.create(); 

* **Multimap:**
  key-value key可以重复 
  创建方式: Multimap<String, String> teachers = ArrayListMultimap.create(); 

* **BiMap:**
  双向Map(Bidirectional Map) 键与值都不能重复 
  创建方式: BiMap<String, String> biMap = HashBiMap.create();

* **Table:**
   双键的Map Map--> Table-->rowKey+columnKey+value //和sql中的联合主键有点像 
    创建方式: Table<String, String, Integer> tables = HashBasedTable.create();


##### 2.将集合转换为特定规则的字符串
```java
List<String> list = Lists.newArrayList("aa","bb","cc"); 
System.out.println(Joiner.on("-").join(list));
```

##### 3.将String转换为特定的集合
```java
String str = "1-2-3-4-5-6"; 
List<String> list = Splitter.on("-").splitToList(str); 
```
##### 4.将String转换为map
```java
String str = "xiaoming=11,xiaohong=23"; 
Map<String,String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
```
##### 5.guava还支持多个字符切割，或者特定的正则分隔
```java
String input = "aa.dd,,ff,,.";
List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
```
<u>**其它**</u>

```java
// 判断匹配结果 
boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true 
// 保留数字文本 
String s1 = CharMatcher.digit().retainFrom("abc 123 efg"); //123
// 删除数字文本 
String s2 = CharMatcher.digit().removeFrom("abc 123 efg"); //abc efg
```

##### 6.set的交集, 并集, 差集
```java
HashSet setA = newHashSet(1, 2, 3, 4, 5); 
HashSet setB = newHashSet(4, 5, 6, 7, 8); 
SetView union = Sets.union(setA, setB);  //union:12345867 
SetView difference = Sets.difference(setA, setB); //difference:123678
SetView intersection = Sets.intersection(setA, setB); //intersection:45
```
##### 7.检查参数
```java
//use java 
(str !=null && !str.isEmpty()) 
//use guava 
if(!Strings.isNullOrEmpty(str)) 

//use java 
if (count <= 0) { throw new IllegalArgumentException("must be positive: " + count); } 
//use guava 
Preconditions.checkArgument(count > 0, "must be positive: %s", count);
```
##### 8.计算中间代码的运行时间
```java
Stopwatch stopwatch = Stopwatch.createStarted();
for (int i = 0; i < 10000000; i++) {
    // watch
}
long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
System.out.println(nanos);
```
##### 9.文件操作
```java
File from=...;  
File to=...;  
Files.copy(from,to); //复制文件
Files.move(File from, File to); //移动文件 
URL url = Resources.getResource("abc.xml"); //获取classpath根下的abc.xml文件url
```
##### 10.Optional

Guava库设计了Optional来解决null含义模糊的问题
**(1).静态方法**

* **Optional.of(T)：** 获得一个Optional对象，其内部包含了一个非null的T数据类型实例，若T=null，则立刻报错。　　
* **Optional.absent()：** 获得一个Optional对象，其内部包含了空值　　
* **Optional.fromNullable(T)：** 将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空[Optional.fromNullable(null)]，和Optional.absent()等价。

**(2).实例方法**
* **boolean isPresent()：** 果Optional包含的T实例不为null，则返回true；若T实例为null，返回false
* **T get()：** 返回Optional包含的T实例，该T实例必须不为空；否则，对包含null的Optional实例调用get()会抛出一个IllegalStateException异常
* **or(T)：** ptional实例中包含了传入的T的相同实例，返回Optional包含的该T实例，否则返回输入的T实例作为默认值
* **T orNull()：** 返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，返回null，逆操作是fromNullable()
* **Set<T>  asSet()：** 不可修改的Set，该Set中包含Optional实例中包含的所有非空存在的T实例，且在该Set中，每个T实例都是单态，如果Optional中没有非空存在的T实例，返回的将是一个空的不可修改的Set。
```java
private void testOptional(){
    Optional<Long> value = method();
    if(value.isPresent()==true){
        System.out.println("获得返回值: " + value.get());     
    }else{
        System.out.println("获得返回值: " + value.or(-12L));    
    }

    System.out.println("获得返回值 orNull: " + value.orNull());

    Optional<Long> valueNoNull = methodNoNull();
    if(valueNoNull.isPresent()==true){
        Set<Long> set=valueNoNull.asSet();
        System.out.println("获得返回值 set 的 size : " + set.size());    
        System.out.println("获得返回值: " + valueNoNull.get());     
    }else{
        System.out.println("获得返回值: " + valueNoNull.or(-12L));    
    }

    System.out.println("获得返回值 orNull: " + valueNoNull.orNull());
}

private Optional<Long> method() {
    return Optional.fromNullable(null);
}
private Optional<Long> methodNoNull() {
    return Optional.fromNullable(15L);
}
```



* **参考资料：[https://www.yiibai.com/guava](https://www.yiibai.com/guava)**