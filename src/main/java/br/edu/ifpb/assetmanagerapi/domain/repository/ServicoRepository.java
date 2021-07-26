package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface ServicoRepository extends CustomJpaRepository<Servico, Long> {

	List<Servico> findTop20ByOrderByDataSaidaDesc();
	
}