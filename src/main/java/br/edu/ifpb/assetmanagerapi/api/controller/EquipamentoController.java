package br.edu.ifpb.assetmanagerapi.api.controller;

import br.edu.ifpb.assetmanagerapi.api.assembler.EquipamentoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.EquipamentoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.EquipamentoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EquipamentoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public EquipamentoDTO cadastrar(@RequestBody @Valid EquipamentoInputDTO equipamentoInput) {
        return equipamentoAssembler.toDTO(equipamentoService.salvar(equipamentoDisassembler.toDomainObject(equipamentoInput)));
    }

    @PreAuthorize("isAuthenticated")
    @PutMapping("/{equipamentoId}")
    public EquipamentoDTO editar(@PathVariable Long equipamentoId, @RequestBody @Valid EquipamentoInputDTO equipamentoInput) {
        Equipamento equipamentoAtual = equipamentoService.buscar(equipamentoId);
        equipamentoDisassembler.copyToDomainObject(equipamentoInput, equipamentoAtual);
        return equipamentoAssembler.toDTO(equipamentoService.salvar(equipamentoAtual));
    }

    @PreAuthorize("isAuthenticated")
    @DeleteMapping("/{equipamentoId}")
    public void deletar(@PathVariable Long equipamentoId) {
        Equipamento equipamento = equipamentoService.buscar(equipamentoId);
        if (equipamento == null) return;
        equipamentoService.deletar(equipamento);
    }

}