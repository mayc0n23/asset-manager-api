package br.edu.ifpb.assetmanagerapi.domain.repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipamentoRepository extends CustomJpaRepository<Equipamento, Long> {
	
	Optional<Equipamento> findByDescricao(String descricao);
	
}