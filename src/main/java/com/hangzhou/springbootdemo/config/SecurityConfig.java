package com.hangzhou.springbootdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/login").permitAll()//设置Spring Security对"/"和"login"不拦截
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")//设置Spring Security的登陆页面访问的路径为/login
            .defaultSuccessUrl("/chat")//登陆成功后转向/chat路径
            .permitAll()
            .and()
            .logout()
            .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("wjs").password(new BCryptPasswordEncoder().encode("wjs")).roles("USER")
                .and()
                .withUser("ktt").password(new BCryptPasswordEncoder().encode("ktt")).roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/static/**");
    }
}
