package com.example.SpringFullStack.DTO.Cart;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class AddToCartDTO {

    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;
}
