package br.edu.ifpb.assetmanagerapi.api.dto.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LicencaSoftwareDTO {
	
	private Long id;
	
	private int numero;
	
	private String software;
	
	private String chaveAtivacao;
	
	private boolean ativacoesInfinitas;
	
	private int maximoAtivacoes;
	
	private int quantidadeUsada;
	
	private CategoriaDTO categoria;
	
}