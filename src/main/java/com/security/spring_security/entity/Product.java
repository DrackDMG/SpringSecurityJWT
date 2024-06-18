package com.security.spring_security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotBlank
    @Column
    private String name;

    @Column
    @DecimalMin(value = "0.1")
    private BigDecimal price;

    public  Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @DecimalMin(value = "0.0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@DecimalMin(value = "0.0") BigDecimal price) {
        this.price = price;
    }
}
