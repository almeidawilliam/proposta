package br.com.zupacademy.william.proposta.proposta;

import br.com.zupacademy.william.proposta.exception.EntidadeNaoEncontradaException;
import br.com.zupacademy.william.proposta.gateway.analisefinanceira.AnaliseFinanceiraClient;
import br.com.zupacademy.william.proposta.gateway.analisefinanceira.AnaliseFinanceiraRequest;
import br.com.zupacademy.william.proposta.infraestrutura.metricas.Metricas;
import br.com.zupacademy.william.proposta.proposta.evento.PropostaCriadaEvent;
import feign.FeignException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private Metricas metricas;

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    @Autowired
    private Tracer tracer;

    //TODO
    //      Refatorar com a anotação @Timed
    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id) {

        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
        timerConsultarProposta.record(() -> {
            Proposta proposta = propostaRepository.findById(id)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe uma proposta com id %d", id)));
        });

        Proposta proposta = propostaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe uma proposta com id %d", id)));

        PropostaDetalhesResponse propostaDetalhesResponse = new PropostaDetalhesResponse(proposta);
        return ResponseEntity.ok(propostaDetalhesResponse);
    }

    @Transactional
    @PostMapping
    public ResponseEntity criar(UriComponentsBuilder uriComponentsBuilder,
                                @RequestBody @Valid PropostaRequest propostaRequest) {
        boolean documentoJaCadastrado = propostaRepository.existsByDocumento(propostaRequest.getDocumento());

        if (documentoJaCadastrado) {
            return ResponseEntity.unprocessableEntity().build();
        }

        var proposta = propostaRequest.toModel();
        var novaProposta = propostaRepository.save(proposta);

        //TODO
        //      Estudar como refatorar
        //      Refatorar também para caso o link não responda (tirar setEstadoProposta do catch)
        try {
            Span activeSpan = tracer.activeSpan();
            activeSpan.setTag("user.email", "william@hotmail.com");
            activeSpan.log("Meu log");
            activeSpan.setBaggageItem("user.email", "william@hotmail.com");

            var validacaoRequest = new AnaliseFinanceiraRequest(
                    String.valueOf(novaProposta.getId()),
                    novaProposta.getDocumento(),
                    novaProposta.getNome());

            analiseFinanceiraClient.avaliarProposta(validacaoRequest);
            novaProposta.setEstadoProposta(PropostaEstado.ELEGIVEL);
        } catch (FeignException e) {
            novaProposta.setEstadoProposta(PropostaEstado.NAO_ELEGIVEL);
        }

        if (novaProposta.eElegivel()) {
            publisher.publishEvent(new PropostaCriadaEvent(novaProposta));
        }

        metricas.meuContador();

        URI enderecoRecurso = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());
        return ResponseEntity.created(enderecoRecurso).build();
    }
}
