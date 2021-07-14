package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;

@Service
public class LicencaSoftwareAssociacaoService {
	
	@Autowired
	private LicencaSoftwareService licencaSoftwareService;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	public List<Equipamento> listarEquipamentosAssociados(Long licencaSoftwareId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		return licenca.getEquipamentosAssociados();
	}
	
	@Transactional
	public void associar(Long licencaSoftwareId, Long equipamentoId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		Equipamento equipamento = equipamentoService.buscar(equipamentoId);
		if (!equipamento.getCategoria().getTipoCategoria().equals(TipoCategoria.HARDWARE)) {
			throw new NegocioException("O equipamento tem que ser de HARDWARE");
		}
		if (licenca.isAtivacoesInfinitas() || (licenca.getQuantidadeUsada() < licenca.getMaximoAtivacoes())) {
			licenca.getEquipamentosAssociados().add(equipamento);
			licenca.setQuantidadeUsada(licenca.getQuantidadeUsada() + 1);
		} else {
			throw new NegocioException("O limite maximo de ativações já foi alcançado");
		}
	}
	
	@Transactional
	public void desassociar(Long licencaSoftwareId, Long equipamentoId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		Equipamento equipamento = equipamentoService.buscar(equipamentoId);
		if (licenca.getEquipamentosAssociados().remove(equipamento)) licenca.setQuantidadeUsada(licenca.getQuantidadeUsada() - 1);
	}
	
}