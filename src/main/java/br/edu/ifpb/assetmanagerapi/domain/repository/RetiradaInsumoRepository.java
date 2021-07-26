package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface RetiradaInsumoRepository extends CustomJpaRepository<RetiradaInsumo, Long> {
	
	Optional<RetiradaInsumo> findByIdAndInsumo(Long id, Insumo insumo);
	
	List<RetiradaInsumo> findByInsumoOrderByDataSaidaDesc(Insumo insumo);
	
}