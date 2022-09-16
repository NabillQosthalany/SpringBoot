package com.example.SpringFullStack.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data


public class ProductDTO {

    private Integer id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;

    public ProductDTO() {
    }
}
