package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.edu.ifpb.assetmanagerapi.api.dto.input.EquipamentoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class EquipamentoControllerTests {
	
	private final String url = "http://localhost:8081/equipamentos";
	
	//Se os testes falharem e retornar o status 401, então criem um novo token e alterem aqui
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI3MTI3NDIsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiYmRlOTg0ZjItMjNmNS00NmVjLWI3ZWItNzI4YzA2MWZmNzhlIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.RThqkwtpwXe5IXkqQz_U41CJwXIts4DnNOoFkTKmI88";
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	private Categoria categoriaCadastrada;
	
	private Equipamento equipamentoCadastrado;
	
	private Categoria categoria;
	
	@BeforeEach
	void setUp() {
		categoriaCadastrada = new Categoria();
		categoriaCadastrada.setNome("Cabos");
		categoriaCadastrada.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaCadastrada);
		
		categoria = new Categoria();
		categoria.setNome("Fios");
		categoria.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoria);
		
		equipamentoCadastrado = new Equipamento();
		equipamentoCadastrado.setDescricao("Mouse gamer");
		equipamentoCadastrado.setBloco("A");
		equipamentoCadastrado.setSala("13");
		equipamentoCadastrado.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoCadastrado.setNumero(162);
		equipamentoCadastrado.setNumeroSerie("9883593h");
		equipamentoCadastrado.setCategoria(categoriaCadastrada);
		equipamentoRepository.save(equipamentoCadastrado);
	}
	
	@AfterEach
	void tearDown() {
		equipamentoRepository.deleteAll();
		categoriaRepository.delete(categoriaCadastrada);
		categoriaRepository.delete(categoria);
	}

	@Test
	@DisplayName("Listando os equipamentos")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um equipamento já cadastrada")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + equipamentoCadastrado.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um equipamento não existente")
	void buscarEquipamentoNaoCadastrado() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando um novo equipamento")
	void cadastrar() throws Exception {
		EquipamentoInputDTO equipamento = new EquipamentoInputDTO();
		equipamento.setDescricao("Mousepad");
		equipamento.setBloco("A");
		equipamento.setSala("13");
		equipamento.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamento.setNumero(162);
		equipamento.setNumeroSerie("9883593h");
		equipamento.setCategoriaId(categoria.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(equipamento)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um novo equipamento sem informar a descrição")
	void cadastrarComDadosFaltando() throws Exception {
		EquipamentoInputDTO equipamento = new EquipamentoInputDTO();
		equipamento.setBloco("A");
		equipamento.setSala("13");
		equipamento.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamento.setNumero(162);
		equipamento.setNumeroSerie("9883593h");
		equipamento.setCategoriaId(categoriaCadastrada.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(equipamento)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um equipamento já existente")
	void cadastrarEquipamentoExistente() throws Exception {
		EquipamentoInputDTO equipamento = new EquipamentoInputDTO();
		equipamento.setDescricao("Mouse gamer");
		equipamento.setBloco("A");
		equipamento.setSala("13");
		equipamento.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamento.setNumero(162);
		equipamento.setNumeroSerie("9883593h");
		equipamento.setCategoriaId(categoriaCadastrada.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(equipamento)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um equipamento em uma categoria que não existe")
	void cadastrarEquipamentoComCategoriaInexistente() throws Exception {
		EquipamentoInputDTO equipamento = new EquipamentoInputDTO();
		equipamento.setDescricao("Headset");
		equipamento.setBloco("A");
		equipamento.setSala("13");
		equipamento.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamento.setNumero(162);
		equipamento.setNumeroSerie("9883593h");
		equipamento.setCategoriaId(new Long(1000));
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(equipamento)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Editando equipamento")
	void editar() throws Exception {
		EquipamentoInputDTO equipamento = new EquipamentoInputDTO();
		equipamento.setDescricao("monitor gamer");
		equipamento.setBloco("B");
		equipamento.setSala("16");
		equipamento.setEstadoConservacao(EstadoConservacao.DANIFICADO);
		equipamento.setNumero(162);
		equipamento.setNumeroSerie("9883593h");
		equipamento.setCategoriaId(categoriaCadastrada.getId());
		mockMvc.perform(put(url + "/" + equipamentoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(equipamento)))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(EquipamentoInputDTO equipamento) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(equipamento);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}