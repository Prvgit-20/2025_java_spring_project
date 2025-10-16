package com.training.demo.tribble.repository;

import com.training.demo.tribble.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {}

