package br.edu.ifpb.assetmanagerapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.dto.output.DashboardDTO;
import br.edu.ifpb.assetmanagerapi.domain.service.DashboardService;

@RestController
@RequestMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public DashboardDTO dashboard() {
		return dashboardService.dashboard();
	}
	
}