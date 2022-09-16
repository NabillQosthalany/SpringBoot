package com.example.SpringFullStack.Repository;

import com.example.SpringFullStack.Model.AuthenticationToken;
import com.example.SpringFullStack.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
