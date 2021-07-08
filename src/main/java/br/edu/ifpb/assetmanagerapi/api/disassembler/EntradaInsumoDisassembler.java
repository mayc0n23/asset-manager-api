package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.EntradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.EntradaInsumo;

@Component
public class EntradaInsumoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EntradaInsumo toDomainObject(EntradaInsumoInputDTO entrada) {
		return modelMapper.map(entrada, EntradaInsumo.class);
	}
	
}