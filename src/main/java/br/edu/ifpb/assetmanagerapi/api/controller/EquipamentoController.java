package br.edu.ifpb.assetmanagerapi.api.controller;

import br.edu.ifpb.assetmanagerapi.api.assembler.EquipamentoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.EquipamentoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.EquipamentoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EquipamentoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.service.EquipamentoService;
import br.edu.ifpb.assetmanagerapi.domain.service.FileStorageService;
import br.edu.ifpb.assetmanagerapi.infrastructure.storage.StorageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/equipamentos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private EquipamentoAssembler equipamentoAssembler;

    @Autowired
    private EquipamentoDisassembler equipamentoDisassembler;

    @Autowired
    private FileStorageService fileStorageService;
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<EquipamentoDTO> listar() {
        return equipamentoAssembler.toCollectionDTO(equipamentoService.listar());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{equipamentoId}")
    public EquipamentoDTO buscar(@PathVariable Long equipamentoId) {
        return equipamentoAssembler.toDTO(equipamentoService.buscar(equipamentoId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipamentoDTO cadastrar(@RequestBody @Valid EquipamentoInputDTO equipamentoInput) {
        return equipamentoAssembler.toDTO(equipamentoService.salvar(equipamentoDisassembler.toDomainObject(equipamentoInput)));
    }

    @PreAuthorize("isAuthenticated")
    @PutMapping("/{equipamentoId}")
    public EquipamentoDTO editar(@PathVariable Long equipamentoId, @RequestBody @Valid EquipamentoInputDTO equipamentoInput) {
        Equipamento equipamentoAtual = equipamentoService.buscar(equipamentoId);
        equipamentoDisassembler.copyToDomainObject(equipamentoInput, equipamentoAtual);
        equipamentoAtual.setId(equipamentoId);
        return equipamentoAssembler.toDTO(equipamentoService.salvar(equipamentoAtual));
    }

    @PreAuthorize("isAuthenticated")
    @DeleteMapping("/{equipamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long equipamentoId) {
        equipamentoService.deletar(equipamentoId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{equipamentoId}/file")
    @ResponseStatus(HttpStatus.CREATED)
    public void inserirArquivo(@PathVariable Long equipamentoId, MultipartFile arquivo) {
    	try {
    		InputStream inputStream = arquivo.getInputStream();
    		FileStorageService.File file = FileStorageService.File.builder()
        			.nomeArquivo(fileStorageService.gerarNomeArquivo(arquivo.getOriginalFilename()))
        			.contentType(arquivo.getContentType())
        			.inputStream(inputStream).build();
    		
    		equipamentoService.inserirArquivo(equipamentoId, file);
    	} catch (IOException e) {
    		throw new StorageException("Não foi possivel armazenar o arquivo");
    	}
    }
    
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{equipamentoId}/file")
    public void editarArquivo(@PathVariable Long equipamentoId, MultipartFile arquivo) {
    	try {
    		InputStream inputStream = arquivo.getInputStream();
    		FileStorageService.File file = FileStorageService.File.builder()
        			.nomeArquivo(fileStorageService.gerarNomeArquivo(arquivo.getOriginalFilename()))
        			.contentType(arquivo.getContentType())
        			.inputStream(inputStream).build();
    		
    		equipamentoService.editarArquivo(equipamentoId, file);
    	} catch (IOException e) {
    		throw new StorageException("Não foi possivel editar o arquivo");
    	}
    }
    
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{equipamentoId}/file")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarArquivo(@PathVariable Long equipamentoId) {
    	equipamentoService.removerArquivo(equipamentoId);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{equipamentoId}/file")
    public ResponseEntity<?> servirFoto(@PathVariable Long equipamentoId) {
    	InputStream inputStream = equipamentoService.recuperarArquivo(equipamentoId);
    	return ResponseEntity.ok()
    			.body(new InputStreamResource(inputStream));
    }
    
}