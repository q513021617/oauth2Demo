package com.example.server.service.impl;

import cn.hutool.core.codec.Base64Encoder;
import com.example.server.model.WebUser;
import com.example.server.service.UserInfoService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        String passwordencode = Base64Encoder.encode("123456789456123".getBytes());
        return new WebUser(s, passwordencode,1);
    }
}
