package com.example.petback.notification.controller;

import com.example.petback.common.domain.Address;
import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.notification.entity.Notification;
import com.example.petback.notification.repository.NotificationRepository;
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

import static com.example.petback.reservation.ReservationStatusEnum.예약대기;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class NotificationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationSlotRepository reservationSlotRepository;

    private User user;
    private String accessToken;
    private Reservation reservation;
    private Hospital hospital;
    private ReservationSlot reservationSlot;
    private Notification notification;
    @BeforeEach
    public void setup() {
        createUser();
        createHospital();
        createReservationSlot();
        createReservation();
        createNotification();
    }

    @Test
    void selectNotifications() throws Exception {
        mockMvc.perform(get("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotification() throws Exception {
        mockMvc.perform(delete("/api/notifications/" + notification.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void createUser() {
        user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass1234"))
                .role(UserRoleEnum.USER)
                .nickname("nickname")
                .email("test1234@test.com")
                .imageUrl("test.jpg")
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("user", UserRoleEnum.USER); // header에 key-value로 보내는 accessToken을 filter에서 처리하기 위함
    }

    private void createNotification() {
        notification = Notification.builder()
                .reservation(reservation)
                .build();
        notificationRepository.save(notification);
    }

    private void createReservation() {
        reservation = Reservation.builder()
                .reservationNum(UUID.randomUUID().toString())
                .reservationStatus(예약대기)
                .user(user)
                .hospital(hospital)
                .reservationSlot(reservationSlot)
                .build();
        reservationRepository.save(reservation);
    }

    private void createHospital() {
        hospital = Hospital.builder()
                .name("테스트병원")
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
                .build();
        hospitalRepository.save(hospital);
    }

    private void createReservationSlot() {
        reservationSlot = ReservationSlot.builder()
                .hospital(hospital)
                .date(LocalDate.of(2024, 8, 22))
                .startTime(LocalTime.of(16, 30))
                .build();
        reservationSlotRepository.save(reservationSlot);
    }

}