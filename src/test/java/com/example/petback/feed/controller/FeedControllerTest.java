package com.example.petback.feed.controller;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.repository.FeedRepository;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FeedControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    private User user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        //user  생성
        user = User.builder()
                .username("testman")
                .password(passwordEncoder.encode("asdf1234"))
                .role(UserRoleEnum.USER)
                .nickname("testnick")
                .email("test@test.com")
                .imageUrl("test.jpg")
                .build();
        userRepository.save(user);
        accessToken = jwtUtil.createToken("testman", UserRoleEnum.USER);
    }


    @Test
    void createFeed() throws Exception {
        FeedRequestDto feedRequestDto = FeedRequestDto.builder()
                .title("테스트피드제목")
                .content("테스트피드내용")
                .build();
        mockMvc.perform(post("/api/feeds/")
                .contentType(MediaType.APPLICATION_JSON)
                .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                .content(objectMapper.writeValueAsString(feedRequestDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void selectFeeds() throws Exception{
        createTestFeed();
        
    }

    private void createTestFeed() {
    }

    @Test
    void selectFeed() {
    }

    @Test
    void updateFeed() {
    }

    @Test
    void deleteFeed() {
    }

    @Test
    void likeFeed() {
    }

    @Test
    void dislikeFeed() {
    }
}
