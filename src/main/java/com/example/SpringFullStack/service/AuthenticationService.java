package com.example.SpringFullStack.service;

import com.example.SpringFullStack.Model.AuthenticationToken;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Repository.TokenRepo;
import com.example.SpringFullStack.exceptions.AuthenticationFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);

    }
    public User getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepo.findByToken(token);
        if(Objects.isNull(token)){
            return null;
        }
        //authenticationToken is not null
        return authenticationToken.getUser();
    }
    public void authenticate(String token)throws AuthenticationFailException{
        //null check
        if (Objects.isNull(token)){
            //throw and exception
            throw new AuthenticationFailException("token not present");
        }
        if (Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token is not value");
        }
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);

    }
    }
