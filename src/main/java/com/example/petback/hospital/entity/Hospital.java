package com.example.petback.hospital.entity;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospitalspecies.entity.HospitalSpecies;
import com.example.petback.hospitalsubject.entity.HospitalSubject;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hospitals")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "is_deleted = false")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String address;
    private String phoneNumber;
    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HospitalSpecies> hospitalSpecies = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HospitalSubject> hospitalSubjects = new HashSet<>();

    @ElementCollection(targetClass = OperatingDay.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "hospital_operating_days", joinColumns = @JoinColumn(name = "hospital_id"))
    @Builder.Default
    private Set<OperatingDay> operatingDays = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "hospital",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSlot> reservationSlots = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }
    public void addHospitalSpecies(HospitalSpecies hospitalSpecies) {
        this.hospitalSpecies.add(hospitalSpecies);
    }
    public void addHospitalSubject(HospitalSubject hospitalSubject) {
        this.hospitalSubjects.add(hospitalSubject);
    }
    public void setReservationSlots(List<ReservationSlot> reservationSlots) {
        this.reservationSlots = reservationSlots;
    }

    public void resetHospitalSpecies(){
        this.hospitalSpecies.clear();
        this.hospitalSubjects.clear();
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

    public Hospital updateAddress(String address) {
        this.address = address;
        return this;
    }

    public Hospital updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Hospital updateOperatingDays(Set<OperatingDay> operatingDays) {
        this.operatingDays = operatingDays;
        return this;
    }

    public void setDeleted() {
        this.isDeleted = Boolean.TRUE;
    }

    public void restore() {
        this.isDeleted = Boolean.FALSE;
    }
}
