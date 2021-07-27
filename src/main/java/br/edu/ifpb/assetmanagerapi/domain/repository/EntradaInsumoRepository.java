package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.assetmanagerapi.domain.model.EntradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;

public interface EntradaInsumoRepository extends JpaRepository<EntradaInsumo, Long> {
	
	Optional<EntradaInsumo> findByIdAndInsumo(Long id, Insumo insumo);
	
	List<EntradaInsumo> findByInsumoOrderByDataDesc(Insumo insumo);
	
	List<EntradaInsumo> findTop20ByOrderByDataDesc();
	
}