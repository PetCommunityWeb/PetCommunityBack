package com.example.petback.common.data;

import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.repository.TipRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class TipInitializer {
    private final TipRepository tipRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        Random random = new Random();

        for (int i = 0; i<2000; i++) {
            String title = random.nextBoolean() ? "abc" : "def"; // title을 abc 또는 def로 랜덤하게 설정
            String content = "content" + i;

            Tip tip = Tip.builder()
                    .title(title)
                    .content(content)
                    .imageUrl(null)
                    .build();

            User user = userRepository.findById(2L).get();
            tip.setUser(user);
            tipRepository.save(tip);
        }
    }
}
