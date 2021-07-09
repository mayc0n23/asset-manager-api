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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.RetiradaInsumoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.RetiradaInsumoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.RetiradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.RetiradaInsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.service.RetiradaInsumoService;

@RestController
@RequestMapping(value = "/insumos/{insumoId}/retiradas", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class RetiradaInsumoController {
	
	@Autowired
	private RetiradaInsumoService retiradaInsumoService;
	
	@Autowired
	private RetiradaInsumoAssembler retiradaInsumoAssembler;
	
	@Autowired
	private RetiradaInsumoDisassembler retiradaInsumoDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<RetiradaInsumoDTO> listar(@PathVariable Long insumoId) {
		return retiradaInsumoAssembler.toCollectionDTO(retiradaInsumoService.listar(insumoId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{retiradaId}")
	public RetiradaInsumoDTO buscar(@PathVariable Long insumoId, @PathVariable Long retiradaId) {
		return retiradaInsumoAssembler.toDTO(retiradaInsumoService.buscar(insumoId, retiradaId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RetiradaInsumoDTO cadastrar(@PathVariable Long insumoId, @RequestBody @Valid RetiradaInsumoInputDTO retirada) {
		return retiradaInsumoAssembler.toDTO(retiradaInsumoService.salvar(insumoId, retiradaInsumoDisassembler.toDomainObject(retirada)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{retiradaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long insumoId, @PathVariable Long retiradaId) {
		retiradaInsumoService.deletar(insumoId, retiradaId);
	}
	
}