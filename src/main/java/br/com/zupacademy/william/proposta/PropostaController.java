package br.com.zupacademy.william.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity criar(UriComponentsBuilder uriComponentsBuilder,
                                @RequestBody @Valid PropostaRequest propostaRequest) {
        Proposta proposta = propostaRequest.toModel();
        Proposta novaProposta = propostaRepository.save(proposta);
        URI enderecoRecurso = uriComponentsBuilder.path("/propostas/{id}").build(novaProposta.getId());

        return ResponseEntity.created(enderecoRecurso).build();
    }
}
