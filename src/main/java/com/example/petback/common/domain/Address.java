package com.example.petback.common.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
