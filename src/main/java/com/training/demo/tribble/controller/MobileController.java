package com.training.demo.tribble.controller;

import com.training.demo.tribble.domain.Mobile;
import com.training.demo.tribble.dto.MobileDto;
import com.training.demo.tribble.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/mobile", produces = MediaType.APPLICATION_JSON_VALUE)
public class MobileController {

    private final MobileService mobileService;

    @Autowired
    public MobileController(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping
    public ResponseEntity<List<Mobile>> listMobiles() {
        try {
            List<Mobile> mobiles = mobileService.listMobiles();
            return ResponseEntity.ok(mobiles);
        } catch (Exception e) {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMobile(@RequestBody MobileDto mobileDto) {
        try {
            Mobile mobile = mobileService.addOrUpdateMobile(mobileDto);
            return ResponseEntity.ok(mobile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
