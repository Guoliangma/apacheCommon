# Commons类库

commons: 一套开发源码、免费使用、商业友好的优秀API作为Java自带API的补充，大多数都是一些工具类

包括 
- Commons BeanUtils，针对Bean的一个工具集。由于Bean往往是有一堆get和set组成，所以BeanUtils也是在此基础上进行一些包装。
- Commons CLI，这是一个处理命令的工具。比如main方法输入的string[]需要解析。你可以预先定义好参数的规则，然后就可以调用CLI来解析。
- Commons Codec，这个工具是用来编码和解码的，包括Base64，URL，Soundx等等。
- Commons Collections，可以把这个工具看成是java.util的扩展。
- Commons Configuration，这个工具是用来帮助处理配置文件的，支持很多种存储方式
- Commons DbUtils，DbUtils就是把数据库操作单独做一个包这样的工具，以后开发不用再重复这样的工作了。这个工具并不是现在流行的OR-Mapping工具（比如Hibernate），只是简化数据库操作
- Commons FileUpload，文件上传。
- Commons HttpClient，这个工具可以方便通过编程的方式去访问网站
- Commons IO，可以看成是java.io的扩展
- Commons JXPath，JXpath就是基于Java对象的Xpath，也就是用Xpath对Java对象进行查询
- Commons Lang，这个工具包可以看成是对java.lang的扩展。提供了诸如StringUtils, StringEscapeUtils, RandomStringUtils, Tokenizer, WordUtils等工具类
- Commons Logging，提供了log4j
- Commons Math，这个包提供的功能有些和Commons Lang重复，但是这个包更专注于做数学工具，功能更强大
- Commons Net，这个包还是很实用的，封装了很多网络协议
- Commons Validator，用来帮助进行验证的工具。比如验证Email字符串，日期字符串等是否合法。
- Commons virtual File System 提供对各种资源的访问接口。支持的资源类型包括

平时常用的是commons-lang、commons-collection、commons-beanutils、commons-codec、commons-io，Commons Logging。

## commons-lang

**Lang** 主要是一些公共的工具集合，比如对字符、数组的操作等等

**1.ArrayUtils**

```java
// 有时我们需要将两个数组合并为一个数组，用ArrayUtils就非常方便，示例如下：  
    private void testArr() {  
        String[] s1 = new String[] { "1", "2", "3" };
        String[] s2 = new String[] { "a", "b", "c" };

        String[] s = (String[]) ArrayUtils.addAll(s1, s2);

        Stream.of(s).forEach(System.out::println);

        String str = ArrayUtils.toString(s);
        System.out.println(str + ">>" + str.length());
    }
```

**2. StringUtils**  

```java
private void testStringUtils() {
        // 截取FROM之后的内容，并去掉空格
        String testTrim = StringUtils.substringAfter("SELECT * FROM PERSON ", "FROM").trim();
        System.out.println("截取FROM之后的内容，并去掉空格：" + testTrim);

        // 只能正整数.
        System.out.println("是否是正整数：" + StringUtils.isNumeric("454534"));

        System.out.println("判断是否相等：" + StringUtils.equals("454534", "454534"));
        System.out.println("判断忽略大小写之后是否相等：" + StringUtils.equalsIgnoreCase("ABC534", "abc534"));

        // 判断是否是空格和null
        System.out.println("判断是否是空格和null：" + StringUtils.isBlank("   "));

        List<String> list = Lists.newArrayList("a", "b", "c");
        // 将数组中的内容以,分隔
        System.out.println("将数组中的内容以,分隔：" + StringUtils.join(list, ","));

        // 在右边加下字符,使之总长度为6
        System.out.println("在右边加下字符,使之总长度为6：" + StringUtils.rightPad("123", 6, '0'));

        // 首字母大写
        System.out.println("首字母大写：" + StringUtils.capitalize("abc"));

        // 删除所有空格
        System.out.println("删除所有空格：" + StringUtils.deleteWhitespace("   ab  c  "));

        // 判断是否包含这个字符
        System.out.println("判断是否包含这个字符：" + StringUtils.contains("abc", "ba"));

        // 表示左边两个字符
        System.out.println("表示左边两个字符：" + StringUtils.left("abc", 2));
    }
```

**3.ClassUtils**

```java
private void testClassUtils() {
    System.out.println("短类名：" + ClassUtils.getShortClassName(TestMglApplicationTests.class));  
    System.out.println("包名：" + ClassUtils.getPackageName(TestMglApplicationTests.class));  
}
```

**4.NumberUtils**     

```java
 // math
    private void testNumberUtils() {
        // 可以识别正负小数等 就别用stringutils里的isnumberic.
        System.out.println(NumberUtils.toInt("a", 0));
        System.out.println(NumberUtils.isNumber("-2.5"));
        System.out.println(NumberUtils.isNumber("2.5"));
    }
```

**5.RandomStringUtils**  

```java
private void testRandomStringUtils() {
        // 字母加数字
        System.out.println("字母加数字:" + RandomStringUtils.randomAlphanumeric(5));
        // 字母
        System.out.println("字母:" + RandomStringUtils.randomAlphabetic(5));
        // 数字
        System.out.println("数字:" + RandomStringUtils.randomNumeric(5));
    }
```

**6.StringEscapeUtils**

```java
 private void testStringEscapeUtils() {
        // 转义反转义
        System.out.println("转义:" + StringEscapeUtils.escapeHtml("<html>"));
        System.out.println("反转义:" + StringEscapeUtils.unescapeHtml("&lt;html&gt;"));
    }
```

## commons-collection

**Collections** 对java.util的扩展封装，处理数据还是挺灵活的。

org.apache.commons.collections – Commons Collections自定义的一组公用的接口和工具类

- bag – 实现Bag接口的一组类
- bidimap – 实现BidiMap系列接口的一组类
- buffer – 实现Buffer接口的一组类
- collection – 实现java.util.Collection接口的一组类
- comparators – 实现java.util.Comparator接口的一组类
- functors – Commons Collections自定义的一组功能类
- iterators – 实现java.util.Iterator接口的一组类
- keyvalue – 实现集合和键/值映射相关的一组类
- list – 实现java.util.List接口的一组类
- map – 实现Map系列接口的一组类
- set – 实现Set系列接口的一组类


```java
  private void testOrderedMap() {
        // 得到集合里按顺序存放的key之后的某一Key
        OrderedMap map = new LinkedMap();
        map.put("FIVE", "5");
        map.put("SIX", "6");
        map.put("SEVEN", "7");
        System.out.println(map.firstKey());
        System.out.println(map.nextKey("FIVE"));
        System.out.println(map.nextKey("SIX"));
    }
```

```java
private void testBidiMap() {
        BidiMap bidi = new TreeBidiMap();
        bidi.put("SIX", "6");
        // 通过key得到value
        System.out.println(bidi.get("SIX"));
        // 通过value得到key
        System.out.println(bidi.getKey("6"));
        // 将map里的key和value对调
        BidiMap inverse = bidi.inverseBidiMap();
        System.out.println(inverse);
    }
```

```java
 private void testList() {
         // 得到两个集合中相同的元素
         List<String> list1 = Lists.newArrayList("1", "2", "3");
         List<String> list2 = Lists.newArrayList("5", "2", "3");

         // 交集 map
         System.out.println("交集:" + CollectionUtils.intersection(list1, list2));
         // 交集,ListUtils
         System.out.println("交集:" + CollectionUtils.retainAll(list1, list2));
         // 并集
         System.out.println("并集:" + CollectionUtils.union(list1, list2));
         // 减
         System.out.println("集合1减集合2:" + CollectionUtils.subtract(list1, list2));
         // 加
         CollectionUtils.addAll(list1, list2.iterator());
         System.out.println("集合1加集合2:" + list1);
         list2.add(null);
         System.out.println("集合2加NULL:" + list2);
         // 不为空才加
         list2.forEach(key -> CollectionUtils.addIgnoreNull(list1, key));
         System.out.println("不为空才加:" + list1);
    }    
```

## commons-beanutils

**BeanUtils** 提供了对于JavaBean进行各种操作， 比如对象,属性复制等等。

**1、对象的克隆**

```java
private void testBeanutils() {
        Book book = new Book();
        book.setXh(Lists.newArrayList("1", "2"));
        book.setName("论语");
        book.setPrice(100);
        book.setContent(Lists.newArrayList("己所不欲,勿施于人。", "不患人之不己知,患不知人也。"));
        try {
            // 克隆，没克隆属性.
            Book cloneBook = (Book)BeanUtils.cloneBean(book);
            System.out.println("克隆：" + cloneBook.toString());
            // 不建议用apache赋值，会抛出异常,可用spring提供的不用try.注意参数顺序
            Book cloneBook2 = new Book();
            org.springframework.beans.BeanUtils.copyProperties(book, cloneBook2);
            System.out.println("赋值：" + cloneBook2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```

**2、 将一个Map对象转化为一个Bean，通过Java的反射机制来做的。**

不建议自己去写过多的反射Util,可以从以下工具类方法中去找适合自己的:

- PropertyUtils 
- BeanUtils
- ClassUtils
- FieldUtils
- ArtertyClassUtil
- ReflectUtil

```java
    private void testMap2Bean() {
        // 这个Map对象的key必须与Bean的属性相对应.
        Map<String, Object> map = Maps.newHashMap();
        map.put("xh", Lists.newArrayList("1"));
        map.put("name", "论语");
        map.put("price", 100);
        map.put("content", Lists.newArrayList("己所不欲,勿施于人。"));

        try {
            // 将map转化为对象属性.
            Book book = new Book();
            BeanUtils.populate(book, map);
            System.out.println("Map2Bean :" + book.toString());
            // 将对象转换为map.
            Map newMap = BeanUtils.describe(book);
            System.out.println("Bean2Map :" + newMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

```

## commons-codec

**Codec** 提供了一些公共的编解码实现，比如Base64, Hex, MD5,Phonetic and URLs等等。

```java
    System.out.println(Base64.encodeBase64String("test".getBytes()));
    System.out.println(new String(Base64.decodeBase64("dGVzdA=="), StandardCharsets.UTF_8));
    System.out.println(DigestUtils.md5Hex("asdfasdfasdfasfdasdfasdfasfasd"));

    // 可以直接使用spring 提供的
    System.out.println(Base64Utils.encodeToString("absd".getBytes()));
    System.out.println(new String(Base64Utils.decodeFromString("YWJzZA=="), StandardCharsets.UTF_8));
```

## common-io

**IO** 对java.io的扩展 操作文件非常方便。

```java
    //1．读取Stream
    //标准代码：  
    try (InputStream in = new URL( "http://jakarta.apache.org" ).openStream()) {
        InputStreamReader inR = new InputStreamReader(in);
        BufferedReader buf = new BufferedReader(inR);
        String line;
        while ((line = buf.readLine()) != null) {
            System.out.println(line);
        }
    }
```

**//使用IOUtils**  

```java
    try (InputStream in = new URL( "http://www.baidu.com").openStream()) {
        System.out.println( IOUtils.toString(in, StandardCharsets.UTF_8));
    }

    //比较两个流是否相等
    InputStream in = new URL("http://www.baidu.com").openStream();
    InputStream in2 = new URL("http://www.baidu.com").openStream();
    System.out.println(IOUtils.contentEquals(in, in2));

    //将字节从InputStream复制到OutputStream或者是ByteArrayOutputStream
    File src = new File("D:/test.txt");
    InputStream inputStream = new FileInputStream(src);
    File dest = new File("D:/blank.txt");
    OutputStream outputStream = new FileOutputStream(dest);
    IOUtils.copy(inputStream, outputStream);

    //从输入流中读取字节(通常返回输入流的字节数组的长度)
    InputStream in4 = new URL("http://www.baidu.com").openStream();
    byte[] buffer = new byte[100000];
    System.out.println(IOUtils.read(in4, buffer));

    //获得输入流的内容放回一个List<String>类型的容器，每一行为这个容器的一个入口，使用特定的字符集（如果为空就使用默认的字符集）
    InputStream in5 = new URL("http://www.baidu.com").openStream();
    List<String> list = IOUtils.readLines(in5, StandardCharsets.UTF_8);
    Iterator<String> iter = list.iterator();
    while(iter.hasNext()){
        System.out.println(iter.next());
    }
```

  **//使用FileUtils**

```java
    File src = new File("D:/test.txt");
    List lines = FileUtils.readLines(src, "UTF-8");
    System.out.println(lines);

    File dest = new File("D:/blank.txt");
    //拷贝文件 --这里会覆盖--而非追加
    FileUtils.copyFile(src, dest);

    //拷贝文件到某一路径
    FileUtils.copyFileToDirectory(src, new File("E:/"));

    //写字符串到一个文件--此种为覆盖的方法
    String string = "Blah blah blah";
    FileUtils.writeStringToFile(dest, string, "ISO-8859-1");

    // 向文件追加内容.
    FileUtils.write(dest, "hhhhhhhhh", StandardCharsets.UTF_8, true);

    //删除文件实例
    File file = new File( ("E:/test.txt") );
    FileUtils.forceDelete(file);  
```









