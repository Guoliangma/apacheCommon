package com.thunisoft.demo.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Stopwatch;
import com.thunisoft.demo.demo.controller.IndexController;
import com.thunisoft.demo.demo.entity.Book;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HutoolTest {

    @Test
    public void test() {
//        this.testMd5();
        this.testAnnotationUtil();
    }
    
    private void testMd5() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String testMd5 = SecureUtil.md5("test");
        System.out.println("Hutool:" + testMd5);
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("Hutool耗时：" + nanos);
        
        Stopwatch stopwatch2 = Stopwatch.createStarted();
        String testMd5ByCommon = DigestUtils.md5Hex("test");
        System.out.println("apache:" + testMd5ByCommon);
        long nanos2 = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println("apache耗时：" + nanos2);
    }

    private void testConvert() {
        //转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        //转换为指定类型数组
        String[] b = {"1", "2", "3", "4"};
        Integer[] bArr = Convert.toIntArray(b);
        //转换为日期对象
        String dateStr = "2017-05-06";
        Date date = Convert.toDate(dateStr);
        //转换为列表
        String[] strArr = {"a", "b", "c", "d"};
        List<String> strList = Convert.toList(String.class, strArr);
    }

    private void testDateUtil() {
        //Date、long、Calendar之间的相互转换
        //当前时间
        Date date = DateUtil.date();
        //Calendar转Date
        date = DateUtil.date(Calendar.getInstance());
        //时间戳转Date
        date = DateUtil.date(System.currentTimeMillis());
        //自动识别格式转换
        String dateStr = "2017-03-01";
        date = DateUtil.parse(dateStr);
        //自定义格式化转换
        date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        //格式化输出日期
        String format = DateUtil.format(date, "yyyy-MM-dd");
        //获得年的部分
        int year = DateUtil.year(date);
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        //获取某天的开始、结束时间
        Date beginOfDay = DateUtil.beginOfDay(date);
        Date endOfDay = DateUtil.endOfDay(date);
        //计算偏移后的日期时间
        Date newDate = DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        //计算日期时间之间的偏移量
        long betweenDay = DateUtil.between(date, newDate, DateUnit.DAY);
    }

    private void testStringUtil() {
        //判断是否为空字符串
        String str = "test";
        StrUtil.isEmpty(str);
        StrUtil.isNotEmpty(str);
        //去除字符串的前后缀
        StrUtil.removeSuffix("a.jpg", ".jpg");
        StrUtil.removePrefix("a.jpg", "a.");
        //格式化字符串
        String template = "这只是个占位符:{}";
        String str2 = StrUtil.format(template, "我是占位符");
    }

    private void testReflectUtil() {
        //获取某个类的所有方法
        Method[] methods = ReflectUtil.getMethods(Book.class);
        //获取某个类的指定方法
        Method method = ReflectUtil.getMethod(Book.class, "getName");
        //使用反射来创建对象
        Book book = ReflectUtil.newInstance(Book.class);
        //反射执行对象的方法
        ReflectUtil.invoke(book, "setId", 1);
    }

    private void testAnnotationUtil() {
        //获取指定类、方法、字段、构造器上的注解列表
        Annotation[] annotationList = AnnotationUtil.getAnnotations(IndexController.class, false);
        //获取指定类型注解
        Api api = AnnotationUtil.getAnnotation(Book.class, Api.class);
        System.out.println("获取类型" + api.description());
        //获取指定类型注解的值
        Object annotationValue = AnnotationUtil.getAnnotationValue(IndexController.class, RequestMapping.class);
        System.out.println("获取值：" + annotationValue.toString());
    }
}
