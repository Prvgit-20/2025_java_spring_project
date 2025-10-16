package com.training.demo.tribble.service.impl;

import com.training.demo.tribble.domain.Mobile;
import com.training.demo.tribble.dto.MobileDto;
import com.training.demo.tribble.repository.MobileRepository;
import com.training.demo.tribble.service.MobileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MobileServiceImpl implements MobileService {
    private final MobileRepository mobileRepository;
    private static final Logger logger = LoggerFactory.getLogger(MobileServiceImpl.class);

    public MobileServiceImpl(MobileRepository mobileRepository) {
        this.mobileRepository = mobileRepository;
    }

    @Override
    public Mobile addOrUpdateMobile(MobileDto mobileDto) {
        logger.info("Adding/updating mobile: {}", mobileDto.imei());
        if (mobileRepository.existsByImei(mobileDto.imei())) {
            throw new IllegalArgumentException("Mobile with IMEI already exists");
        }
        Mobile mobile = new Mobile(
            mobileDto.brand(),
            mobileDto.model(),
            mobileDto.imei(),
            mobileDto.price(),
            mobileDto.color(),
            mobileDto.stockQuantity()
        );
        return mobileRepository.save(mobile);
    }

    @Override
    public List<Mobile> listMobiles() {
        logger.debug("Listing all mobiles");
        return mobileRepository.findAll();
    }

    @Override
    public Mobile getMobile(UUID id) {
        return mobileRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Mobile not found"));
    }
}
