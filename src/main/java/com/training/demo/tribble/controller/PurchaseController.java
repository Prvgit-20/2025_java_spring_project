package com.training.demo.tribble.controller;

import com.training.demo.tribble.dto.PurchaseRequestDto;
import com.training.demo.tribble.domain.Purchase;
import com.training.demo.tribble.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        try {
            Purchase purchase = purchaseService.createPurchase(purchaseRequestDto);
            return ResponseEntity.ok(purchase);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchase(@PathVariable UUID id) {
        Purchase purchase = purchaseService.getPurchase(id);
        if (purchase == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchase);
    }
}
