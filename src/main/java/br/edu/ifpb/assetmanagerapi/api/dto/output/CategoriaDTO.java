package br.edu.ifpb.assetmanagerapi.api.dto.output;

import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaDTO {
	
	private Long id;
	
	private String nome;
	
	private TipoCategoria tipoCategoria;
	
}