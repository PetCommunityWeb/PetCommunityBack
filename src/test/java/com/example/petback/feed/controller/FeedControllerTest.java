package com.example.petback.feed.controller;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    private Feed feed;

    @BeforeEach
    public void setUp() {
        createUser();
        createTestFeed();
    }

    @Test
    @DisplayName("피드 생성 테스트")
    void createFeed() throws Exception {
        FeedRequestDto feedRequestDto = FeedRequestDto.builder()
                .title("테스트피드제목")
                .content("테스트피드내용")
                .imageUrl("imageUrl")
                .build();
        mockMvc.perform(post("/api/feeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(feedRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //피드 전체 조회
    @Test
    @DisplayName("피드 전체 조회")
    void selectFeeds() throws Exception {
        mockMvc.perform(get("/api/feeds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("피드 단일 조회")
    void selectFeed() throws Exception {
        mockMvc.perform(get("/api/feeds/" + feed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("피드 수정 테스트")
    void updateFeed() throws Exception {
        FeedRequestDto updateFeedRequestDto = FeedRequestDto.builder()
                .title("수정된타이틀")
                .content("수정된내용")
                .imageUrl("imageUrl")
                .build();
        mockMvc.perform(put("/api/feeds/"+feed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(updateFeedRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("피드 삭제 테스트")
    void deleteFeed() throws Exception {
        Feed feed = feedRepository.findByTitle("테스트피드제목")
                .orElseThrow();
        mockMvc.perform(delete("/api/feeds/" + feed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("피드 좋아요 테스트")
    void likeFeed() throws Exception {
        // 본인 피드 좋아요 불가
        mockMvc.perform(post("/api/feeds/" + feed.getId() + "/likes")
                        .contentType(MediaType.ALL)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    private void createTestFeed() {
        feed =  feedRepository.save(Feed.builder()
                .title("테스트피드제목")
                .content("테스트피드내용")
                .imageUrl("imageUrl")
                .user(user)
                .build());
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
        accessToken = jwtUtil.createToken("user", UserRoleEnum.USER, user.getId()); // header에 key-value로 보내는 accessToken을 filter에서 처리하기 위함
    }
}
