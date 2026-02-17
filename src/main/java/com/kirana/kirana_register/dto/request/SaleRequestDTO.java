package com.kirana.kirana_register.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestDTO {

    private UserRequestDTO customer;
    private List<ProductItemRequestDTO> items;
    private String currency;
    private double exchangeRate;

}
