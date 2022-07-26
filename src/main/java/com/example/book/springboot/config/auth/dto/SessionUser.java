package com.example.book.springboot.config.auth.dto;

import com.example.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    /**
     * SesionUser 생성자
     * 인증된 사용자 정보만 필요
     * @param user
     */
    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
