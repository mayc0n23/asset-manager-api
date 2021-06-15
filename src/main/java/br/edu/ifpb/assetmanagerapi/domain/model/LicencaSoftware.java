package br.edu.ifpb.assetmanagerapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LicencaSoftware {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private int numero;
	
	@Column(nullable = false)
	private String software;
	
	@Column(nullable = false)
	private String chaveAtivacao;
	
	@Column(nullable = false)
	private int maximoAtivacoes;
	
	@Column(nullable = false)
	private int quantidadeUsada = 0;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Categoria categoria;
	
}