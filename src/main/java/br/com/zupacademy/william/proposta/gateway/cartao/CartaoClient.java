package br.com.zupacademy.william.proposta.gateway.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cartoes", url = "http://localhost:8888/api/cartoes")
public interface CartaoClient {

    @PostMapping
    GeracaoCartaoGatewayResponse cadastrarCartao(@RequestBody GeracaoCartaoRequestGateway geracaoCartaoRequestGateway);
}
