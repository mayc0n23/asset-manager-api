package br.edu.ifpb.assetmanagerapi.api.disassembler;

import br.edu.ifpb.assetmanagerapi.api.dto.input.EquipamentoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipamentoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Equipamento toDomainObject(EquipamentoInputDTO equipamentoInput) {
        return modelMapper.map(equipamentoInput, Equipamento.class);
    }

    public void copyToDomainObject(EquipamentoInputDTO equipamentoInput, Equipamento equipamento) {
    	equipamento.setCategoria(new Categoria());
        modelMapper.map(equipamentoInput, equipamento);
    }
}
