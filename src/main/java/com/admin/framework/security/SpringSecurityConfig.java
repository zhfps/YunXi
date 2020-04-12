package com.admin.framework.security;
import com.admin.framework.filter.JwtAuthenticationTokenFilter;
import com.admin.framework.filter.ValidateCodeFilter;
import com.admin.framework.security.authentication.MyAuthenctiationFailureHandler;
import com.admin.framework.security.authentication.MyAuthenticationSuccessHandler;
import com.admin.framework.security.authentication.MyLogoutSuccessHandler;
import com.admin.framework.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 禁用 csrf
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        httpSecurity.authorizeRequests()
                // 不拦截得的请求
                .antMatchers("/login.html",
                        "/my/**",
                        "/treetable-lay/**",
                        "/xadmin/**",
                        "/ztree/**",
                        "/statics/**",
                        "/api/sysUser/All",
                        "/api/code"
                        )
                .permitAll()
                .anyRequest()
                .authenticated();
        //解决X-Frame-Options DENY问题
        httpSecurity.headers().frameOptions().sameOrigin();
        //登录验证码验证
        httpSecurity.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        //Token 验证
//        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, ValidateCodeFilter.class);
        httpSecurity.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and().logout().permitAll().logoutSuccessHandler(myLogoutSuccessHandler)
        ;
        //异常处理
        httpSecurity.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }

    /**
     * 用户密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
