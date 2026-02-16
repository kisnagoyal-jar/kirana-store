package com.kirana.kirana_register.service.staff;

import com.kirana.kirana_register.dao.mongodb.ProductDao;
import com.kirana.kirana_register.dao.postgres.InventoryDao;
import com.kirana.kirana_register.dao.postgres.TransactionDao;
import com.kirana.kirana_register.dao.postgres.TransactionItemDao;
import com.kirana.kirana_register.dto.request.ProductItemRequestDTO;
import com.kirana.kirana_register.dto.request.SaleRequestDTO;
import com.kirana.kirana_register.entity.mongodb.Product;
import com.kirana.kirana_register.entity.postgres.Inventory;
import com.kirana.kirana_register.entity.postgres.Transaction;
import com.kirana.kirana_register.entity.postgres.TransactionItem;
import com.kirana.kirana_register.enums.TransactionType;
import com.kirana.kirana_register.service.helper.CurrencyRateService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final TransactionItemDao transactionItemDao;
    private final InventoryDao inventoryDao;
    private final ProductDao productDao;
    private final CurrencyRateService currencyRateService;



    public TransactionService(
            TransactionDao transactionDao,
            TransactionItemDao transactionItemDao,
            InventoryDao inventoryDao,
            ProductDao productDao, CurrencyRateService currencyRateService
    ) {
        this.transactionDao = transactionDao;
        this.transactionItemDao = transactionItemDao;
        this.inventoryDao = inventoryDao;
        this.productDao = productDao;
        this.currencyRateService = currencyRateService;
    }

    // ================= SALE =================

    @Transactional
    public Long createSaleTransaction(
            SaleRequestDTO request,
            String customerId,
            String kiraanaId
    ) {

        // 1️⃣ Create transaction shell
        Transaction tx = new Transaction();
        tx.setKiraanaId(kiraanaId);
        tx.setUserId(customerId);
        tx.setType(TransactionType.SALE);
        tx.setTotalAmount(0);
        tx.setCompleted(false);

        tx = transactionDao.save(tx);

        double totalUsd = 0;

        // 2️⃣ Process each product
        for (ProductItemRequestDTO item : request.getItems()) {

            // 🔐 Product must belong to same kirana
            Product product = productDao
                    .findByIdAndKiraanaId(item.getProductId(), kiraanaId)
                    .orElseThrow(() ->
                            new IllegalStateException("Product not found")
                    );

            Inventory inventory = inventoryDao
                    .findById(product.getInventoryId())
                    .orElseThrow(() ->
                            new IllegalStateException("Inventory not found")
                    );

            if (inventory.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException(
                        "Insufficient stock for product: " + product.getProductName()
                );
            }

            // 3️⃣ Update inventory
            inventory.setQuantity(
                    inventory.getQuantity() - item.getQuantity()
            );
            inventoryDao.save(inventory);

            // 4️⃣ Create transaction item
            TransactionItem txItem = new TransactionItem();
            txItem.setTransactionId(tx.getId());
            txItem.setProductId(item.getProductId());
            txItem.setProductName(product.getProductName());
            txItem.setQuantity(item.getQuantity());
            txItem.setUnitPrice(product.getPrice());

            transactionItemDao.save(txItem);

            totalUsd += product.getPrice() * item.getQuantity();
        }

        // 5️⃣ Finalize transaction
        double usdToInrRate = currencyRateService.getUsdToInrRate();
        double totalInr = totalUsd * usdToInrRate;

        tx.setTotalAmount(totalInr);
        tx.setCompleted(true);
        transactionDao.save(tx);

        return tx.getId();
    }

    // ================= REFUND =================

    @Transactional
    public Long createRefundTransaction(
            Long originalTransactionId,
            String kiraanaId
    ) {

        // 1️⃣ Fetch original transaction
        Transaction originalTx = transactionDao
                .findById(originalTransactionId)
                .orElseThrow(() ->
                        new IllegalStateException("Original transaction not found")
                );

        // 🔐 Ensure same kirana
        if (!originalTx.getKiraanaId().equals(kiraanaId)) {
            throw new IllegalStateException(
                    "Transaction does not belong to this kirana"
            );
        }

        // 2️⃣ Only SALE can be refunded
        if (originalTx.getType() != TransactionType.SALE) {
            throw new IllegalStateException(
                    "Only SALE transactions can be refunded"
            );
        }

        // 3️⃣ Fetch original items
        List<TransactionItem> items =
                transactionItemDao.findByTransactionId(originalTransactionId);

        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "No transaction items found for refund"
            );
        }

        // 4️⃣ Restore inventory
        for (TransactionItem item : items) {

            Product product = productDao
                    .findByIdAndKiraanaId(item.getProductId(), kiraanaId)
                    .orElseThrow(() ->
                            new IllegalStateException("Product not found for refund")
                    );

            Inventory inventory = inventoryDao
                    .findById(product.getInventoryId())
                    .orElseThrow(() ->
                            new IllegalStateException("Inventory not found")
                    );

            inventory.setQuantity(
                    inventory.getQuantity() + item.getQuantity()
            );
            inventoryDao.save(inventory);
        }

        // 5️⃣ Create refund transaction
        Transaction refundTx = new Transaction();
        refundTx.setKiraanaId(kiraanaId);
        refundTx.setUserId(originalTx.getUserId());
        refundTx.setType(TransactionType.REFUND);
//        refundTx.setCurrency(originalTx.getCurrency());
//        refundTx.setExchangeRate(originalTx.getExchangeRate());
        refundTx.setTotalAmount(originalTx.getTotalAmount() * -1);
        refundTx.setOriginalTransactionId(originalTransactionId);
        refundTx.setCompleted(true);

        refundTx = transactionDao.save(refundTx);

        return refundTx.getId();
    }
}
