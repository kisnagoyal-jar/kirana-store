package com.kirana.kirana_register.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class FxRatesResponse {

    private String base;
    private Map<String, Double> rates;
}
