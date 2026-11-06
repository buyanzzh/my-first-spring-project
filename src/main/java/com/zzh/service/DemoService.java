package com.zzh.service;

import com.zzh.entity.Category;
import com.zzh.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 实验组入口：a 没有 @Transactional，a 里用 this 调 b
    public void a() {
        this.b("B-this调进来的");      // ⭐ 全部戏肉就在这一行：自己调自己类的方法
    }

    // b 有标签，谁调它炸了就该回滚——前提是"从外面"调它
    @Transactional
    public void b(String name) {
        Category c = new Category();
        c.setName(name);
        categoryMapper.insert(c);      // 先写库
        int x = 1 / 0;                 // 再炸
    }
    // 场景③：异常被吞 —— 事务照常提交，数据落库！
    @Transactional
    public void demoCatch() {
        Category c = new Category();
        c.setName("C-吞异常也落库");
        categoryMapper.insert(c);
        try {
            int x = 1 / 0;
        } catch (Exception e) {
            System.out.println("异常被我吞了");
        }
    }

}
