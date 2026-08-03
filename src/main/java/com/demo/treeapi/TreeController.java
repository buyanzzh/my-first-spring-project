package com.demo.treeapi;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TreeController {

    //private static final Logger log = LoggerFactory.getLogger(Category.class);

    @Autowired
    private TreeService treeService;

    // 全查询分类
    @GetMapping("/tree")
    public Category getTree() {
        log.info("收到全查询请求");
        return treeService.getTree();
//        return new Category();
    }

    // 查询分类（按ID）
    @GetMapping("/node/{id}")
    public Category getNodeById(@PathVariable int id) {
        log.info("收到单一查询请求");
        // 查数据库，只返回 id 等于这个参数的单个分类对象（不拼树）
        return treeService.getCategoryById(id);
    }

    // 新增分类
    @PostMapping("/category")
    public String addCategory(@RequestBody Category category) {
        log.info("收到新增请求");
        int result = treeService.addCategory(category);
        if (result == 1) {
            return "新增成功！";
        } else {
            return "新增失败！";
        }
    }

    // 删除分类（按ID）
    @DeleteMapping("/category/{id}")
    public String deleteCategory(@PathVariable int id) {
        log.info("收到删除请求，id：{}", id);
        boolean result = treeService.removeCategoryById(id);
        return result ? "删除成功！" : "删除失败！";
    }

    // 更新分类
    @PutMapping("/category")
    public String updateCategory(@RequestBody Category category) {
        log.info("收到更新请求，category：{}", category);
        boolean result = treeService.updateCategory(category);
        return result ? "更新成功！" : "更新失败！";
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/test-redis")
    public String testRedis() {
        redisTemplate.opsForValue().set("test-key", "Hello Redis");
        return redisTemplate.opsForValue().get("test-key");
    }
}