package br.com.zupacademy.william.proposta.proposta.cartao.aviso;

import br.com.zupacademy.william.proposta.exception.EntidadeNaoEncontradaException;
import br.com.zupacademy.william.proposta.proposta.cartao.Cartao;
import br.com.zupacademy.william.proposta.proposta.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/cartoes/{id}/avisos-viagem")
public class AvisoViagemController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private HttpServletRequest request;

    @Transactional
    @PostMapping
    public ResponseEntity criar(@PathVariable Long id,
                                @RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                @RequestHeader HashMap<String, String> headers) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe um cartão com id %d", id)));

        String ipClienteDaRequisicao = request.getRemoteAddr();
        String userAgent = headers.get("user-agent");

        AvisoViagem novoAvisoDeViagem = avisoViagemRequest.toModel(ipClienteDaRequisicao, userAgent);
        cartao.associarAvisoDeViagem(novoAvisoDeViagem);

        return ResponseEntity.ok().build();
    }
}
