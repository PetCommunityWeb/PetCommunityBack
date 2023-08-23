package com.example.petback.reservation.controller;

import com.example.petback.common.domain.Address;
import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservation.dto.ReservationRequestDto;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.reservationslot.repository.ReservationSlotRepository;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ReservationSlotRepository reservationSlotRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    private User user;
    private Hospital hospital;
    private ReservationSlot reservationSlot;
    private String accessToken;

    @BeforeEach
    public void setup() {
        createTestUser();
        createTestHospital();
        createTestReservationSlots();
    }

    private void createTestUser() {
        user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass1234"))
                .role(UserRoleEnum.USER)
                .nickname("nickname")
                .email("test1234@test.com")
                .imageUrl("test.jpg")
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("user", UserRoleEnum.USER);
    }

    private void createTestHospital() {
        hospital = hospitalRepository.save(Hospital.builder()
                .name("테스트병원")
                .introduction("테스트 병원입니다.")
                .address(Address.builder()
                        .city("서울시")
                        .street("테스트길")
                        .zipcode("123-456")
                        .build())
                .imageUrl("abc.jpg")
                .latitude(127.4451)
                .longitude(37.4485)
                .phoneNumber("02-1234-5678")
                .user(user)
                .build());
    }

    private void createTestReservationSlots() {
        reservationSlot = reservationSlotRepository.save(ReservationSlot.builder()
                .hospital(hospital)
                .date(LocalDate.of(2024, 8, 22))
                .startTime(LocalTime.of(16, 30))
                .build());
    }


    @Test
    void createReservation() throws Exception {
        Long id = hospital.getId();
        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                .hospitalId(id)
                .date(LocalDate.of(2024, 8, 22))
                .startTime(LocalTime.of(16, 30))
                .build();
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(reservationRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteReservation() throws Exception {
        Reservation reservation = createTestReservation();
        mockMvc.perform(delete("/api/reservations/" + reservation.getReservationNum())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectAllReservations() throws Exception {
        createTestReservation();
        mockMvc.perform(get("/api/reservations" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectReservation() throws Exception {
        Reservation reservation = createTestReservation();
        mockMvc.perform(get("/api/reservations/" + reservation.getReservationNum())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    Reservation createTestReservation() {
        return reservationRepository.save(
                Reservation.builder()
                        .reservationNum(UUID.randomUUID().toString())
                        .reservationStatus(ReservationStatusEnum.예약완료)
                        .reservationSlot(reservationSlot)
                        .user(user)
                        .hospital(hospital)
                        .build()
        );
    }
}