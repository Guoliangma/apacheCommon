package com.thunisoft.demo.demo.entity;

import java.util.List;

import com.thunisoft.demo.demo.Api;

@Api
public class Book {

    private List<String> xh;

    private String name;

    private Integer price;

    private List<String> content;

    public List<String> getXh() {
        return xh;
    }

    public void setXh(List<String> xh) {
        this.xh = xh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return com.alibaba.fastjson.JSON.toJSONString(this);
    }

}
