package br.edu.ifpb.assetmanagerapi.domain.model;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

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
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "matricula", column = @Column(name = "matriculaExpedidor")),
		@AttributeOverride(name = "nome", column = @Column(name = "nomeExpedidor")),
	})
	private Servidor expedidor;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "matricula", column = @Column(name = "matriculaSolicitante")),
		@AttributeOverride(name = "nome", column = @Column(name = "nomeSolicitante")),
	})
	private Servidor solicitante;
	
	@ManyToOne(targetEntity = Setor.class, fetch = FetchType.LAZY, optional = false)
	private Setor setor;
	
}