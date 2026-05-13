package com.bank.bank.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {
    private Long walletId;
    private BigDecimal balance = BigDecimal.ZERO;
    private Integer operationType;
    private LocalDateTime createdOn;
    private Integer createdBy;
    private Integer modifiedBy;
    private LocalDateTime modifiedOn;
}
