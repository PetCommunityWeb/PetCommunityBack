package com.example.petback.tip.controller;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.tip.dto.TipRequestDto;
import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.entity.TipLike;
import com.example.petback.tip.repository.TipLikeRepository;
import com.example.petback.tip.repository.TipRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TipControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TipRepository tipRepository;
    @Autowired
    private TipLikeRepository tipLikeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    private User user;
    private String accessToken;

    @BeforeEach
    public void setup() {
        // test user 생성
        user = User.builder()
                .username("tiptest1")
                .password(passwordEncoder.encode("test1234."))
                .email("test@test.com")
                .imageUrl("test.jpg")
                .nickname("팁_테스트")
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("tiptest1", UserRoleEnum.USER, user.getId());
    }

    @Test
    void createTip() throws Exception {
        TipRequestDto tipRequestDto = TipRequestDto.builder()
                .title("테스트")
                .content("테스트입니다.")
                .build();
        mockMvc.perform(post("/api/tips/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(tipRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectTips() throws Exception {
        createTestTip();
        mockMvc.perform(get("/api/tips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private Long createTestTip() {
        Tip tip = Tip.builder()
                .title("팁테스트")
                .content("팁 테스트입니다")
                .user(user)
                .build();
        tipRepository.save(tip);
        return tip.getId();
    }

    @Test
    void selectTip() throws Exception {
        Long id = createTestTip();
        Tip tip = tipRepository.findById(id)
                .orElseThrow();
        mockMvc.perform(get("/api/tips/" + tip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void updateTip() throws Exception {
        createTestTip();
        TipRequestDto updateTipRequestDto = TipRequestDto.builder()
                .title("팁테스트(수정)")
                .content("수정된 테스트입니다")
                .build();
        mockMvc.perform(put("/api/tips/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(updateTipRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void deleteTip() throws Exception {
        Long id = createTestTip();
        Tip tip = tipRepository.findById(id)
                .orElseThrow();
        mockMvc.perform(delete("/api/tips/" + tip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void likeTip() throws Exception {
        // 본인의 tip에는 좋아요 시 BAD_REQUEST
        Long id = createTestTip();
        Tip tip = tipRepository.findById(id)
                .orElseThrow();
        mockMvc.perform(post("/api/tips/" + tip.getId() + "/likes")
                .contentType(MediaType.ALL)
                .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}