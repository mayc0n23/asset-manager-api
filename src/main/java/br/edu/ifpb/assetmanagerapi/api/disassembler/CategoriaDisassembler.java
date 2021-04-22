package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.CategoriaInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;

@Component
public class CategoriaDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Categoria toDomainObject(CategoriaInputDTO categoriaInput) {
		return modelMapper.map(categoriaInput, Categoria.class);
	}
	
	public void copyToDomainObject(CategoriaInputDTO categoriaInput, Categoria categoria) {
		modelMapper.map(categoriaInput, categoria);
	}
	
}