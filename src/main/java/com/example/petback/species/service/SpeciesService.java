package com.example.petback.species.service;

import com.example.petback.species.SpeciesEnum;
import com.example.petback.species.entity.Species;
import com.example.petback.species.repository.SpeciesRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    @PostConstruct // 애플리케이션이 시작될때 SpeciesEnum에 있는 동물의 종들을 species db에 넣어줌
    public void initSpeciesData() {
        for(SpeciesEnum speciesEnum : SpeciesEnum.values()) {
            speciesRepository.findByName(speciesEnum)
                    .orElseGet(() -> {
                        Species species = Species.builder()
                                .name(speciesEnum)
                                .build();
                        return speciesRepository.save(species);
                    });
        }
    }
}
