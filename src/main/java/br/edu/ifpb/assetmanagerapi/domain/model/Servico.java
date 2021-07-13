package br.edu.ifpb.assetmanagerapi.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Servico extends Saida {
	
	private LocalDateTime dataRetorno;
	
	private String descricao;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoServico tipoServico;
	
	@ManyToOne(targetEntity = Equipamento.class, fetch = FetchType.LAZY, optional = false)
	private Equipamento equipamento;
	
	@ManyToMany
	@JoinTable(name = "servico_insumo", joinColumns = @JoinColumn(name = "servico_id"), 
		inverseJoinColumns = @JoinColumn(name = "retirada_id"))
	private List<RetiradaInsumo> retiradas = new ArrayList<>();
	
}