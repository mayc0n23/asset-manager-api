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

import br.edu.ifpb.assetmanagerapi.api.dto.input.InsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.InsumoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class InsumoControllerTests {
	
	private final String url = "http://localhost:8081/insumos";
	
	//Se os testes falharem e retornar o status 401, então criem um novo token e alterem aqui
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI3MTI3NDIsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiYmRlOTg0ZjItMjNmNS00NmVjLWI3ZWItNzI4YzA2MWZmNzhlIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.RThqkwtpwXe5IXkqQz_U41CJwXIts4DnNOoFkTKmI88";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private InsumoRepository insumoRepository;
	
	private Categoria categoriaCadastrada;
	
	private Insumo insumoCadastrado;
	
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
		
		insumoCadastrado = new Insumo();
		insumoCadastrado.setNome("Resma de papel");
		insumoCadastrado.setEstante('A');
		insumoCadastrado.setPrateleira((short) 2);
		insumoCadastrado.setQuantidadeMinima(1);
		insumoCadastrado.setQuantidadeAtual(10);
		insumoCadastrado.setUnidadeDeMedida("UND");
		insumoCadastrado.setCategoria(categoriaCadastrada);
		
		insumoRepository.save(insumoCadastrado);
	}
	
	@AfterEach
	void tearDown() {
		insumoRepository.deleteAll();
		categoriaRepository.delete(categoriaCadastrada);
		categoriaRepository.delete(categoria);
	}

	@Test
	@DisplayName("Listando os insumos")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um insumo já cadastrado")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + insumoCadastrado.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um insumo não existente")
	void buscarInsumoNaoCadastrado() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando um novo insumo")
	void cadastrar() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setNome("Caneta azul");
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(1);
		insumo.setQuantidadeAtual(20);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(categoria.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um novo insumo com a quantidade atual menor que a quantidade minima")
	void cadastrarComQuantidadeInvalida() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setNome("Caneta azul");
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(10);
		insumo.setQuantidadeAtual(1);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(categoria.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um novo insumo sem informar o nome")
	void cadastrarComDadosFaltando() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(1);
		insumo.setQuantidadeAtual(20);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(categoria.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um insumo já existente")
	void cadastrarInsumoExistente() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setNome("Resma de papel");
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(1);
		insumo.setQuantidadeAtual(20);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(categoria.getId());
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um insumo em uma categoria que não existe")
	void cadastrarInsumoComCategoriaInexistente() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setNome("Caneta azul");
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(1);
		insumo.setQuantidadeAtual(20);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(new Long(10000));
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Editando insumo")
	void editar() throws Exception {
		InsumoInputDTO insumo = new InsumoInputDTO();
		insumo.setNome("Caneta preta");
		insumo.setEstante('B');
		insumo.setPrateleira((short) 3);
		insumo.setQuantidadeMinima(1);
		insumo.setQuantidadeAtual(20);
		insumo.setUnidadeDeMedida("UND");
		insumo.setCategoriaId(categoria.getId());
		mockMvc.perform(put(url + "/" + insumoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(insumo)))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(InsumoInputDTO insumo) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(insumo);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}