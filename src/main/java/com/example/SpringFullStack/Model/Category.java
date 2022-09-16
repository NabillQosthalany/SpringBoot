package com.example.SpringFullStack.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private @NotBlank String categoryName;
    private @NotBlank String description;
    @Column(name = "image_url")
    private @NotBlank String imageUrl;

}
