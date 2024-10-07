package com.wcajisaca.accountService;

import com.wcajisaca.accountService.constants.ChallengeAccountConstant;
import com.wcajisaca.accountService.entities.Account;
import com.wcajisaca.accountService.entities.Movements;
import com.wcajisaca.accountService.enums.TypeAccount;
import com.wcajisaca.accountService.enums.TypeMovement;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ChallengeAccountApplicationTests {
	Account account = new Account();
	@Test
	@Order(1)
	void testAccountEntity() {
		UUID personId = UUID.randomUUID();
		account.setPersonId(personId);
		account.setTypeAccount(TypeAccount.AHO);
		account.setAccountNumber(123456789);
		account.setInitialBalance(ChallengeAccountConstant.INITIAL_BALANCE);
		account.setStatus(Boolean.TRUE);

		assertThat(account.getPersonId()).isEqualTo(personId);
		assertThat(account.getTypeAccount()).isEqualTo(TypeAccount.AHO);
		assertThat(account.getAccountNumber()).isEqualTo(123456789);
		assertThat(account.getInitialBalance()).isEqualTo(ChallengeAccountConstant.INITIAL_BALANCE);
		assertThat(account.getStatus()).isTrue();
	}

	@Test
	@Order(2)
	void testMovementEntity(){
		LocalDate movementDate = LocalDate.now();
		UUID accountId = UUID.randomUUID();
		Movements movement = new Movements();
		movement.setMovementDate(movementDate);
		movement.setTypeMovement(TypeMovement.DEP);
		movement.setValue(100.0);
		movement.setAccountId(accountId);
		movement.setStatus(Boolean.TRUE);

		assertThat(movement.getAccountId()).isEqualTo(accountId);
		assertThat(movement.getMovementDate()).isEqualTo(movementDate);
		assertThat(movement.getTypeMovement()).isEqualTo(TypeMovement.DEP);
		assertThat(movement.getValue()).isEqualTo(100.0);
		assertThat(movement.getStatus()).isTrue();
	}
}
