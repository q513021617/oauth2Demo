package com.example.client.controlller;

import com.example.client.model.WebUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<Object> getUserInfo() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();


        return ResponseEntity.ok(principal);
    }

}
