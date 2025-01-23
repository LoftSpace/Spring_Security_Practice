package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.User;
import moin.demo.dto.LoginRequestDto;
import moin.demo.dto.SignupRequestDto;
import moin.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        User user = signupRequestDto.toEntity();
        if(userRepository.findByUserId(user.getUserId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        user.updateToEncodedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.updateToEncodedIdType(bCryptPasswordEncoder.encode(user.getIdType()));
        userRepository.save(user);

    }

    @Transactional
    public void login(LoginRequestDto loginRequestDto){
        User user = userRepository.findByUserId(loginRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id"));

        //비밀번호 확인 로직

        //토큰 반환
    }


}
