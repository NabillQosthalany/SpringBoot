package com.example.SpringFullStack.DTO.Cart;

import lombok.Data;

import java.util.List;
@Data
public class CartDTO {
    private List<CartItemDTO> cartItems;
    private double totalCost;

    public CartDTO() {

    }
}
