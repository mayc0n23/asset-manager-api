package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.InsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;

@Component
public class InsumoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public InsumoDTO toDTO(Insumo insumo) {
		return modelMapper.map(insumo, InsumoDTO.class);
	}
	
	public List<InsumoDTO> toCollectionDTO(List<Insumo> insumos) {
		return insumos.stream()
				.map(insumo -> toDTO(insumo))
				.collect(Collectors.toList());
	}
	
}