package br.edu.ifpb.assetmanagerapi.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Emprestimo extends Saida {
	
	@Column(nullable = false)
	private LocalDateTime dataPrevistaRetorno;
	
	private LocalDateTime dataRetorno;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusEmprestimo status;
	
	@ManyToOne(targetEntity = Equipamento.class, fetch = FetchType.LAZY, optional = false)
	private Equipamento equipamento;
	
}