package com.bank.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.bank.entities.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>{

}
