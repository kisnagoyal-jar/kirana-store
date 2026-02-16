package com.kirana.kirana_register.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String productName;
    private double price;

    private int initialQuantity;
    private int capacity;

}
