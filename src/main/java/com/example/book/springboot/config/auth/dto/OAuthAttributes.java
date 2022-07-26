package com.example.book.springboot.config.auth.dto;

import com.example.book.springboot.domain.user.Role;
import com.example.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String pricture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String pricture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.pricture = pricture;
    }

    /**
     * OAuth2User 사용자정보Map 변환
     *
     * @param registrationId
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .pricture((String) attributes.get("pricture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * UserEntity 생성 (처음 가입 시점)
     *
     * @return
     */
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(pricture)
                .role(Role.GUEST)
                .build();
    }
}
