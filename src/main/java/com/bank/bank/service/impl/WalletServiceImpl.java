package com.bank.bank.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.bank.dtos.WalletDto;
import com.bank.bank.entities.Wallet;
import com.bank.bank.enums.OperationTypeEnum;
import com.bank.bank.mappers.WalletMapper;
import com.bank.bank.repositories.WalletRepository;
import com.bank.bank.requests.WalletRequestDto;
import com.bank.bank.responses.ApiResponse;
import com.bank.bank.service.WalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

	private final WalletRepository walletRepository;
	private final WalletMapper walletMapper;

	@Override
	@Transactional
	public ApiResponse<WalletDto> addInWallet(WalletRequestDto walletDto) {
		log.info("<<START>> addInWallet service <<START>>");
		try {
			if (walletDto.getAmount() == null) {
				log.error("Amount is null");
				return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Amount is mandatory", true,
						LocalDateTime.now());
			}
			if (walletDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
				log.error("Invalid amount : {}", walletDto.getAmount());
				return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Amount must be greater than zero", true,
						LocalDateTime.now());
			}
			Wallet wallet = walletMapper.toEntity(walletDto);
			wallet.setBalance(walletDto.getAmount());
			wallet.setOperationType(OperationTypeEnum.DEPOSIT.getOperationTypeId());
			wallet.setCreatedOn(LocalDateTime.now());
			wallet.setCreatedBy(1);
			Wallet savedWallet = walletRepository.save(wallet);
			log.info("Wallet created successfully with ID : {}", savedWallet.getWalletId());
			WalletDto responseDto = walletMapper.toDto(savedWallet);
			log.info("<<END>> addInWallet service <<END>>");
			return new ApiResponse<>(responseDto, HttpStatus.CREATED, "Wallet created successfully", false,
					LocalDateTime.now());
		} catch (Exception e) {
			log.error("Error while creating wallet : {}", e.getMessage(), e);
			return new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", true,
					LocalDateTime.now());
		}
	}

	@Override
	@Transactional
	public ApiResponse<WalletDto> updateInWallet(WalletDto walletDto) {
		log.info("<<START>> updateInWallet service <<START>>");
		int maxRetry = 3;
		for (int attempt = 1; attempt <= maxRetry; attempt++) {
			try {
				ApiResponse<WalletDto> validationResponse = validateWalletRequest(walletDto);
				if (validationResponse != null) {
					return validationResponse;
				}
				synchronized (walletDto.getWalletId().toString().intern()) {
					Optional<Wallet> walletData = walletRepository.findById(walletDto.getWalletId());
					if (walletData.isEmpty()) {
						log.error("Wallet not found for ID : {}", walletDto.getWalletId());
						return new ApiResponse<>(null, HttpStatus.NOT_FOUND, "Wallet not found", true,
								LocalDateTime.now());
					}
					Wallet wallet = walletData.get();
					if (walletDto.getOperationType()
					        == OperationTypeEnum.DEPOSIT
					                .getOperationTypeId()) {
						depositAmount(wallet, walletDto);
					}

					else if (walletDto.getOperationType() == OperationTypeEnum.WITHDRAW.getOperationTypeId()) {
						ApiResponse<WalletDto> withdrawResponse = withdrawAmount(wallet, walletDto);
						if (withdrawResponse != null) {
							return withdrawResponse;
						}
					}
					else {
						log.error("Invalid operation type");

						return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Invalid operation type", true,
								LocalDateTime.now());
					}
					wallet.setModifiedOn(LocalDateTime.now());
					wallet.setModifiedBy(1);
					Wallet savedWallet = walletRepository.save(wallet);
					log.info("Wallet updated successfully for ID : {}", savedWallet.getWalletId());
					WalletDto responseDto = walletMapper.toDto(savedWallet);
					log.info("<<END>> updateInWallet service <<END>>");
					return new ApiResponse<>(responseDto, HttpStatus.OK, "Wallet updated successfully", false,
							LocalDateTime.now());
				}
			} catch (ObjectOptimisticLockingFailureException e) {
				log.warn("Concurrent update detected. Retry attempt : {}", attempt);
				if (attempt == maxRetry) {
					return new ApiResponse<>(null, HttpStatus.CONFLICT,
							"Concurrent request conflict occurred. Please retry", true, LocalDateTime.now());
				}
			} catch (Exception e) {
				log.error("Error while updating wallet : {}", e.getMessage(), e);
				return new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", true,
						LocalDateTime.now());
			}
		}
		return new ApiResponse<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Unable to process request", true,
				LocalDateTime.now());
	}

	private ApiResponse<WalletDto> validateWalletRequest(WalletDto walletDto) {
		if (walletDto.getWalletId() == null) {
			log.error("Wallet ID is null");
			return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Wallet ID is mandatory", true, LocalDateTime.now());
		}
		if (walletDto.getBalance() == null) {
			log.error("Amount is null");
			return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Amount is mandatory", true, LocalDateTime.now());
		}
		if (walletDto.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
			log.error("Invalid amount : {}", walletDto.getBalance());
			return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Amount must be greater than zero", true,
					LocalDateTime.now());
		}
		if (walletDto.getOperationType() == null) {
			log.error("Operation type is null");
			return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Operation type is mandatory", true,
					LocalDateTime.now());
		}
		return null;
	}

	private void depositAmount(Wallet wallet, WalletDto walletDto) {
		wallet.setBalance(wallet.getBalance().add(walletDto.getBalance()));
		wallet.setOperationType(OperationTypeEnum.DEPOSIT.getOperationTypeId());
		log.info("Amount deposited successfully");
	}

	private ApiResponse<WalletDto> withdrawAmount(Wallet wallet, WalletDto walletDto) {
		if (wallet.getBalance().compareTo(walletDto.getBalance()) < 0) {
			log.error("Insufficient balance. Available : {}, Requested : {}", wallet.getBalance(),
					walletDto.getBalance());
			return new ApiResponse<>(null, HttpStatus.BAD_REQUEST, "Insufficient balance", true, LocalDateTime.now());
		}
		wallet.setBalance(wallet.getBalance().subtract(walletDto.getBalance()));
		wallet.setOperationType(OperationTypeEnum.WITHDRAW.getOperationTypeId());
		log.info("Amount withdrawn successfully");
		return null;
	}

	@Override
	public ApiResponse<WalletDto> getWalletById(
	        Long walletId) {
	    log.info("<<START>> getWalletById service <<START>>");
	    try {
	        Optional<Wallet> wallet =
	                walletRepository.findById(walletId);
	        if (wallet.isEmpty()) {
	            log.error(
	                    "Wallet not found for ID : {}",
	                    walletId);
	            return new ApiResponse<>(
	                    null,
	                    HttpStatus.NOT_FOUND,
	                    "Wallet not found",
	                    true,
	                    LocalDateTime.now());
	        }
            Wallet data = wallet.get();
	        WalletDto responseDto =
	                walletMapper.toDto(data);
	        log.info("<<END>> getWalletById service <<END>>");
	        return new ApiResponse<>(
	                responseDto,
	                HttpStatus.OK,
	                "Wallet fetched successfully",
	                false,
	                LocalDateTime.now());
	    } catch (Exception e) {
	        log.error(
	                "Error while fetching wallet : {}",
	                e.getMessage(),
	                e);
	        return new ApiResponse<>(
	                null,
	                HttpStatus.INTERNAL_SERVER_ERROR,
	                "Something went wrong",
	                true,
	                LocalDateTime.now());
	    }
	}
}
