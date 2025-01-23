package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.User;
import moin.demo.dto.JwtToken;
import moin.demo.dto.LoginRequestDto;
import moin.demo.dto.SignupRequestDto;
import moin.demo.repository.UserRepository;
import moin.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        User user = signupRequestDto.toEntity();
        if(userRepository.findByUserId(user.getUserId()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

        user.updateToEncodedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.updateToEncodedIdType(bCryptPasswordEncoder.encode(user.getIdType()));
        userRepository.save(user);
    }

    @Transactional
    public JwtToken login(LoginRequestDto loginRequestDto){

        String userId = loginRequestDto.getUserId();

        String password = loginRequestDto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userId,password);

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }


}
