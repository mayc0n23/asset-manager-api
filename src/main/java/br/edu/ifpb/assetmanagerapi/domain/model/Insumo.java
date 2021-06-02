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
public class Insumo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private char estante;
	
	@Column(nullable = false)
	private short prateleira;
	
	@Column(nullable = false)
	private float quantidadeMinima;
	
	@Column(nullable = false)
	private String unidadeDeMedida;
	
	@Column(nullable = false)
	private float quantidadeAtual;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Categoria categoria;
	
	@Column(nullable = false)
	private boolean visivel = true;
	
}