package com.kirana.kirana_register.service.helper.staff;

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
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionDao transactionDao;
    private final TransactionItemDao transactionItemDao;
    private final InventoryDao inventoryDao;
    private  final ProductDao productDao;

    public TransactionService(
            TransactionDao transactionDao,
            TransactionItemDao transactionItemDao,
            InventoryDao inventoryDao,
            ProductDao productDao
    ) {
        this.transactionDao = transactionDao;
        this.transactionItemDao = transactionItemDao;
        this.inventoryDao = inventoryDao;
        this.productDao = productDao;
    }

    // ================= SALE =================

    @Transactional
    public Long createSaleTransaction(SaleRequestDTO request, String customerId) {

        Transaction tx = new Transaction();
        tx.setKiraanaId(request.getKiraanaId());
        tx.setUserId(customerId);
        tx.setType(TransactionType.SALE);
        tx.setCurrency(request.getCurrency());
        tx.setExchangeRate(request.getExchangeRate());
        tx.setTotalAmount(0);
        tx.setCompleted(false);

        tx = transactionDao.save(tx);

        double total = 0;

        for (ProductItemRequestDTO item : request.getItems()) {
            Product product = productDao.findByIdAndKiraanaId(item.getProductId(), request.getKiraanaId())
                    .orElseThrow(() -> new IllegalStateException("Product not found"));

            Inventory inventory = inventoryDao
                    .findById(product.getInventoryId())
                    .orElseThrow(() -> new IllegalStateException("Inventory not found"));

            if (inventory.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock");
            }

            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());
            inventoryDao.save(inventory);

            TransactionItem ti = new TransactionItem();
            ti.setTransactionId(tx.getId());
            ti.setProductId(item.getProductId());
            ti.setProductName(product.getProductName());
            ti.setQuantity(item.getQuantity());
            ti.setUnitPrice(product.getPrice());

            transactionItemDao.save(ti);

            total += product.getPrice() * item.getQuantity();
        }
        //calculation exchange rate and total amount //todo:--------------------------------------------
        tx.setTotalAmount(total * request.getExchangeRate());
        transactionDao.save(tx);

        return tx.getId();
    }

    // ================= REFUND =================

    @Transactional
    public Long createRefundTransaction(Long originalTransactionId, String kiraanaId) {

        // 1️⃣ Fetch original transaction
        Transaction originalTx = transactionDao.findById(originalTransactionId)
                .orElseThrow(() ->
                        new IllegalStateException("Original transaction not found")
                );

        // 2️⃣ Validate it belongs to same kirana
        if (!originalTx.getKiraanaId().equals(kiraanaId)) {
            throw new IllegalStateException("Transaction does not belong to this kirana");
        }

        // 3️⃣ Validate type
        if (originalTx.getType() != TransactionType.SALE) {
            throw new IllegalStateException("Only SALE transactions can be refunded");
        }

        // 4️⃣ Fetch original items
        List<TransactionItem> items =
                transactionItemDao.findByTransactionId(originalTransactionId);

        if (items.isEmpty()) {
            throw new IllegalStateException("No items found for original transaction");
        }

        // 5️⃣ Restore stock
        for (TransactionItem item : items) {
            Long inventoryId = productDao.findByIdAndKiraanaId(item.getProductId(), kiraanaId)
                    .orElseThrow(() -> new IllegalStateException("Product not found for item") )
                    .getInventoryId();

            Inventory inventory = inventoryDao
                    .findById(inventoryId)
                    .orElseThrow(() ->
                            new IllegalStateException("Inventory not found for product")
                    );

            inventory.setQuantity(
                    inventory.getQuantity() + item.getQuantity()
            );

            inventoryDao.save(inventory);
        }

        // 6️⃣ Create refund transaction
        Transaction refundTx = new Transaction();
        refundTx.setKiraanaId(kiraanaId);
        refundTx.setUserId(originalTx.getUserId());
        refundTx.setType(TransactionType.REFUND);
        refundTx.setCurrency(originalTx.getCurrency());
        refundTx.setExchangeRate(originalTx.getExchangeRate());
        refundTx.setTotalAmount(originalTx.getTotalAmount() * -1);
        refundTx.setOriginalTransactionId(originalTransactionId);

        refundTx = transactionDao.save(refundTx);

        return refundTx.getId();
    }

}
