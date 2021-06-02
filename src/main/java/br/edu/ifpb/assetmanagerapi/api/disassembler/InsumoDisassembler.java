package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.InsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;

@Component
public class InsumoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Insumo toDomainObject(InsumoInputDTO insumoInput) {
		Insumo insumo = modelMapper.map(insumoInput, Insumo.class);
		insumo.setId(null);
		return insumo;
	}
	
	public void copyToDomainObject(InsumoInputDTO insumoInput, Insumo insumo) {
		insumo.setCategoria(new Categoria());
		modelMapper.map(insumoInput, insumo);
	}

}