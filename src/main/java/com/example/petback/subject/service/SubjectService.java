package com.example.petback.subject.service;

import com.example.petback.species.SpeciesEnum;
import com.example.petback.species.entity.Species;
import com.example.petback.subject.SubjectEnum;
import com.example.petback.subject.entity.Subject;
import com.example.petback.subject.repository.SubjectRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService {
    private final SubjectRepository subjectRepository;


    @PostConstruct // 애플리케이션이 시작될때 SpeciesEnum에 있는 동물의 종들을 species db에 넣어줌
    public void initSpeciesData() {
        for(SubjectEnum subjectEnum : SubjectEnum.values()) {
            subjectRepository.findByName(subjectEnum)
                    .orElseGet(() -> {
                        Subject subject = Subject.builder()
                                .name(subjectEnum)
                                .build();
                        return subjectRepository.save(subject);
                    });
        }
    }
}
