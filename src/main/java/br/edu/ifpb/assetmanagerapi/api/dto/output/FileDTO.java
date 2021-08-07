package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileDTO {
	
	private InputStream inputStream;
	
	private String contentType;
	
}