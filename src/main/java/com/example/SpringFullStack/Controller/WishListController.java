package com.example.SpringFullStack.Controller;

import com.example.SpringFullStack.DTO.ProductDTO;
import com.example.SpringFullStack.Model.Product;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Model.WishList;
import com.example.SpringFullStack.common.ApiResponse;
import com.example.SpringFullStack.service.AuthenticationService;
import com.example.SpringFullStack.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    //save product in wishlist
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token) {
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);


        //save the item in wishlist

        WishList wishList = new WishList(user, product);

        wishListService.createWishlist(wishList);

        ApiResponse apiResponse = new ApiResponse(true,"Added to WishList");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        //get all wishlist item from a user
    }
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDTO>>getWishList(@PathVariable("token")String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);
        List <ProductDTO>productDtos = wishListService.getWishListForUser(user);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }

}

