package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.CategoriaDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;

@Component
public class CategoriaAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CategoriaDTO toDTO(Categoria categoria) {
		return modelMapper.map(categoria, CategoriaDTO.class);
	}
	
	public List<CategoriaDTO> toCollectionDTO(List<Categoria> categorias) {
		return categorias.stream()
				.map(categoria -> toDTO(categoria))
				.collect(Collectors.toList());
	}
	
}