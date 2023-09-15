package com.example.petback.user.controller;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.dto.ProfileResponseDto;
import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.example.petback.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Mock
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private User user;
    private String accessToken;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private SignupRequestDto signupRequestDto() {
        return SignupRequestDto.builder()
                .username("test1234")
                .password(passwordEncoder.encode("test1234."))
                .role(UserRoleEnum.USER)
                .nickname("테스트")
                .email("test1234@test.com")
                .build();
    }

    private User user() {
        return User.builder()
                .username("test1234")
                .password(passwordEncoder.encode("test1234."))
                .role(UserRoleEnum.USER)
                .nickname("테스트")
                .email("test1234@test.com")
                .imageUrl("test.jpg")
                .build();
    }

    @DisplayName("회원가입 성공")
    @Test
    public void signUpSuccess() throws Exception {
        // given
        SignupRequestDto requestDto = signupRequestDto();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(requestDto)));

        // then
        resultActions.andExpect(status().isCreated());
    }


    @DisplayName("전체 사용자 조회")
    @Test
    void selectProfiles() throws Exception {
        User user = User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1234."))
                .email("test@test.com")
                .nickname("테스트")
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("test1", UserRoleEnum.USER, user.getId());

        User user2 = User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test1234."))
                .email("test2@test.com")
                .nickname("테스트2")
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user2);
        accessToken = jwtUtil.createToken("test2", UserRoleEnum.USER, user.getId());

        List<ProfileResponseDto> findAll = userRepository.findAll().stream().map(ProfileResponseDto::new).toList();

        // then
        Assertions.assertThat(findAll.size()).isEqualTo(2);

    }


    @Test
    @DisplayName("상세 사용자 조회")
    void selectProfile() throws Exception {
        User user = User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1234."))
                .email("test@test.com")
                .nickname("테스트")
                .role(UserRoleEnum.USER)
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("test1", UserRoleEnum.USER, user.getId());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateProfile() throws Exception {
        Long id = createTestProfile();
        ProfileRequestDto updateProfileRequestDto = ProfileRequestDto.builder()
                .username("test12")
                .nickname("회원정보수정테스트")
                .imageUrl("test.jpg")
                .build();
        accessToken = jwtUtil.createToken("test123", UserRoleEnum.USER, user.getId());
        mockMvc.perform(put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(updateProfileRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private Long createTestProfile() {
        user = User.builder()
                .username("test123")
                .password(passwordEncoder.encode("test1234."))
                .email("test@test.com")
                .nickname("테스트12")
                .role(UserRoleEnum.USER)
                .imageUrl("test1.jpg")
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("test123", UserRoleEnum.USER, user.getId());

        return user.getId();
    }

    @Test
    void deleteProfile() throws Exception {
        Long id = createTestProfile();
        User user = userRepository.findById(id)
                .orElseThrow();
        mockMvc.perform(delete("/api/users/profile/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }
}