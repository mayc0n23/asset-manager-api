package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.CategoriaAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.CategoriaDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.CategoriaInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.CategoriaDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private CategoriaAssembler categoriaAssembler;
	
	@Autowired
	private CategoriaDisassembler categoriaDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<CategoriaDTO> listar() {
		return categoriaAssembler.toCollectionDTO(categoriaService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{categoriaId}")
	public CategoriaDTO buscar(@PathVariable Long categoriaId) {
		return categoriaAssembler.toDTO(categoriaService.buscar(categoriaId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	public CategoriaDTO cadastrar(@RequestBody @Valid CategoriaInputDTO categoriaInput) {
		return categoriaAssembler.toDTO(categoriaService.salvar(categoriaDisassembler.toDomainObject(categoriaInput)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{categoriaId}")
	public CategoriaDTO editar(@PathVariable Long categoriaId, @RequestBody @Valid CategoriaInputDTO categoriaInput) {
		Categoria categoriaAtual = categoriaService.buscar(categoriaId);
		categoriaDisassembler.copyToDomainObject(categoriaInput, categoriaAtual);
		return categoriaAssembler.toDTO(categoriaService.salvar(categoriaAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{categoriaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long categoriaId) {
		categoriaService.deletar(categoriaId);
	}
	
}