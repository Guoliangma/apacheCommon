package com.thunisoft.demo.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.thunisoft.artery.data.annotation.ExceptionTips;

/**
 * Index Controller 首页面Controller
 *
 * @author Guoliang Ma
 * @date 2020-06-18
 */
@Controller
@RequestMapping("index")
public class IndexController {
	/**
	 * html入口
     * 注意：如直接访问到contextPath，默认走的SpringBoot的转发，不会走此方法了
	 * @return 'resources/templates/index.html'
	 */
    @GetMapping()
    public String index(HttpServletRequest request) {
        // 此处为同步获取数据的方式
    	request.setAttribute("rs1", rs1("Artery6", "新一代开发平台"));
        return "index";
    }
    /**
     * 数据源
     * @param name 姓名
     * @param desc 描述
     * @return
     */
    @ResponseBody
    @RequestMapping("/rs1")
    public Object rs1(String name, String desc) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("desc", desc);
        return json;
    }
    /**
     * 示例
     * @param param1 示例参数1
     * @param param2 示例参数2
     * @return
     */
    @ExceptionTips("请求example失败")
    @ResponseBody
    @GetMapping("/example")   
    public String example(String param1, String param2) {
        return "example data"; 
    }
}