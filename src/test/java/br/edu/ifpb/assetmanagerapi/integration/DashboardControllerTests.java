package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTests {
	
	private final String url = "http://localhost:8081/dashboard";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2Mjc0NjQwMTAsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiMjQxNmM0OGUtMTI0OC00NGIwLWE5YjEtMzA2MjgzMmNkYTIzIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.qG13GQJip_93GFCIP7itN-whjW7YJeHpuLmqX8F3Iro";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Pegando os dados do dashboard")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
}