package com.mvp.vending.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer amountAvailable;
    @Column(nullable = false)
    private Float cost;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long sellerId;
}
