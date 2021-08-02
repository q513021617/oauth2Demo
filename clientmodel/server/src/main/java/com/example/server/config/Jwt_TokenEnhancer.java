package com.example.server.config;

import com.example.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt内容增强器
 */
public class Jwt_TokenEnhancer implements TokenEnhancer {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, String> parameters = authentication.getOAuth2Request().getRequestParameters();
        String username = parameters.get("username");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userdata",userDetails);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(hashMap);
        return accessToken;
    }
}
