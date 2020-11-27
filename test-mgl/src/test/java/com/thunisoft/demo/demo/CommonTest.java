package com.thunisoft.demo.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thunisoft.demo.demo.entity.Book;

/**
 * 
 * CommonTest
 *
 * @description CommonUtils的单元测试
 * @author maguoliang
 * @date 2020年9月7日 下午11:47:34
 * @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Test
    public void test() {
        System.out.println("测试结果：");
//         this.testArr();
//         this.testStringUtils();
//         this.testClassUtils();
//         this.testNumberUtils();
//         this.testRandomStringUtils();
//         this.testStringEscapeUtils();
        /** commons-collection */
//         this.testOrderedMap();
//         this.testBidiMap();
//         this.testList();
        /** commons-Beanutils */
//         this.testBeanutils();
//         this.testMap2Bean();
        /** Codec */
//         this.testCodec();
        /** IO */
//         this.testIo();
        /** FileUtils() */
//         this.testFileUtils();
//        this.testCopyFile();
         this.testDeleteFile();
    }

    // 有时我们需要将两个数组合并为一个数组，用ArrayUtils就非常方便，示例如下：
    private void testArr() {
        String[] s1 = new String[] {"1", "2", "3"};
        String[] s2 = new String[] {"a", "b", "c"};

        String[] s = (String[])ArrayUtils.addAll(s1, s2);

        Stream.of(s).forEach(System.out::println);

        String str = ArrayUtils.toString(s);
        System.out.println(str + ">>" + str.length());
    }

    private void testStringUtils() {
        // 截取FROM之后的内容，并去掉空格
        String testTrim = StringUtils.substringAfter("SELECT * FROM PERSON ", "FROM").trim();
        System.out.println("截取FROM之后的内容，并去掉空格：" + testTrim);

        // 只能正整数.
        System.out.println("是否是正整数：" + StringUtils.isNumeric("-454534"));

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

    private void testClassUtils() {
        System.out.println("短类名：" + ClassUtils.getShortClassName(TestMglApplicationTests.class));
        System.out.println("包名：" + ClassUtils.getPackageName(TestMglApplicationTests.class));
    }

    // math
    private void testNumberUtils() {
        // 可以识别正负小数等 就别用stringutils里的isnumberic.
        System.out.println(NumberUtils.toInt("a", 0));
        System.out.println(NumberUtils.isNumber("-2.5"));
        System.out.println(NumberUtils.isNumber("2.5"));
    }

    private void testRandomStringUtils() {
        // 字母加数字
        System.out.println("字母加数字:" + RandomStringUtils.randomAlphanumeric(5));
        // 字母
        System.out.println("字母:" + RandomStringUtils.randomAlphabetic(5));
        // 数字
        System.out.println("数字:" + RandomStringUtils.randomNumeric(5));
    }

    private void testStringEscapeUtils() {
        // 转义反转义
        System.out.println("转义:" + StringEscapeUtils.escapeHtml("<html>"));
        System.out.println("反转义:" + StringEscapeUtils.unescapeHtml("&lt;html&gt;"));
    }

    @SuppressWarnings("unchecked")
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

    // 思考题：cloneBook2能不能为Null?
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

    // 思考题：如果xh 的 集合的元素是多个bean2Map会是什么样？
    @SuppressWarnings("rawtypes")
    private void testMap2Bean() {
        // 这个Map对象的key必须与Bean的属性相对应.
        Map<String, Object> map = Maps.newHashMap();
        map.put("xh", Lists.newArrayList("1","2"));
        map.put("name", "论语");
        map.put("price", 100);
        map.put("content", Lists.newArrayList("己所不欲,勿施于人。","233"));

        try {
            // 将map转化为对象属性.
            Book map2Bean = new Book();
            BeanUtils.populate(map2Bean, map);
            System.out.println("Map2Bean :" + map2Bean.toString());
            // 将对象转换为map.
            Map bean2Map = BeanUtils.describe(map2Bean);
            System.out.println("Bean2Map :" + bean2Map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 思考题：md5能不能解密？
    private void testCodec() {
        // 工具类
        System.out.println("test的Base64：" + Base64.encodeBase64String("test".getBytes()));
        System.out.println("解译test的Base64：" + new String(Base64.decodeBase64("dGVzdA=="), StandardCharsets.UTF_8));
        System.out.println("md5加密test：" + DigestUtils.md5Hex("test"));

        // 可以直接使用spring 提供的
        System.out.println("spring提供的Base64：" + Base64Utils.encodeToString("test".getBytes()));
        System.out
            .println("spring解译Base64：" + new String(Base64Utils.decodeFromString("dGVzdA=="), StandardCharsets.UTF_8));
    }

    @SuppressWarnings("deprecation")
    private void testIo() {
        InputStream in1 = null;
        InputStream in2 = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        InputStream in3 = null;
        InputStream in4 = null;
        try {
            in1 = new URL("http://www.baidu.com").openStream();
            System.out.println("in1字符串：" + IOUtils.toString(in1, StandardCharsets.UTF_8));

            // 比较两个流是否相等
            in2 = new URL("http://www.baidu.com").openStream();
            System.out.println("in1 和 in2 是否相等" + IOUtils.contentEquals(in1, in2));

            // 将字节从InputStream复制到OutputStream或者是ByteArrayOutputStream
            File fileTest = new File("D:/test.txt");
            inputStream = new FileInputStream(fileTest);
            File fileBlank = new File("D:/blank.txt");
            outputStream = new FileOutputStream(fileBlank);
            IOUtils.copy(inputStream, outputStream);

            // 从输入流中读取字节(通常返回输入流的字节数组的长度)
            in3 = new URL("http://www.baidu.com").openStream();
            byte[] buffer = new byte[100000];
            System.out.println("字节数组:" + IOUtils.read(in3, buffer));

            // 获得输入流的内容放回一个List<String>类型的容器，每一行为这个容器的一个入口，使用特定的字符集（如果为空就使用默认的字符集）
            in4 = new URL("http://www.baidu.com").openStream();
            List<String> list = IOUtils.readLines(in4, StandardCharsets.UTF_8);
            System.out.println("集合读取：");
            list.forEach(x -> System.out.println(x));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in1);
            IOUtils.closeQuietly(in2);
            IOUtils.closeQuietly(in3);
            IOUtils.closeQuietly(in4);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    private void testFileUtils() {
        List<String> lines;
        List<String> lines2;
        try {
            File testFile = new File("D:/test.txt");
            lines = FileUtils.readLines(testFile, "UTF-8");
            System.out.println("D盘Test:" + lines);

            File blankFile = new File("D:/blank.txt");
            lines2 = FileUtils.readLines(blankFile, "UTF-8");
            System.out.println("D盘blank:" + lines2);

            // 拷贝文件 --这里会覆盖--而非追加
            FileUtils.copyFile(testFile, blankFile);
            String lines3 = FileUtils.readFileToString(blankFile, "UTF-8");
            System.out.println("拷贝文件到D盘blank:" + lines3);

            // 写字符串到一个文件--此种为覆盖的方法
            String string = "Chosen One!";
            FileUtils.writeStringToFile(blankFile, string, "ISO-8859-1");
            String lines4 = FileUtils.readFileToString(blankFile, "UTF-8");
            System.out.println("覆盖内容到D盘blank:" + lines4);

            // 向文件追加内容.
            FileUtils.write(blankFile, "Son of the Chosen!", StandardCharsets.UTF_8, true);

            String lines5 = FileUtils.readFileToString(blankFile, "UTF-8");
            System.out.println("追加内容到D盘blank:" + lines5);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testCopyFile() {
        // 拷贝文件到某一路径
        File blankFile = new File("D:/blank.txt");
        try {
            FileUtils.copyFileToDirectory(blankFile, new File("E:/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testDeleteFile() {
        // 删除文件实例
        File file = new File(("E:/blank.txt"));
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
