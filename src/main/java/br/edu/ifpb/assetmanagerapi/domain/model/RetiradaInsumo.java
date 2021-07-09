package br.edu.ifpb.assetmanagerapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class RetiradaInsumo extends Saida {
	
	@Column(nullable = false)
	private float quantidade;
	
	@ManyToOne(targetEntity = Insumo.class, fetch = FetchType.LAZY, optional = false)
	private Insumo insumo;
	
}