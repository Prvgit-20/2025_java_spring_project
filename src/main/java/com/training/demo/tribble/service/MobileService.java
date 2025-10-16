package com.training.demo.tribble.service;

import com.training.demo.tribble.domain.Mobile;
import com.training.demo.tribble.dto.MobileDto;
import java.util.List;
import java.util.UUID;

public interface MobileService {
    Mobile addOrUpdateMobile(MobileDto mobileDto);
    List<Mobile> listMobiles();
    Mobile getMobile(UUID id);
}
