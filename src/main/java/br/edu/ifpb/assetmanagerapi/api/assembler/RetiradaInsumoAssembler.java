package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.RetiradaInsumoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;

@Component
public class RetiradaInsumoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RetiradaInsumoDTO toDTO(RetiradaInsumo retirada) {
		return modelMapper.map(retirada, RetiradaInsumoDTO.class);
	}
	
	public List<RetiradaInsumoDTO> toCollectionDTO(List<RetiradaInsumo> retiradas) {
		return retiradas.stream()
				.map(retirada -> toDTO(retirada))
				.collect(Collectors.toList());
	}
	
}