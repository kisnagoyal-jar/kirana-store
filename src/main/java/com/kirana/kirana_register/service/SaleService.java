package com.kirana.kirana_register.service;


import com.kirana.kirana_register.dto.request.ProductItemRequestDTO;
import com.kirana.kirana_register.dto.request.SaleRequestDTO;
import com.kirana.kirana_register.dto.response.TransactionResponseDTO;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.validationServices.ProductValidationService;
import com.kirana.kirana_register.service.validationServices.UserValidationService;
import org.springframework.stereotype.Service;

@Service
public class SaleService {
    private final CurrentUserService currentUserService;
    private final UserValidationService userValidationService;
    private final ProductValidationService productValidationService;
    private final TransactionService transactionService;

    public SaleService(CurrentUserService currentUserService, UserValidationService userValidationService, ProductValidationService productValidationService, TransactionService transactionService) {
        this.currentUserService = currentUserService;
        this.userValidationService = userValidationService;
        this.productValidationService = productValidationService;
        this.transactionService = transactionService;
    }


    public TransactionResponseDTO createSaleTransaction(SaleRequestDTO saleRequest) {
        UserPrincipal currentUser = currentUserService.getCurrentUser();
        String kiranaId = currentUser.getKiranaId();

        String customerId = userValidationService
                .getCustomer(
                        saleRequest.getCustomer()
                )
                .getId();
        // Validate the sale request (e.g., check product availability, calculate total price)
        for(ProductItemRequestDTO item : saleRequest.getItems()) {
            // Validate each product item (e.g., check if product exists, check inventory)
            productValidationService.validateProduct(item.getProductId(), kiranaId);
        }
        return transactionService.createSaleTransaction(saleRequest, customerId, kiranaId);
    }

}
