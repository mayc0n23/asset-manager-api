package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoriaInputDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull
	private TipoCategoria tipoCategoria;
	
}