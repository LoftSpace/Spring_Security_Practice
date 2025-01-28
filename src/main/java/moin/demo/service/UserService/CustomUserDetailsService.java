package moin.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.User;
import moin.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        return User.builder()
                .username(userId)
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();

    }

    private UserDetails createUserDetails(User user) {
        return User.builder()
                .userId(user.getUserId())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(List.of(user.getRoles().toArray(new String[0])))
                .build();
    }
}
