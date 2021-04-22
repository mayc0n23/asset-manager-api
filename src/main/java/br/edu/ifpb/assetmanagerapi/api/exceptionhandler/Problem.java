package br.edu.ifpb.assetmanagerapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class Problem {
	
	private final Integer status;
	
	private final String detail;
	
	private final OffsetDateTime timestamp;
	
	private List<Object> objects;
	
	@Builder
	@Getter
	public static class Object {
		private String name;
		private String message;
	}
	
}