package br.edu.ifpb.assetmanagerapi.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface LicencaSoftwareRepository extends CustomJpaRepository<LicencaSoftware, Long> {
	
	Optional<LicencaSoftware> findByChaveAtivacao(String chaveAtivacao);
	
}