package com.example.simgpleauth2.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("user_info") // @TableName中的值, 对应着数据库中的表名
public class WebUser {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    String username;

    @TableField(value = "password")
    String password;

    @TableField(value = "role")
    Integer role;

    @TableField(value = "phone")
    String phone;

    //1，男  2，女
    @TableField(value = "sex")
    Integer sex;

    @TableField(value = "email")
    String email;

    @TableField(value = "avatar")
    String avatar;

    @TableField(value = "created")
    Date created;

    @TableField(value = "updated")
    Date updated;

}
