package com.wcajisaca.accountService.mapper;

import com.wcajisaca.accountService.dtos.AccountDTO;
import com.wcajisaca.accountService.dtos.MovementsDTO;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.entities.Movements;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 * @author wilson
 */
@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDTO toAccountDTO(Account account);
    Account toAccount(AccountDTO accountDTO);
    Account updateAccountFromDTO(AccountDTO accountDTO, @MappingTarget Account account);
}