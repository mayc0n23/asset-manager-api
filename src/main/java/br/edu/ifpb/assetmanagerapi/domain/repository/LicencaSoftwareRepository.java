package br.edu.ifpb.assetmanagerapi.domain.repository;

import org.springframework.stereotype.Repository;

import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.infrastructure.repository.CustomJpaRepository;

@Repository
public interface LicencaSoftwareRepository extends CustomJpaRepository<LicencaSoftware, Long> {

}