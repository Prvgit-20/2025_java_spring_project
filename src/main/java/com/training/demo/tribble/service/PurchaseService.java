package com.training.demo.tribble.service;

import com.training.demo.tribble.dto.PurchaseRequestDto;
import com.training.demo.tribble.domain.Purchase;
import java.util.UUID;

public interface PurchaseService {
    Purchase createPurchase(PurchaseRequestDto purchaseRequestDto);
    Purchase getPurchase(UUID id);
}
