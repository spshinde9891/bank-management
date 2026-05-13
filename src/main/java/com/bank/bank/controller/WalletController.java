package com.bank.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank.dtos.WalletDto;
import com.bank.bank.requests.WalletRequestDto;
import com.bank.bank.responses.ApiResponse;

@RequestMapping("api/v1/wallets")
@RestController
@CrossOrigin(origins = "*")
public interface WalletController {
	
	@PostMapping("/save")
	public ResponseEntity<ApiResponse<WalletDto>> addInWallet(@RequestBody WalletRequestDto walletDto);
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<WalletDto>> updateInWallet(@RequestBody WalletDto walletDto);

	@GetMapping("/{walletId}")
	public ResponseEntity<ApiResponse<WalletDto>> getWalletById(@PathVariable Long walletId);
}
