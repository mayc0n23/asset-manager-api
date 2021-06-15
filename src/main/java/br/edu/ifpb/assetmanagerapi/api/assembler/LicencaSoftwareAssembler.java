package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.LicencaSoftwareDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;

@Component
public class LicencaSoftwareAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public LicencaSoftwareDTO toDTO(LicencaSoftware licencaSoftware) {
		return modelMapper.map(licencaSoftware, LicencaSoftwareDTO.class);
	}
	
	public List<LicencaSoftwareDTO> toCollectionDTO(List<LicencaSoftware> licencas) {
		return licencas.stream()
				.map(licenca -> toDTO(licenca))
				.collect(Collectors.toList());
				
	}
	
}