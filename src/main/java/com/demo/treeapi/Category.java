package com.demo.treeapi; // 如果你的包名不是这个，改成你自己的包名

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private int id;
    private String name;
    private int parentId;
    @JsonIgnoreProperties("children")
    private List<Category> children;

    public Category() {
        this.children = new ArrayList<>();
    }

    public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }

    // Getter 和 Setter（Spring Boot 返回 JSON 必须要有这些）
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    public List<Category> getChildren() { return children; }
    public void setChildren(List<Category> children) { this.children = children; }
}