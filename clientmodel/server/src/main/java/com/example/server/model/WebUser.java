package com.example.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;

@Data
@TableName("user_info") // @TableName中的值, 对应着数据库中的表名
public class WebUser implements UserDetails {

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

    public WebUser(String username, String password, Integer role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
