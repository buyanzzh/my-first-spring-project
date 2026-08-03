package com.demo.treeapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class TreeService {
    //冲突test2,第三次测试

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 从数据库查出所有分类，拼成树，返回根节点
    public Category getTree() {
        // 1. 查询数据库
        String sql = "SELECT id, name, parent_id FROM category";
        //查询category表所有数据的sql语句
        List<Category> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            //jdbctemplate用于执行SQL语句,同statement
            Category c = new Category();
            //将查询到的数据存放在对象中
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setParentId(rs.getInt("parent_id"));
            return c;
        });

        // 2. 调用你上周写的拼树逻辑（一模一样）
        return buildTree(list);
    }

    // 这就是你上周在 TreeTest 里写的 buildTree 方法，一字没改
    private Category buildTree(List<Category> list) {
        Map<Integer, Category> map = new HashMap<>();
        //先遍历list,将数据存到map中
        for (Category node : list) {
            map.put(node.getId(), node);
        }

        //再遍历list,将parentid==某个id的节点作为该节点的孩子
        Category root = null;
        for (Category node : list) {
            if (node.getParentId() == 0) {
                root = node;
            } else {
                Category parent = map.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        return root;
    }

    public Category getCategoryById(int id) {
        System.out.println("传入的 id 是：" + id);
        //查询id=?的数据
        String sql = "SELECT id, name, parent_id FROM category WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Category c = new Category();
            //存放在对象里
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setParentId(rs.getInt("parent_id"));
            return c;
        }, id);
    }



    public int addCategory(Category category) {
        // 1. 定义插入的 SQL（用 ? 占位符防注入）
        String sql = "INSERT INTO category (id, name, parent_id) VALUES (?, ?, ?)";

        // 2. 执行更新操作，返回受影响的行数（成功插入就是 1）
        return jdbcTemplate.update(sql,
                category.getId(),
                category.getName(),
                category.getParentId()
        );
    }

    // 删除分类（按ID）
    public boolean removeCategoryById(int id) {
        log.info("Service层删除 id：{}", id);
        String sql = "DELETE FROM category WHERE id = ?";
        // 执行删除，返回影响行数
        int rows = jdbcTemplate.update(sql, id);
        // 删了1行及以上返回 true
        return rows > 0;
    }

    // 更新分类（全字段更新）
    public boolean updateCategory(Category category) {
        log.info("Service层更新数据：{}", category);
        String sql = "UPDATE category SET name = ?, parent_id = ? WHERE id = ?";
        int rows = jdbcTemplate.update(sql,
                category.getName(),
                category.getParentId(),
                category.getId()
        );
        return rows > 0;
    }
}