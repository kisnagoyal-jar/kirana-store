package com.kirana.kirana_register.controller;

import com.kirana.kirana_register.dto.request.SaleRequestDTO;
import com.kirana.kirana_register.dto.response.TransactionResponseDTO;
import com.kirana.kirana_register.security.UserPrincipal;
import com.kirana.kirana_register.service.CurrentUserService;
import com.kirana.kirana_register.service.SaleService;
import com.kirana.kirana_register.service.TransactionService;
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
    private final SaleService saleService;
    private final CurrentUserService currentUserService;

    public TransactionController(
            TransactionService transactionService,
            SaleService saleService, CurrentUserService currentUserService
    ) {
        this.transactionService = transactionService;
        this.saleService = saleService;
        this.currentUserService = currentUserService;
    }

    /**
     * STAFF and ADMIN only
     */
    @PostMapping("/sale")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<TransactionResponseDTO> sale(
            @RequestBody SaleRequestDTO request
    ) {
        return ResponseEntity.ok(
                saleService.createSaleTransaction(request)
        );
    }


    /**
     * ADMIN only
     */
    @PostMapping("/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> refund(
            @RequestBody Map<String, String> body
    ) {
        UserPrincipal admin = currentUserService.getCurrentUser();
        String originalTransactionId = body.get("originalTransactionId");
        String kiranaId = admin.getUser().getKiranaId();

        String refundTxId = transactionService.createRefundTransaction(
                originalTransactionId,
                kiranaId
        );

        return ResponseEntity.ok(
                Map.of("refundTransactionId", refundTxId)
        );
    }

}
