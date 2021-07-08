package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.EntradaInsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.EntradaInsumo;

@Component
public class EntradaInsumoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EntradaInsumoDTO toDTO(EntradaInsumo entrada) {
		return modelMapper.map(entrada, EntradaInsumoDTO.class);
	}
	
	public List<EntradaInsumoDTO> toCollectionDTO(List<EntradaInsumo> entradas) {
		return entradas.stream()
				.map(entrada -> toDTO(entrada))
				.collect(Collectors.toList());
	}
	
}