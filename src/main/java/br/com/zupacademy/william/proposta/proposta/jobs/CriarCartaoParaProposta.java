package br.com.zupacademy.william.proposta.proposta.jobs;

import br.com.zupacademy.william.proposta.gateway.cartao.CartaoClient;
import br.com.zupacademy.william.proposta.gateway.cartao.GeracaoCartaoRequestGateway;
import br.com.zupacademy.william.proposta.proposta.Proposta;
import br.com.zupacademy.william.proposta.proposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CriarCartaoParaProposta {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoClient cartaoClient;

    @Transactional
    @Scheduled(cron = "0/5 * * * * *")
    public void reavaliarProposta() {
        //TODO      Refatorar para usar specification
        List<Proposta> propostas = propostaRepository.buscarPropostasComStatusElegivelQueNaoPossuemCartao();

        propostas.forEach(novaProposta -> {
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
        });
    }
}
