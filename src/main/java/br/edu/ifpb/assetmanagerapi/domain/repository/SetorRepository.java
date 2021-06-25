package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface SetorRepository extends CustomJpaRepository<Setor, Long> {
	
	Optional<Setor> findBySigla(String sigla);
	
}