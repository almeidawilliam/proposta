package br.com.zupacademy.william.proposta.proposta.cartao.bloqueio;

import br.com.zupacademy.william.proposta.exception.EntidadeNaoEncontradaException;
import br.com.zupacademy.william.proposta.exception.RegraVioladaException;
import br.com.zupacademy.william.proposta.gateway.cartao.BloqueioCartaoGatewayReponse;
import br.com.zupacademy.william.proposta.gateway.cartao.BloqueioCartaoGatewayRequest;
import br.com.zupacademy.william.proposta.gateway.cartao.BloqueioCartaoResultado;
import br.com.zupacademy.william.proposta.gateway.cartao.CartaoClient;
import br.com.zupacademy.william.proposta.proposta.cartao.Cartao;
import br.com.zupacademy.william.proposta.proposta.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;

@RestController
@RequestMapping("/cartoes/{id}/bloqueios")
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CartaoClient cartaoClient;

    @Transactional
    @PostMapping
    public ResponseEntity criar(@PathVariable Long id,
                                @RequestHeader HashMap<String, String> headers) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe um cartão com id %d", id)));

        if (cartao.estaBloqueado()) {
            throw new RegraVioladaException(String.format("O cartão %d já está bloqueado", cartao.getId()));
        }

        try {
            BloqueioCartaoGatewayReponse gatewayResponse =
                    cartaoClient.bloquearCartao(
                            cartao.getNumero(),
                            new BloqueioCartaoGatewayRequest("sistema de propostas")
                    );

            if (gatewayResponse.getResultado().equals(BloqueioCartaoResultado.BLOQUEADO)) {
                //        String ip = request.getHeader("x-forwarded-for");
                String remoteAddr = request.getRemoteAddr();
                String userAgent = headers.get("user-agent");

                Bloqueio novoBloqueio = new Bloqueio(remoteAddr, userAgent);
                cartao.bloquear(novoBloqueio);

                return ResponseEntity.ok().build();
            }
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.badRequest().build();
    }
}
