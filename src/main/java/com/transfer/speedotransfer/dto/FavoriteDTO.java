package com.transfer.speedotransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO {

    private Long id;
    private Long userId;
    private String accountNumber;
    private String accountName;
}
