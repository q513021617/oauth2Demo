package com.example.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class Config_Resource extends ResourceServerConfigurerAdapter {

    @Value("${uaa.clientId}")
    private String clientId;
    @Value("${uaa.clientSecret}")
    private String clientSecret;
    @Value("${uaa.tokenEndpoint}")
    private String tokenEndpoint;

    /**
     * ResourceID资源的标识
     */
    private static final String DEMO_RESOURCE_ID = "api";

    /**
     * 在每个ResourceServer实例上设置resourceId，该resourceId作为该资源的唯一标识
     * 验证服务器给Client第三方客户端授权时，可以设置这个Client可以访问哪些Resource-Server资源服务
     * 如没有设置就是对所有的Resource-Server都有访问权限
     *
     * resources.resourceId(DEMO_RESOURCE_ID)     //为每个ResourceServer(一个微服务实例)设置一个ResourceID
     * resources.stateless(true)                  //标记以指示在这些资源上仅允许基于令牌的身份验证
     * resources.tokenStore(xxx)                  //token的存储方式
     * resources.tokenExtractor(xxx)              //token获取方式,默认为BearerTokenExtractor
     * resources.authenticationEntryPoint(xxx);   //配置自定义的认证异常处理返回类
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    /**
     * 用来配置拦截保护的请求
     * 这里可以代替Spring Security同名方法配置
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()                       //禁用了csrf功能,关跨域保护
                .authorizeRequests()                //限定签名成功的请求
                .antMatchers("/api/**").authenticated()  //必须认证过后才可以访问
                .anyRequest().permitAll()       //其他没有限定的请求允许随意访问
                .and().anonymous();             //对于没有配置权限的其他请求允许匿名访问

    }

    /**
     * RemoteTokenServices是用于向远程认证服务器验证token，同时获取token对应的用户的信息
     * 通过RestTemplate调用远程服务，我们在使用这个类时，要设置checkTokenEndpointUrl、clientId、clientSecret等。
     * 只需要显示注入RemoteTokenServices remoteTokenServices()的Bean就可以调用授权服务器的/oauth/check_token端点查询token的合法性，之后返回其信息
     * 设置客户端配置的值
     */
    @Primary
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        //设置授权服务器check_token（验证token）端点完整地址
        tokenServices.setCheckTokenEndpointUrl(tokenEndpoint+"/oauth/check_token");
        //设置client_id与secret，注意client_secret值不能使用passwordEncoder加密
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        return tokenServices;
    }
}