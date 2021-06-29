package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.edu.ifpb.assetmanagerapi.api.dto.input.EmprestimoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.StatusEmprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EmprestimoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EmprestimoControllerTests {
	
	private final String url = "http://localhost:8081/emprestimos";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjUwMzY1NDgsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNzRjY2U4OGQtOGI4ZS00YzI4LWEyNTItMDZlYmNjODY1NTFmIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.nTrTmHzbvVkZ52C0G4nulaUAvQb7k9Hs-iF75ArJzu4";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	private Equipamento equipamentoCadastrado;
	
	private Categoria categoriaCadastrada;
	
	private Emprestimo emprestimoCadastrado;
	
	@BeforeEach
	void setUp() {
		categoriaCadastrada = new Categoria();
		categoriaCadastrada.setNome("Computadores");
		categoriaCadastrada.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaCadastrada);
		
		equipamentoCadastrado = new Equipamento();
		equipamentoCadastrado.setDescricao("Notebook Pirata");
		equipamentoCadastrado.setBloco("A");
		equipamentoCadastrado.setSala("13");
		equipamentoCadastrado.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoCadastrado.setNumero(162);
		equipamentoCadastrado.setNumeroSerie("9883593h");
		equipamentoCadastrado.setCategoria(categoriaCadastrada);
		equipamentoRepository.save(equipamentoCadastrado);
		
		emprestimoCadastrado = new Emprestimo();
		emprestimoCadastrado.setStatus(StatusEmprestimo.EMPRESTADO);
		emprestimoCadastrado.setDataPrevistaRetorno(LocalDateTime.now());
		emprestimoCadastrado.setDataRetorno(LocalDateTime.now().plusDays(2));
		emprestimoCadastrado.setDataSaida(LocalDateTime.now());
		emprestimoCadastrado.setLinkChamadoSuap("link");
		emprestimoCadastrado.setEquipamento(equipamentoCadastrado);
		emprestimoRepository.save(emprestimoCadastrado);
	}
	
	@AfterEach
	void tearDown() {
		emprestimoRepository.deleteAll();
		equipamentoRepository.delete(equipamentoCadastrado);
		categoriaRepository.delete(categoriaCadastrada);
	}
	
	@Test
	@DisplayName("Listando os emprestimos")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um emprestimo já cadastrado")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + emprestimoCadastrado.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um emprestimo não existente")
	void buscarEmprestimoNaoCadastrado() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando um emprestimo com os dados corretos")
	void cadastrar() throws Exception {
		EmprestimoInputDTO emprestimo = new EmprestimoInputDTO();
		emprestimo.setDataPrevistaRetorno(LocalDateTime.now().minusDays(2));
		emprestimo.setEquipamentoId(equipamentoCadastrado.getId());
		emprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(emprestimo)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um emprestimo faltando dados")
	void cadastrarFaltandoDados() throws Exception {
		EmprestimoInputDTO emprestimo = new EmprestimoInputDTO();
		emprestimo.setEquipamentoId(equipamentoCadastrado.getId());
		emprestimo.setDataPrevistaRetorno(LocalDateTime.now());
		emprestimo.setLinkChamadoSuap("linkkk");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(emprestimo)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um emprestimo com equipamento inexistente")
	void cadastrarComEquipamentoInexistente() throws Exception {
		EmprestimoInputDTO emprestimo = new EmprestimoInputDTO();
		emprestimo.setDataPrevistaRetorno(LocalDateTime.now());
		emprestimo.setEquipamentoId(1500L);
		emprestimo.setLinkChamadoSuap("linkkk");
		emprestimo.setStatus(StatusEmprestimo.EMPRESTADO);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(emprestimo)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Editando um emprestimo")
	void editar() throws Exception {
		EmprestimoInputDTO emprestimo = new EmprestimoInputDTO();
		emprestimo.setDataPrevistaRetorno(LocalDateTime.now());
		emprestimo.setEquipamentoId(equipamentoCadastrado.getId());
		emprestimo.setDataRetorno(LocalDateTime.now());
		emprestimo.setLinkChamadoSuap("ma oe");
		emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
		mockMvc.perform(put(url + "/" + emprestimoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(emprestimo)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("deletando um emprestimo válido")
	void deletar() throws Exception {
		mockMvc.perform(delete(url + "/" + emprestimoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("deletando um emprestimo inválido (inexistente)")
	void deletarEmprestimoInvalido() throws Exception {
		mockMvc.perform(delete(url + "/16520")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(EmprestimoInputDTO emprestimo) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(emprestimo);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}