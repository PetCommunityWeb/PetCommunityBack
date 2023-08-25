package com.example.petback.comment.controller;

import com.example.petback.comment.dto.CommentRequestDto;
import com.example.petback.comment.entity.Comment;
import com.example.petback.comment.repository.CommentRepository;
import com.example.petback.common.jwt.JwtUtil;
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
public class FeedCommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private CommentRepository commentRepository;
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
    @DisplayName("코멘트 작성 테스트")
    void createComment() throws Exception {
        createTestFeed();
        Feed feed = feedRepository.findByTitle("테스트피드제목").orElseThrow();
        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .content("테스트코멘트")
                .build();

        mockMvc.perform(post("/api/comments/" + feed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(commentRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


    }

    @Test
    @DisplayName("피드 수정 테스트")
    void updateComment() throws Exception {
        Feed feed = createTestFeed();
        createTestComment();

        CommentRequestDto updateCommentRequestDto = CommentRequestDto.builder()
                .content("수정된내용")
                .build();

        mockMvc.perform(put("/api/comments/"+feed.getId()+"?feedId=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                        .content(objectMapper.writeValueAsString(updateCommentRequestDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("코멘트 삭제 테스트")
    void deleteComment() throws Exception {
        Feed feed = createTestFeed();
        Comment comment = createTestComment();

        mockMvc.perform(delete("/api/comments/"+comment.getId()+"?feedId="+feed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(JwtUtil.AUTHORIZATION_HEADER, accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private Feed createTestFeed() {
        return feedRepository.save(Feed.builder()

                .title("테스트피드제목")
                .content("테스트피드내용")
                .imageUrl("imagUrl")
                .user(user)
                .build());

    }

    private Comment createTestComment() {
        Feed feed = feedRepository.findByTitle("테스트피드제목").orElseThrow();
       return commentRepository.save(Comment.builder()
                .feed(feed)
                .user(user)
                .username(user.getUsername())
                .content("테스트코멘트내용")
                .build());
    }
}
