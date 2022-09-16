package com.example.SpringFullStack.service;

import com.example.SpringFullStack.DTO.ProductDTO;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Model.WishList;
import com.example.SpringFullStack.Repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired

    WishListRepo wishListRepo;

    @Autowired
    ProductService productService;

    public void createWishlist(WishList wishList) {
        wishListRepo.save(wishList);
    }


    public List<ProductDTO> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDTO> productDtos = new ArrayList<>();
        for (WishList wishList: wishLists) {
            productDtos.add(productService.getProductDto(wishList.getProduct()));
        }
        return productDtos;

    }
}
