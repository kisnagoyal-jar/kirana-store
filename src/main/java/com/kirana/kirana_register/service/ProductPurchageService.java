package com.kirana.kirana_register.service;

import com.kirana.kirana_register.dao.mongodb.ProductDao;
import com.kirana.kirana_register.dto.request.ProductItemRequestDTO;
import com.kirana.kirana_register.dto.request.SaleRequestDTO;
import com.kirana.kirana_register.entity.mongodb.User;
import com.kirana.kirana_register.service.helper.staff.ProductValidationService;
import com.kirana.kirana_register.service.helper.staff.TransactionService;
import com.kirana.kirana_register.service.helper.staff.UserValidationService;
import org.springframework.stereotype.Service;

@Service
public class ProductPurchageService {

    private final UserValidationService userValidationService;
    private final ProductValidationService productValidationService;
    private final TransactionService transactionService;
    private final ProductDao productDao;

    public ProductPurchageService(
            UserValidationService userValidationService,
            ProductValidationService productValidationService,
            TransactionService transactionService,
            ProductDao productDao
    ) {
        this.userValidationService = userValidationService;
        this.productValidationService = productValidationService;
        this.transactionService = transactionService;
        this.productDao = productDao;
    }

    // ================= SALE =================

    public Long processSale(SaleRequestDTO request) {

        //  Resolve / create customer (NO login)
        User customer = userValidationService.getOrCreateCustomer(
                request.getCustomer(),
                request.getKiraanaId()
        );

        // Validate products (Mongo)
        validateProduct(request);

        // Delegate money + stock to TransactionService

        return transactionService.createSaleTransaction(request, customer.getId());
    }

    private void validateProduct(SaleRequestDTO request) {
        for (ProductItemRequestDTO item : request.getItems()) {
            productValidationService.validateProduct(
                    item.getProductId(),
                    request.getKiraanaId()
            );

        }
    }





    // ================= REFUND =================

    public Long processRefund(Long originalTransactionId, String kiraanaId) {
        return transactionService.createRefundTransaction(originalTransactionId, kiraanaId);
    }
}
