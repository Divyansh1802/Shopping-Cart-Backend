package com.E_COMM.Dream_shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Product")
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private String brand;
   private String description;
   private BigDecimal price;
   private int inventory;

   @ManyToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "category_id")
   private Category category;

   @OneToMany(mappedBy =  "product",cascade = CascadeType.ALL,orphanRemoval = true)
   @JsonIgnore
   private List<Image> images;

    public Product(String name,String brand,String description,int inventory,BigDecimal price,Category category) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
    }
}
