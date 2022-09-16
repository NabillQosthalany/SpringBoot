package com.example.SpringFullStack.Controller;

import com.example.SpringFullStack.DTO.Cart.AddToCartDTO;
import com.example.SpringFullStack.DTO.Cart.CartDTO;
import com.example.SpringFullStack.Model.Product;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.common.ApiResponse;
import com.example.SpringFullStack.service.AuthenticationService;
import com.example.SpringFullStack.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @Autowired
    private AuthenticationService authenticationService;


    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addToCart(@RequestBody AddToCartDTO addToCartDTO,
                                                @RequestParam("token")String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDTO,user);
        return new ResponseEntity<>(new ApiResponse(true,"Added to cart"), HttpStatus.OK);


    }
    @GetMapping("/")
    public ResponseEntity<CartDTO> getCarsItems(@RequestParam("token")String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        //get cart items
        CartDTO cartDTO = cartService.listCartItem(user);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                     @RequestParam("token") String token){
        //authenticate the token
        authenticationService.authenticate(token);

        //find the user
        User user = authenticationService.getUser(token);

        cartService.deletedCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true,"Item has been removed"), HttpStatus.OK);


    }



}
