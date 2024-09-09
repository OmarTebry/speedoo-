package com.transfer.speedotransfer.entity;

import com.transfer.speedotransfer.dto.UserDTO;
import com.transfer.speedotransfer.dto.RegisterResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "`users`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDate dateOfBirth;  // Date of birth, can't be null

    @Column(nullable = false)
    private String country;  // Country, can't be null

    @Column
    private String phoneNumber;  // Phone number, can be null

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Favorite> favorites = new HashSet<>();



    public RegisterResponseDTO toResponse() {
        return RegisterResponseDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .country(this.country)
                .dateOfBirth(this.dateOfBirth)
                .phoneNumber(this.phoneNumber)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .accounts(this.accounts.stream().map(Account::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

}