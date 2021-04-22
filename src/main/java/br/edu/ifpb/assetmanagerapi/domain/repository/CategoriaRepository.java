package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;

@Repository
public interface CategoriaRepository extends CustomJpaRepository<Categoria, Long> {

	Optional<Categoria> findByNome(String nome);
	
}