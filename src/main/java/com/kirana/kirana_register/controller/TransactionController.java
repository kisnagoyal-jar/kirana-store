package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.dto.request.SaleRequestDTO;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.helper.staff.TransactionService;
import com.kirana.kirana_register.service.helper.staff.UserValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserValidationService userValidationService;

    public TransactionController(
            TransactionService transactionService,
            UserValidationService userValidationService
    ) {
        this.transactionService = transactionService;
        this.userValidationService = userValidationService;
    }

    /**
     * STAFF and ADMIN only
     */
    @PostMapping("/sale")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<?> sale(
            @AuthenticationPrincipal UserPrincipal staff,
            @RequestBody SaleRequestDTO request
    ) {
        // 🔐 derive kirana from STAFF identity (NOT request)
        String kiraanaId = staff.getUser().getKiraanaId();

        // validate or create customer
        String customerId = userValidationService
                .getOrCreateCustomer(
                        request.getCustomer(),
                        kiraanaId
                )
                .getId();

        Long txId = transactionService
                .createSaleTransaction(request, customerId);

        return ResponseEntity.ok(Map.of("transactionId", txId));
    }

    /**
     * ADMIN only
     */
    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> refund(
            @AuthenticationPrincipal UserPrincipal admin,
            @RequestBody Map<String, Long> body
    ) {
        Long originalTransactionId = body.get("originalTransactionId");
        String kiraanaId = admin.getUser().getKiraanaId();

        Long refundTxId = transactionService.createRefundTransaction(
                originalTransactionId,
                kiraanaId
        );

        return ResponseEntity.ok(
                Map.of("refundTransactionId", refundTxId)
        );
    }

}
