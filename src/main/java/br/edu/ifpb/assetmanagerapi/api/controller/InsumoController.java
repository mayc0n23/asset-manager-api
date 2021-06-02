package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.InsumoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.InsumoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.InsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.InsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.service.InsumoService;

@RestController
@RequestMapping(value = "/insumos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class InsumoController {
	
	@Autowired
	private InsumoService insumoService;
	
	@Autowired
	private InsumoAssembler insumoAssembler;
	
	@Autowired
	private InsumoDisassembler insumoDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<InsumoDTO> listar() {
		return insumoAssembler.toCollectionDTO(insumoService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{insumoId}")
	public InsumoDTO buscar(@PathVariable Long insumoId) {
		return insumoAssembler.toDTO(insumoService.buscar(insumoId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InsumoDTO cadastrar(@RequestBody @Valid InsumoInputDTO insumoInput) {
		return insumoAssembler.toDTO(insumoService.salvar(insumoDisassembler.toDomainObject(insumoInput)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{insumoId}")
	public InsumoDTO editar(@PathVariable Long insumoId, @RequestBody @Valid InsumoInputDTO insumoInput) {
		Insumo insumoAtual = insumoService.buscar(insumoId);
		insumoDisassembler.copyToDomainObject(insumoInput, insumoAtual);
		insumoAtual.setId(insumoId);
		return insumoAssembler.toDTO(insumoService.salvar(insumoAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{insumoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long insumoId) {
		insumoService.deletar(insumoId);
	}
	
}