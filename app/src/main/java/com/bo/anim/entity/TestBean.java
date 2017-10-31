package com.bo.anim.entity;

/**
 * Created by TT on 2017-10-30.
 */

public class TestBean {
    private String name;
    private String Content;

    public TestBean(String name, String content) {
        this.name = name;
        Content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
