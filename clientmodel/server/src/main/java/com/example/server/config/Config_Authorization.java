package com.example.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class Config_Authorization extends AuthorizationServerConfigurerAdapter {

    @Value("${myoauth2.clientId}")
    private String clientId;
    @Value("${myoauth2.clientSecret}")
    private String clientSecret;

    private static final String DEMO_RESOURCE_ID = "api";

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManagerBean")
    AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userInfoServiceImpl")
    UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

//============redis存储token===============
//    @Resource
//    private RedisConnectionFactory redisConnectionFactory;
//============redis存储token===============


    //=============JWT存储token==================


    @Resource
    private JwtAccessTokenConverter accessTokenConverter;
    @Resource
    private Jwt_TokenEnhancer jwtTokenEnhancer;
    //=============JWT存储token==================

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }




    /**
     * 访问端点配置
     * 配置授权authorization以及令牌(token)的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置Redis存储token
        //endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory));

        //配置Jwt存储token + Jwt自定义增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, accessTokenConverter));
        endpoints.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter).tokenEnhancer(tokenEnhancerChain);

        //配置管理器允许GET和POST请求端点oauth/token获取Token
        endpoints.authenticationManager(authenticationManager).allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


    /**
     * 授权端点开放，配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")              //开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("isAuthenticated()")      //开启/oauth/check_token验证端口认证权限访问
                .allowFormAuthenticationForClients();       //允许表单认证
    }

    /**
     * 配置客户端详情服务
     * 客户端详情信息在这里进行初始化, 通过数据库来存储调取详情信息
     * 在验证服务器为客户端client配置resourceIds的目的是：限制某个client可以访问的资源服务。
     * 当请求发送到Resource Server的时候会携带access_token，
     * Resource Server会根据access_token找到client_id，进而找到该client可以访问的resource_ids。
     * 如果resource_ids包含ResourceServer自己设置ResourceID，这关就过去了，就可以继续进行其他的权限验证
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用内存模式; 也可以配置客户端存储到数据库DB,你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
        clients.inMemory()
                .withClient(clientId)                                                   //client_id
                .secret(passwordEncoder.encode(clientSecret))                           //client_密码
                .resourceIds(DEMO_RESOURCE_ID)                                          //配置资源的id
                .authorizedGrantTypes("client_credentials")                             //授权类型:客户端模式
                .scopes("all")                                                          //配置申请的权限范围
                .authorities("client");                                                 //客户端可以使用的权限

    }
}
