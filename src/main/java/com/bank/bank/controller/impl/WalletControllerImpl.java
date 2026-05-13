package com.bank.bank.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bank.bank.controller.WalletController;
import com.bank.bank.dtos.WalletDto;
import com.bank.bank.requests.WalletRequestDto;
import com.bank.bank.responses.ApiResponse;
import com.bank.bank.service.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
@RequiredArgsConstructor
public class WalletControllerImpl implements WalletController{
	
	private final WalletService walletService;

	@Override
	public ResponseEntity<ApiResponse<WalletDto>> addInWallet(WalletRequestDto walletDto) {
		log.info("<<START>> addInWallet called <<START>>");
		ResponseEntity<ApiResponse<WalletDto>> responseEntity =
	            new ResponseEntity<>(
	                    walletService.addInWallet(walletDto),
	                    HttpStatus.OK);
		log.info("<<END>> addInWallet  <<END>>");
		return responseEntity;
	}

	@Override
	public ResponseEntity<ApiResponse<WalletDto>> updateInWallet(WalletDto walletDto) {
		log.info("<<START>> updateInWallet called <<START>>");
		ResponseEntity<ApiResponse<WalletDto>> responseEntity =
	            new ResponseEntity<>(
	                    walletService.updateInWallet(walletDto),
	                    HttpStatus.OK);
		log.info("<<END>> updateInWallet  <<END>>");
		return responseEntity;
	}

	@Override
	public ResponseEntity<ApiResponse<WalletDto>> getWalletById(Long walletId) {
		log.info("<<START>> getWalletById called <<START>>");
		ResponseEntity<ApiResponse<WalletDto>> responseEntity =
	            new ResponseEntity<>(
	                    walletService.getWalletById(walletId),
	                    HttpStatus.OK);
		log.info("<<END>> getWalletById  <<END>>");
		return responseEntity;
	}	

}
