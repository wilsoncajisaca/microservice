package com.wcajisaca.clientService;

import com.wcajisaca.clientService.controllers.ClientController;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.wcajisaca.clientService.dto.request.ClientDTO.clientDTOForTest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ClientController.class)
@ActiveProfiles("test")
class ChallengeApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IClientService clientService;
	private UUID clientId;

	@BeforeEach
	void setUp() {
		clientId = UUID.randomUUID();
	}

	@Test
	void testGetClientById() throws Exception {
		ClientDTO clientDTO = clientDTOForTest(clientId.toString());
		when(clientService.findById(clientId)).thenReturn(clientDTO);
		mockMvc.perform(get("/clientes/{id}", clientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.clientId").value(clientId.toString()))  // Accede a clientId dentro de data
				.andExpect(jsonPath("$.data.name").value("Wilson Cajisaca"))
				.andExpect(jsonPath("$.data.gender").value("Masculino"))
				.andExpect(jsonPath("$.data.identification").value("0106146137"))
				.andExpect(jsonPath("$.data.phone").value("0963521463"))
				.andExpect(jsonPath("$.data.address").value("Cuenca"))
				.andExpect(jsonPath("$.data.age").value(30));
	}

	@Test
	void testDeleteClient() throws Exception {
		mockMvc.perform(delete("/clientes/{id}", clientId))
				.andExpect(status().isOk());

		verify(clientService).deleteLogicById(clientId);
	}

	@Test
	void testClientEntity() {
		Client client = new Client();

		client.setClientId("wcajisaca");
		client.setPassword("password");
		client.setStatus(true);
		client.setName("Wilson Cajisaca");
		client.setGender("Masculino");
		client.setAge(30);
		client.setIdentification("0106254137");
		client.setAddress("Cuenca");
		client.setPhone("0963521463");

		assertThat(client.getClientId()).isEqualTo("wcajisaca");
		assertThat(client.getPassword()).isEqualTo("password");
		assertThat(client.getStatus()).isTrue();
		assertThat(client.getName()).isEqualTo("Wilson Cajisaca");
		assertThat(client.getGender()).isEqualTo("Masculino");
		assertThat(client.getAge()).isEqualTo(30);
		assertThat(client.getIdentification()).isEqualTo("0106254137");
		assertThat(client.getAddress()).isEqualTo("Cuenca");
		assertThat(client.getPhone()).isEqualTo("0963521463");
	}
}
