package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.User;
import moin.demo.dto.SignupRequestDto;
import moin.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        User user = signupRequestDto.toEntity();
        if(userRepository.findByUserId(user.getUserId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        userRepository.save(user);
    }
    /*
    private String encryptPassword(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }


     */
}
