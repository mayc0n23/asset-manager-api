package br.edu.ifpb.assetmanagerapi.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Saida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@EqualsAndHashCode.Include
	private Long id;
	
	@CreationTimestamp
	private LocalDateTime dataSaida;
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
}