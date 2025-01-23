package moin.demo.service;

import moin.demo.domain.User;
import moin.demo.dto.SignupRequestDto;
import moin.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    @Test
    @DisplayName("회원가입")
    void signup() {
        //given
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .userId("test@test.com")
                .password("Qq09iu!@1238798")
                .name("test")
                .idType("REG_NO")
                .idValue("001123-3111111")
                .build();
        User user = signupRequestDto.toEntity();

        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encoder.encode(user.getPassword()));
        //when
        userService.signup(signupRequestDto);

        //then
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    void login() {
    }
}

