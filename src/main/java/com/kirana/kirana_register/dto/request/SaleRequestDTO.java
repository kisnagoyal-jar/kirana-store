package com.kirana.kirana_register.dto.request;

import com.kirana.kirana_register.dto.mongodb.CustomerRequestDTO;
import java.util.List;

public class SaleRequestDTO {

    private CustomerRequestDTO customer;
    private List<ProductItemRequestDTO> items;
    private String currency;
    private double exchangeRate;

    public CustomerRequestDTO getCustomer() {
        return customer;
    }

    public List<ProductItemRequestDTO> getItems() {
        return items;
    }

    public String getCurrency() {
        return currency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setCustomer(CustomerRequestDTO customer) {
        this.customer = customer;
    }

    public void setItems(List<ProductItemRequestDTO> items) {
        this.items = items;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
