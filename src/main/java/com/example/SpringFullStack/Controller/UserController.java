package com.example.SpringFullStack.Controller;

import com.example.SpringFullStack.DTO.ResponseDTO;
import com.example.SpringFullStack.DTO.User.SignInDTO;
import com.example.SpringFullStack.DTO.User.SignInResponseDTO;
import com.example.SpringFullStack.DTO.User.SignUpDTO;
import com.example.SpringFullStack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;


    //sign up
    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignUpDTO signUpDTO){
        return userService.signUp(signUpDTO);
    }


    //sign in
    @PostMapping("/signin")
    public SignInResponseDTO signIn(@RequestBody SignInDTO signInDto) {
        return userService.signIn(signInDto);
    }
}
