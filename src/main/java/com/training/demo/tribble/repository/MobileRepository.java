package com.training.demo.tribble.repository;

import com.training.demo.tribble.domain.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MobileRepository extends JpaRepository<Mobile, UUID> {
    boolean existsByImei(String imei);
}
