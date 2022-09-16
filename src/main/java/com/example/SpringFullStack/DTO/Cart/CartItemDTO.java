package com.example.SpringFullStack.DTO.Cart;

import com.example.SpringFullStack.Model.Cart;
import com.example.SpringFullStack.Model.Product;
import lombok.Data;

@Data
public class CartItemDTO {

    private Integer id;
    private Integer quantity;
    private Product product;

    public CartItemDTO() {

    }
    public CartItemDTO(Cart cart){
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
