package br.edu.ifpb.assetmanagerapi.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsumoDTO {
	
	private Long id;
	
	private String nome;
	
	private char estante;
	
	private short prateleira;
	
	private float quantidadeMinima;
	
	private String unidadeDeMedida;
	
	private float quantidadeAtual;
	
	private CategoriaDTO categoria;
	
	private boolean visivel;

}