package com.thunisoft.demo.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

/**
 * 
 * GuavaTest
 *
 * @description Guava工具类的测试
 * @author maguoliang
 * @date 2020年9月8日 上午01:42:31
 * @version V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GuavaTest {

    @Test
    public void test() {
//         this.testMap();
//         this.testList1();
//         this.testList2();
//        this.testString2Map();
//        this.testStopwatch();
//        this.testOptional();
    }

    private void testCollection() {
        // 普通Collection的创建
        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        Map<String, String> map = Maps.newHashMap();

        // 不变Collection的创建
        ImmutableList<String> iList = ImmutableList.of("a", "b", "c");
        ImmutableSet<String> iSet = ImmutableSet.of("e1", "e2");
        ImmutableMap<String, String> iMap = ImmutableMap.of("k1", "v1", "k2", "v2");
    }

    private void testMap() {
        // 没用Guava
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        map.put("aa", list);
        System.out.println("没用Guava：" + map.get("aa"));

        // 用Guava
        Multimap<String, Integer> mapGuava = ArrayListMultimap.create();
        mapGuava.put("aa", 1);
        mapGuava.put("aa", 2);
        System.out.println("用了Guava：" + mapGuava.get("aa"));
    }

    // 将集合转换为特定规则的字符串
    private void testList1() {
        List<String> list = Lists.newArrayList("aa", "bb", "cc");
        System.out.println(Joiner.on("-").join(list));
    }

    // 将String转换为特定的集合
    private void testList2() {
        String str = "1-2-3-4-5-6";
        List<String> list = Splitter.on("-").splitToList(str);
        System.out.println(list);
    }

    private void testString2Map() {
        String str = "xiaoming=11,xiaohong=23";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(str);
        System.out.println(map.toString());
    }

    private void testStopwatch() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 10000000; i++) {
            // watch
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }
    
    private void testOptional(){
        Optional<Long> value = Optional.fromNullable(null);
        if(value.isPresent()==true){
            System.out.println("1.获得返回值: " + value.get());     
        }else{
            System.out.println("2.获得返回值: " + value.or(-12L));    
        }
        
        System.out.println("3.获得返回值 orNull: " + value.orNull());
        
        Optional<Long> valueNoNull = Optional.fromNullable(15L);
        if(valueNoNull.isPresent()==true){
            Set<Long> set=valueNoNull.asSet();
            System.out.println("4.获得返回值 set 的 size : " + set.size());    
            System.out.println("5.获得返回值: " + valueNoNull.get());     
        }else{
            System.out.println("6.获得返回值: " + valueNoNull.or(-12L));    
        }
        
        System.out.println("7.获得返回值 orNull: " + valueNoNull.orNull());
    }
    
}
