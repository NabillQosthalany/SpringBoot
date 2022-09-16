package com.example.SpringFullStack.service;

import com.example.SpringFullStack.DTO.Cart.AddToCartDTO;
import com.example.SpringFullStack.DTO.Cart.CartDTO;
import com.example.SpringFullStack.DTO.Cart.CartItemDTO;
import com.example.SpringFullStack.Model.Cart;
import com.example.SpringFullStack.Model.Product;
import com.example.SpringFullStack.Model.User;
import com.example.SpringFullStack.Repository.CartRepo;
import com.example.SpringFullStack.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;
    @Autowired
    CartRepo cartRepo;
    public void addToCart(AddToCartDTO addToCartDTO, User user) {
       Product product = productService.findById(addToCartDTO.getProductId());

        //validate if the productId is valid
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDTO.getQuantity());
        cart.setCreatedDate(new Date());

        //save the cart
        cartRepo.save(cart);
    }

    public CartDTO listCartItem(User user) {
        List<Cart> cartList = cartRepo.findByUserOrderByCreatedDateDesc(user);

        List<CartItemDTO> cartItems = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart: cartList){
            CartItemDTO cartItemDTO = new CartItemDTO(cart);
            totalCost += cartItemDTO.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDTO);
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalCost(totalCost);
        cartDTO.setCartItems(cartItems);
        return cartDTO;
    }

    public void deletedCartItem(Integer cartItemId, User user) {
        //the item id belongs to user
        Optional<Cart> optionalCart = cartRepo.findById(cartItemId);

        if (optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if(cart.getUser() != user){
            throw new CustomException("cart item does not belong to used: "+cartItemId);
        }
        cartRepo.delete(cart);

    }
}
