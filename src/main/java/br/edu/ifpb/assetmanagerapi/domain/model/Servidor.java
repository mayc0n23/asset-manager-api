package br.edu.ifpb.assetmanagerapi.domain.model;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Servidor {
	
	private String matricula;
	
	private String nome;
	
}