package br.edu.ifpb.assetmanagerapi.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("asset-manager.storage")
public class StorageProperties {
	
	private Path diretorioFotos;
	
}