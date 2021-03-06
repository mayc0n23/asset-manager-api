package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface EmprestimoRepository extends CustomJpaRepository<Emprestimo, Long> {
	
	List<Emprestimo> findTop20ByOrderByDataSaidaDesc();
	
}