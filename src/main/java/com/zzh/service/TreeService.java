package com.zzh.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzh.entity.Category;
import com.zzh.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class TreeService {

    @Autowired
    CategoryMapper categoryMapper;

    // 从数据库查出所有分类，拼成树，返回根节点
    @Cacheable(value = "category" , key = "'allTree'")
    public Category getTree() {
        log.info("查询全量菜单树 - 走数据库");
        // 1. 查询数据库
        //查询category表所有数据的sql语句
        List<Category> list = categoryMapper.selectList(
                new LambdaQueryWrapper<Category>()
                        .select(Category::getId, Category::getName, Category::getParentId)
                        .orderByAsc(Category::getId));

        // 2. 调用你写的拼树逻辑
        return buildTree(list);
    }

    // 这就是你写的 buildTree 方法
    private Category buildTree(List<Category> list) {
        log.info("buildTree 收到 {} 行", list.size());
        Map<Integer, Category> map = new HashMap<>();
        //先遍历list,将数据存到map中
        for (Category node : list) {
            map.put(node.getId(), node);
        }

        //再遍历list,将parentid==某个id的节点作为该节点的孩子
        Category root = null;
        for (Category node : list) {
            Integer pid = node.getParentId();
            if (pid == null || pid == 0) {
                root = node;
            }
            else {
                Category parent = map.get(pid);
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }
        return root;
    }

    // 根据ID获取分类
    @Cacheable(value = "category" , key = "#id")
    public Category getCategoryById(int id) {
        log.info("Service层查询数据：{}", id);
        Category category = categoryMapper.selectById(id);
        return category;

    }

    // 添加分类
    @CacheEvict(value = "category" , allEntries = true)
    public int addCategory(Category category) {
        log.info("Service层添加数据：{}", category.toString());
        int insert = categoryMapper.insert(category);
        return insert;
    }

    // 删除分类（按ID）
    @CacheEvict(value = "category" , allEntries = true)
    public boolean removeCategoryById(int id) {
        log.info("Service层删除 id：{}", id);

        int i = categoryMapper.deleteById(id);
        return i > 0;

    }

    // 更新分类（全字段更新）
    @CacheEvict(value = "category" , allEntries = true)
    public boolean updateCategory(Category category) {
        log.info("Service层更新数据：{}", category);

        int update = categoryMapper.updateById(category);
        return update > 0;

    }
}