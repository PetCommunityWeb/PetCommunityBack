package com.example.petback.hospital.controller;

import com.example.petback.common.domain.Address;
import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HospitalControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    private User user;
    private String accessToken;

    @BeforeEach
    public void setup() {
        // testuser 생성
        user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass1234"))
                .role(UserRoleEnum.USER)
                .nickname("nickname")
                .email("test1234@test.com")
                .imageUrl("test.jpg")
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("user", UserRoleEnum.USER, user.getId());
    }

    @Test
    void createHospital() throws Exception {
        HospitalRequestDto hospitalRequestDto = HospitalRequestDto.builder()
                .name("테스트병원1")
                .introduction("테스트 병원입니다.")
                .address(String.valueOf(Address.builder()
                        .city("서울시")
                        .street("테스트길")
                        .zipcode("123-456")
                        .build()))
                .imageUrl("abc.jpg")
                .latitude(127.4451)
                .longitude(37.4485)
                .phoneNumber("02-1234-5678")
                .speciesEnums(
                        Arrays.asList(SpeciesEnum.강아지, SpeciesEnum.기타)
                )
                .subjectEnums(
                        Arrays.asList(SubjectEnum.내과, SubjectEnum.정형외과)
                )
                .build();
        mockMvc.perform(post("/api/hospitals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(hospitalRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectAllHospitals() throws Exception {
        createTestHospital();
        mockMvc.perform(get("/api/hospitals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectHospital() throws Exception {
        createTestHospital();
        Hospital hospital = hospitalRepository.findByName("테스트병원1")
                .orElseThrow();
        mockMvc.perform(get("/api/hospitals/" + hospital.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateHospital() throws Exception {
        createTestHospital();
        HospitalRequestDto updateHospitalRequestDto = HospitalRequestDto.builder()
                .name("수정된 병원")
                .introduction("수정된 병원입니다.")
                .address(String.valueOf(Address.builder()
                        .city("서울시")
                        .street("수정된길")
                        .zipcode("123-456")
                        .build()))
                .imageUrl("abc.jpg")
                .latitude(127.4451)
                .longitude(37.4485)
                .phoneNumber("02-1234-5678")
                .speciesEnums(
                        Arrays.asList(SpeciesEnum.고양이, SpeciesEnum.기타)
                )
                .subjectEnums(
                        Arrays.asList(SubjectEnum.외과, SubjectEnum.정형외과)
                )
                .build();
        mockMvc.perform(put("/api/hospitals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(updateHospitalRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteHospital() throws Exception {
        createTestHospital();
        Hospital hospital = hospitalRepository.findByName("테스트병원1")
                .orElseThrow();
        mockMvc.perform(delete("/api/hospitals/" + hospital.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    void createTestHospital(){
        hospitalRepository.save(Hospital.builder()
                .name("테스트병원1")
                .introduction("테스트 병원입니다.")
                .address(String.valueOf(Address.builder()
                        .city("서울시")
                        .street("테스트길")
                        .zipcode("123-456")
                        .build()))
                .imageUrl("abc.jpg")
                .latitude(127.4451)
                .longitude(37.4485)
                .phoneNumber("02-1234-5678")
                .user(user)
                .build());
    }
}