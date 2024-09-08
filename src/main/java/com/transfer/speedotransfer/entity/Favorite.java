package com.transfer.speedotransfer.entity;

import com.transfer.speedotransfer.dto.FavoriteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountName;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public FavoriteDTO toDTO() {
        return FavoriteDTO.builder()
                .id(this.id)
                .accountNumber(this.accountNumber)
                .accountName(this.accountName)
                .build();
    }
}