package moin.demo.controller;

import lombok.RequiredArgsConstructor;
import moin.demo.dto.LoginRequestDto;
import moin.demo.dto.SignupRequestDto;
import moin.demo.service.UserService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequestDto signupRequestDto){
        try{
            userService.signup(signupRequestDto);
            return ResponseEntity.ok().body(
                    Map.of("resultCode",200,"resultMsg","OK"));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("resultCode", 400, "resultMsg", e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            String jwtToken = userService.login(loginRequestDto);
            return ResponseEntity.ok()
                    .body(
                        Map.of("resultCode",200,"resultMsg","OK","token",jwtToken));
        } catch(BadCredentialsException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("resultCode", 400, "resultMsg", "비정상적인 접근입니다."));
        }
    }



}
