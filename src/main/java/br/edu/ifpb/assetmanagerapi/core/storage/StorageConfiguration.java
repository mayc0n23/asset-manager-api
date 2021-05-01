package br.edu.ifpb.assetmanagerapi.core.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ifpb.assetmanagerapi.domain.service.FileStorageService;
import br.edu.ifpb.assetmanagerapi.infrastructure.storage.LocalFileStorageService;

@Configuration
public class StorageConfiguration {
	
	@Bean
	public FileStorageService fileStorageService() {
		return new LocalFileStorageService();
	}
	
}