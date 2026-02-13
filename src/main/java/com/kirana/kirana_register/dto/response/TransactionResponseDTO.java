package com.kirana.kirana_register.dto.response;

//todo: for now we are keeping it simple, but we can expand this to include more details like total amount, list of items, etc.
public class TransactionResponseDTO {

    private Long transactionId;
    private String status;
    private String message;

    public TransactionResponseDTO(Long transactionId, String status, String message) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
    }

    public Long getTransactionId(){
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
