package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.EntradaInsumoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.EntradaInsumoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.EntradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EntradaInsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.service.EntradaInsumoService;

@RestController
@RequestMapping(value = "/insumos/{insumoId}/entradas", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class EntradaInsumoController {
	
	@Autowired
	private EntradaInsumoService entradaInsumoService;
	
	@Autowired
	private EntradaInsumoAssembler entradaInsumoAssembler;
	
	@Autowired
	private EntradaInsumoDisassembler entradaInsumoDisassembler;
	
	@GetMapping
	public List<EntradaInsumoDTO> listar(@PathVariable Long insumoId) {
		return entradaInsumoAssembler.toCollectionDTO(entradaInsumoService.listar(insumoId));
	}
	
	@GetMapping("/{entradaId}")
	public EntradaInsumoDTO buscar(@PathVariable Long insumoId, @PathVariable Long entradaId) {
		return entradaInsumoAssembler.toDTO(entradaInsumoService.buscar(insumoId, entradaId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntradaInsumoDTO cadastrar(@PathVariable Long insumoId, @RequestBody @Valid EntradaInsumoInputDTO entrada) {
		return entradaInsumoAssembler.toDTO(entradaInsumoService.salvar(insumoId, entradaInsumoDisassembler.toDomainObject(entrada)));
	}
	
	@DeleteMapping("/{entradaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long insumoId, @PathVariable Long entradaId) {
		entradaInsumoService.deletar(insumoId, entradaId);
	}
	
}