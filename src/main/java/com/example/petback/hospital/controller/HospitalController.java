package com.example.petback.hospital.controller;

import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.hospital.dto.HospitalListResponseDto;
import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.service.HospitalService;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity createHospital(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody HospitalRequestDto requestDto){
        HospitalResponseDto responseDto = hospitalService.createHospital(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/my-hospitals")
    public ResponseEntity selectMyHospitals(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<HospitalResponseDto> responseDtos = hospitalService.selectMyHospitals(userDetails.getUser()).getHospitalResponseDtos();
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping
    public ResponseEntity selectAllHospitals(){
        List<HospitalResponseDto> responseDtos = hospitalService.selectAllHospitals().getHospitalResponseDtos();
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectHospital(@PathVariable Long id){
        HospitalResponseDto responseDto = hospitalService.selectHospital(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateHospital(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody HospitalRequestDto requestDto){
        HospitalResponseDto responseDto = hospitalService.updateHospital(userDetails.getUser(), id, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteHospital(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        hospitalService.deleteHospital(userDetails.getUser(), id);
        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }
}
