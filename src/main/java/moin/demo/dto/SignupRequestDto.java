package moin.demo.dto;


import lombok.*;

import moin.demo.domain.User;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignupRequestDto {


    private String userId;
    private String password;
    private String name;
    private String idType;
    private String idValue;


    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .idType(idType)
                .idValue(idValue)
                .build();
    }

}
