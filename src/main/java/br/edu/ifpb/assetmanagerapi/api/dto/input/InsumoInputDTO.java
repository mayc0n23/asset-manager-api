package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsumoInputDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull
	private char estante;
	
	@NotNull
	private short prateleira;
	
	@PositiveOrZero
	private float quantidadeMinima;
	
	@NotBlank
	private String unidadeDeMedida;
	
	@PositiveOrZero
	private float quantidadeAtual;
	
	@NotNull
	private Long categoriaId;
	
}