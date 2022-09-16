package com.example.SpringFullStack.Repository;

import com.example.SpringFullStack.Model.Cart;
import com.example.SpringFullStack.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserOrderByCreatedDateDesc(User user);
}
