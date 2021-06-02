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

import br.edu.ifpb.assetmanagerapi.api.dto.input.CategoriaInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTests {
	
	private final String url = "http://localhost:8081/categorias";
	
	//Se os testes falharem e retornar o status 401, então criem um novo token e alterem aqui
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjI3MTI3NDIsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiYmRlOTg0ZjItMjNmNS00NmVjLWI3ZWItNzI4YzA2MWZmNzhlIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.RThqkwtpwXe5IXkqQz_U41CJwXIts4DnNOoFkTKmI88";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	private Categoria categoriaCadastrada;
	
	@BeforeEach
	void setUp() {
		categoriaCadastrada = new Categoria();
		categoriaCadastrada.setNome("Teclado");
		categoriaCadastrada.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaCadastrada);
	}
	
	@AfterEach
	void tearDown() {
		categoriaRepository.delete(categoriaCadastrada);
	}

	@Test
	@DisplayName("Listando as categorias")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando uma categoria já cadastrada")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + categoriaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando uma categoria não existente")
	void buscarCategoriaNaoCadastrada() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	
	/*
	 * Lembrar de deletar essa categoria no banco de dados, 
	 * se não os testes falham quando executados novamente
	 */
	@Test
	@DisplayName("Cadastrando uma nova categoria")
	void cadastrar() throws Exception {
		CategoriaInputDTO categoria = new CategoriaInputDTO();
		categoria.setNome("Mouse");
		categoria.setTipoCategoria(TipoCategoria.HARDWARE);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(categoria)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova categoria sem informar o nome")
	void cadastrarComDadosFaltando() throws Exception {
		CategoriaInputDTO categoria = new CategoriaInputDTO();
		categoria.setTipoCategoria(TipoCategoria.HARDWARE);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(categoria)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma categoria já existente")
	void cadastrarCategoriaExistente() throws Exception {
		CategoriaInputDTO categoria = new CategoriaInputDTO();
		categoria.setNome("Teclado");
		categoria.setTipoCategoria(TipoCategoria.HARDWARE);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(categoria)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Editando categoria")
	void editar() throws Exception {
		CategoriaInputDTO categoria = new CategoriaInputDTO();
		categoria.setNome("Fonte");
		categoria.setTipoCategoria(TipoCategoria.HARDWARE);
		mockMvc.perform(put(url + "/" + categoriaCadastrada.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(categoria)))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(CategoriaInputDTO categoria) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(categoria);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}