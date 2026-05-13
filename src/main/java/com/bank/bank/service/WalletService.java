package com.bank.bank.service;

import org.springframework.stereotype.Service;

import com.bank.bank.dtos.WalletDto;
import com.bank.bank.requests.WalletRequestDto;
import com.bank.bank.responses.ApiResponse;

@Service
public interface WalletService {

	public ApiResponse<WalletDto> addInWallet(WalletRequestDto walletDto);

	public ApiResponse<WalletDto> updateInWallet(WalletDto walletDto);

	public ApiResponse<WalletDto> getWalletById(Long walletId);

}
