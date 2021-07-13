package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsumoIdInputDTO {
	
	@NotNull
	private Long id;
	
}