package com.zzh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String permKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermKey() {
        return permKey;
    }

    public void setPermKey(String permKey) {
        this.permKey = permKey;
    }
}
