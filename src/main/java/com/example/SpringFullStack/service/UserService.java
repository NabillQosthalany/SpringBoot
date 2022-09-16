package com.example.SpringFullStack.service;

import com.example.SpringFullStack.DTO.ResponseDTO;
import com.example.SpringFullStack.DTO.User.SignInDTO;
import com.example.SpringFullStack.DTO.User.SignInResponseDTO;
import com.example.SpringFullStack.DTO.User.SignUpDTO;
import com.example.SpringFullStack.Model.AuthenticationToken;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Repository.UserRepo;
import com.example.SpringFullStack.exceptions.AuthenticationFailException;
import com.example.SpringFullStack.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;
    public ResponseDTO signUp(SignUpDTO signUpDTO) {
        //save the user
        if (Objects.nonNull(userRepo.findByEmail(signUpDTO.getEmail()))){
           // we have an user
            throw new CustomException("user already present");
        }
        //
        //has the password
        
        String encryptedPassword = signUpDTO.getPassword();

        try {
             encryptedPassword = hashPassword(signUpDTO.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = new User(signUpDTO.getFirstName(),signUpDTO.getLastName(),
                signUpDTO.getEmail(),encryptedPassword);

        userRepo.save(user);


        //create the token

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDTO responseDTO = new ResponseDTO("success","user created successfully");
        return responseDTO;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

//    public SignInResponseDTO signIn(SignInDTO signInDTO) {
//        //find user by email
//        User user = userRepo.findByEmail(signInDTO.getEmail());
//
//        if(Objects.isNull((user))){
//            throw new AuthenticationFailException("user is not valid");
//        }
//
//        //has the password
//        try {
//            if(!user.getPassword().equals(hashPassword(signInDTO.getPassword()))){
//                throw new AuthenticationFailException("wrong password");
//            }
//        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }
//
//        //compare the password in db
//
//        //if password match
//        AuthenticationToken authenticationToken = authentiacationService.getToken(user);
//
//        //retrive te token
//        if (Objects.isNull(authenticationToken)){
//            throw new CustomException("token is not present");
//        }
//        return new SignInResponseDTO("Success", authenticationToken.getToken());
//
//        //return response
//
//    }
    public SignInResponseDTO signIn(SignInDTO signInDto) {
        // find user by email

        User user = userRepo.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        // hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // compare the password in DB

        // if password match

        AuthenticationToken token = authenticationService.getToken(user);

        // retrive the token

        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInResponseDTO("success", token.getToken());

        // return response
    }
}
