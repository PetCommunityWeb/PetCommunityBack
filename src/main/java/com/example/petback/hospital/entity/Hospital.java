package com.example.petback.hospital.entity;

import com.example.petback.common.domain.Address;
import com.example.petback.hospitalspecies.entity.HospitalSpecies;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE hospital SET is_deleted = true WHERE id = ?")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    @Embedded
    private Address address;
    private String phoneNumber;
    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HospitalSpecies> hospitalSpecies = new HashSet<>();

    public void setUser(User user) {
        this.user = user;
    }
    public void addHospitalSpecies(HospitalSpecies hospitalSpecies) {
        this.hospitalSpecies.add(hospitalSpecies);
    }
    public void resetHospitalSpecies(){
        this.hospitalSpecies.clear();
    }
    public Hospital updateName(String name) {
        this.name = name;
        return this;
    }

    public Hospital updateIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public Hospital updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Hospital updateLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Hospital updateLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Hospital updateAddress(Address address) {
        this.address = address;
        return this;
    }

    public Hospital updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
