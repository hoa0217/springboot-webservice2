package com.example.book.springboot.config.auth;

import com.example.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()// h2-console화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                .authorizeRequests()// url별 권한관리를 설정하는 옵션의 시작점
                .antMatchers("/","/main", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 권한관리 대상을 지정하는 옵션, 메소드(URL, HTTP)별로 관리 가능
                .antMatchers("/api/v1/**").hasRole(Role.GUEST.name()) // permitAll : 전체 열람 권한, hasRole : user 권한을 가진 사람만
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들은 authenticated(인증된 = 로그인한)한 사용자들에게 허용
                .and()
                .logout() // 로그아웃 기능에 대한 설정 시작점
                .logoutSuccessUrl("/")// 로그아웃 성공시 / 주소로 이동
                .and()
                .oauth2Login() // 로그인 기능에 대한 설정 시작점
                .userInfoEndpoint().userService(customOAuth2UserService); // userService : 로그인 성공 후 후속조치를 진행할 UserServie 인터페이스의 구현체 등록
    }

}
