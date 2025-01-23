package moin.demo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moin.demo.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {
    private String userId;
    private String password;

}
