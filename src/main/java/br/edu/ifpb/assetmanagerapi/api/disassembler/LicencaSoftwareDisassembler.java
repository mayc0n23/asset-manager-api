package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.LicencaSoftwareInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;

@Component
public class LicencaSoftwareDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public LicencaSoftware toDomainObject(LicencaSoftwareInputDTO licencaInput) {
		LicencaSoftware licenca = modelMapper.map(licencaInput, LicencaSoftware.class);
		licenca.setId(null);
		return licenca;
	}
	
	public void copyToDomainObject(LicencaSoftwareInputDTO licencaInput, LicencaSoftware licenca) {
		licenca.setCategoria(new Categoria());
		modelMapper.map(licencaInput, licenca);
	}
	
}