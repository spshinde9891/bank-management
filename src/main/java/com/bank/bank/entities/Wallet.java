package com.bank.bank.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @Column(name = "wallet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "operation_type", nullable = false)
    private Integer operationType;
    
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    
    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "modified_by")
    private Integer modifiedBy;
    
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;
}