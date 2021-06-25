package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.SetorInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;

@Component
public class SetorDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Setor toDomainObject(SetorInputDTO setorInput) {
		return modelMapper.map(setorInput, Setor.class);
	}
	
	public void copyToDomainObject(SetorInputDTO setorInput, Setor setor) {
		modelMapper.map(setorInput, setor);
	}
	
}