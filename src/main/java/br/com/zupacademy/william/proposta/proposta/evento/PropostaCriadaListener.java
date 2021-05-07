package br.com.zupacademy.william.proposta.proposta.evento;

import br.com.zupacademy.william.proposta.gateway.analisefinanceira.AnaliseFinanceiraClient;
import br.com.zupacademy.william.proposta.gateway.cartao.CartaoClient;
import br.com.zupacademy.william.proposta.gateway.cartao.GeracaoCartaoRequestGateway;
import br.com.zupacademy.william.proposta.proposta.Proposta;
import br.com.zupacademy.william.proposta.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PropostaCriadaListener {

    @Autowired
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    @Autowired
    private CartaoClient cartaoClient;

    @Autowired
    private PropostaRepository propostaRepository;

    @Async
    @EventListener
    public void handleEvent(PropostaCriadaEvent event) {
        Proposta novaProposta = event.getProposta();

        try {
            var geracaoCartaoRequest = new GeracaoCartaoRequestGateway(
                    String.valueOf(novaProposta.getId()),
                    novaProposta.getDocumento(),
                    novaProposta.getNome());

            var geracaoCartaoResponseGateway = cartaoClient.cadastrarCartao(geracaoCartaoRequest);
            var novoCartao = geracaoCartaoResponseGateway.toModel();
            novaProposta.vincularCartao(novoCartao);
        } catch (Exception ignored) {
        }

        propostaRepository.save(novaProposta);
    }
}
