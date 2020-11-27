package com.thunisoft.demo.demo;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Maps;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MvelTest {

    private static ParserContext context = new ParserContext();

    static {
        // MvelTest是getCurrentYear函数的类
        Method[] declaredMethods = MvelTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            context.addImport(method.getName(), method);
        }
    }

    @Test
    public void test() {
         this.testMvel();
//         this.testData();
//        this.testData2();
    }

    private void testMvel() {
        System.out.println("测试结果：");
        Map<String, BigDecimal> context = Maps.newHashMap();
        context.put("a", new BigDecimal(100));
        context.put("b", new BigDecimal(200));
        context.put("c", new BigDecimal(300));
        context.put("x", new BigDecimal("0.1"));
        context.put("y", new BigDecimal("0.333"));
        String expression = "a*x + b*y + c";
        Object compileExpressionResult = MVEL.eval(expression, context);
        System.out.println(String.valueOf(compileExpressionResult));
    }

    private void testData() {
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
        mapping.forEach((k, v) -> {
            Object reValue = MVEL.eval((String)v, a);
            b.put(k, reValue);
        });

        System.out.println("源对象" + a);
        System.out.println("目标对象" + b);
    }

    /**
     * 获取当前年份方法
     * 
     * @return
     */
    public static Object getCurrentYear() {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        return year;
    }

    private void testData2() {
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
    }
}
