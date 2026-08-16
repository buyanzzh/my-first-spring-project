package com.zzh;

import com.zzh.entity.Category;
import com.zzh.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class MybatisPlusTest {
    public static void main(String[] args) {
        List<Integer> res = new LinkedList<>();
        res.add(1);
        res.add(2);
        res.add(3);
        boolean contains = res.contains(2);
        for (int i = 0; i < res.size(); i++) {
            
        }

    }


    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void testSelectList(){
        List<Category> list = categoryMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        Category category = new Category(null,"电子手表",1);
        int result = categoryMapper.insert(category);
        System.out.println("插入影响的行数是" + result);
        System.out.println("自动生成的id是" + category.getId());
    }

    @Test
    public void testDelete(){
        int res = categoryMapper.deleteById(6);
        System.out.println("删除影响的行数是" + res);
    }

    @Test
    public void SelectById(){
        System.out.println(categoryMapper.selectById(1));
    }

    @Test
    public void SelectByIds(){
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        System.out.println(categoryMapper.selectBatchIds(ids));
    }

    @Test
    public void SelectByMap(){
        Map<String , Object> map = new HashMap<>();
        map.put("parent_id",2);
        map.put("name","华为");
        System.out.println(categoryMapper.selectByMap(map));
    }
}
