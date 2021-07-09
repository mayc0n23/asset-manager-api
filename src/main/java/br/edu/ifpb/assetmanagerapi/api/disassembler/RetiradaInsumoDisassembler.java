package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.RetiradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;

@Component
public class RetiradaInsumoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RetiradaInsumo toDomainObject(RetiradaInsumoInputDTO retirada) {
		return modelMapper.map(retirada, RetiradaInsumo.class);
	}
	
}