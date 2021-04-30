package br.edu.ifpb.assetmanagerapi.api.assembler;

import br.edu.ifpb.assetmanagerapi.api.dto.output.EquipamentoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EquipamentoDTO toDTO(Equipamento equipamento) {
        return modelMapper.map(equipamento, EquipamentoDTO.class);
    }

    public List<EquipamentoDTO> toCollectionDTO(List<Equipamento> equipamentos) {
        return equipamentos.stream()
                .map(equipamento -> toDTO(equipamento))
                .collect(Collectors.toList());
    }
}
