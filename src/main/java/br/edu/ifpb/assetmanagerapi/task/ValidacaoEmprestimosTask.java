package br.edu.ifpb.assetmanagerapi.task;

import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.StatusEmprestimo;
import br.edu.ifpb.assetmanagerapi.domain.service.EmprestimoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ValidacaoEmprestimosTask {

    private static final Logger log = LoggerFactory.getLogger(ValidacaoEmprestimosTask.class);

    @Autowired
    private EmprestimoService emprestimoService;

    @Scheduled(cron = "0 55 23 * * MON-FRI")
    public void validarExpiracaoDosEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listar();
        log.info("Validando se empréstimos estão expirados");

        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getStatus().equals(StatusEmprestimo.EMPRESTADO)) {
                LocalDateTime dataAtual = LocalDateTime.now();
                LocalDateTime dataRetorno = emprestimo.getDataRetorno();
                LocalDateTime dataPrevista = emprestimo.getDataPrevistaRetorno();

                if (dataAtual.isAfter(dataPrevista) && dataRetorno == null) {
                    emprestimo.setStatus(StatusEmprestimo.EXPIRADO);
                    emprestimoService.salvar(emprestimo);

                    log.info("O empréstimo " + emprestimo.getId() + " do equipamento " + emprestimo.getEquipamento().getDescricao()
                            + " realizado no dia " + emprestimo.getDataSaida() + " expirou no dia " + dataPrevista);
                }
            }
        }
    }

}
