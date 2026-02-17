package com.kirana.kirana_register.dto.response;

import lombok.*;


@Data
@AllArgsConstructor
public class TransactionResponseDTO {

    private final String transactionId;
    private final String status;
    private final String message;
    private final double totalAmount;

}
