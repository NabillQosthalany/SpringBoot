package com.example.SpringFullStack.Repository;

import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepo extends JpaRepository<WishList,Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

}
