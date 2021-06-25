package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.SetorDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;

@Component
public class SetorAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public SetorDTO toDTO(Setor setor) {
		return modelMapper.map(setor, SetorDTO.class);
	}
	
	public List<SetorDTO> toCollectionDTO(List<Setor> setores) {
		return setores.stream()
				.map(setor -> toDTO(setor))
				.collect(Collectors.toList());
	}
	
}