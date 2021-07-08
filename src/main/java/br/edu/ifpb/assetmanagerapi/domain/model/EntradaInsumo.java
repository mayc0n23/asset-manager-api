package br.edu.ifpb.assetmanagerapi.domain.model;

import java.time.LocalDateTime;

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
public class EntradaInsumo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime data;
	
	@Column(nullable = false)
	private LocalDateTime dataValidade;
	
	@Column(nullable = false)
	private float quantidade;
	
	@ManyToOne(targetEntity = Insumo.class, fetch = FetchType.LAZY, optional = false)
	private Insumo insumo;
	
}