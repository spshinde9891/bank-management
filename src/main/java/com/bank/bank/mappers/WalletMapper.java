package com.bank.bank.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.bank.bank.dtos.WalletDto;
import com.bank.bank.entities.Wallet;
import com.bank.bank.requests.WalletRequestDto;

@Mapper(componentModel = "spring")
public interface WalletMapper {
	Wallet toEntity(WalletRequestDto walletRequestDto);

	WalletDto toDto(Wallet wallet);

	List<WalletDto> mapWalletListToDtoList(List<Wallet> walletList);

}
