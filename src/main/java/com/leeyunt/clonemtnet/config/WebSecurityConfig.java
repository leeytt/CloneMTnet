package com.leeyunt.clonemtnet.config;

import com.leeyunt.clonemtnet.jwt.JwtTokenFilter;
import com.leeyunt.clonemtnet.security.RestAuthenticationEntryPoint;
import com.leeyunt.clonemtnet.security.RestfulAccessDeniedHandler;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 *
 * spring security的核心配置
 *
 * @author leeyunt
 * @since 2020/01/17
 */

@EnableConfigurationProperties(value = {IgnoreUrlsSecurityConfig.class})
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true) //允许开启security的方法级别的安全
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Autowired
    private IgnoreUrlsSecurityConfig ignoreUrlsSecurityConfig;


    @Bean
    public JwtTokenFilter authenticationTokenFilterBean() throws Exception {
        return jwtTokenFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userDetailService ).passwordEncoder( new BCryptPasswordEncoder() );//密码编码工具,非常方便的实现密码的加密加盐
    }

    /**
     * HTTP请求安全处理
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity ) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();//开启登录配置
        List<String> ignoreUrlList = ignoreUrlsSecurityConfig.getUrls();
        /*不需要保护的资源路径允许访问*/
        for (String url :ignoreUrlList) {
            registry.antMatchers(url).permitAll();
        }
        /*允许跨域请求的OPTIONS请求*/
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();
        /*允许Swagger文档请求*/
        registry.antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                .antMatchers("/doc.html").permitAll();
        /*任何请求需要身份认证*/
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                /*关闭跨站请求防护及不使用session*/
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /*自定义权限拒绝处理类*/
                .and()
                .exceptionHandling()
                /*权限不足 如果权限不足,restfulAccessDeniedHandler,(排除白名单之外的接口)*/
                .accessDeniedHandler(restfulAccessDeniedHandler())
                /*未登录,如果未登录,会进入restAuthenticationEntryPoint中,(排除白名单之外的接口)*/
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                /*使用前文自定义的 Token过滤器*/
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().cacheControl();
    }

    /**
     * 权限不足时访问,返回
     * @return
     */
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    /**
     * 未登录时访问,返回
     * @return
     */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

}
