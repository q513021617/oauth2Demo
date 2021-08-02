package com.example.clientModel.controlller;

import com.example.clientModel.model.WebUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    // 资源API
    @RequestMapping("/api/userinfo")
    public ResponseEntity<WebUser> getUserInfo() {
        WebUser user = (WebUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = user.getUsername() + "@spring2go.com";
        WebUser userInfo = new WebUser("admin","admin",0);
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(email);
        return ResponseEntity.ok(userInfo);
    }

}
