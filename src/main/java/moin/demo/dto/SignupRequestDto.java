package moin.demo.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import moin.demo.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class SignupRequestDto {


    private String userId;
    private String password;
    private String name;
    private String type;
    private String idValue;


    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .username(name)
                .type(type)
                .idValue(idValue)
                .build();
    }

}
