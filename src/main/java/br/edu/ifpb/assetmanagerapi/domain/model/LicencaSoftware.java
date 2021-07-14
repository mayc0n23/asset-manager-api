package br.edu.ifpb.assetmanagerapi.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	private int maximoAtivacoes;
	
	@Column(nullable = false)
	private boolean ativacoesInfinitas;
	
	@Column(nullable = false)
	private int quantidadeUsada = 0;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Categoria categoria;
	
	@ManyToMany
	@JoinTable(name = "aplicacao_licenca_software", joinColumns = @JoinColumn(name = "licenca_software_id"),
			inverseJoinColumns = @JoinColumn(name = "equipamento_id"))
	private List<Equipamento> equipamentosAssociados = new ArrayList<>();
}