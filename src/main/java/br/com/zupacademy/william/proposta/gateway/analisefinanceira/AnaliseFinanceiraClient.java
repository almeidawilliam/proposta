package br.com.zupacademy.william.proposta.gateway.analisefinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "financeiro", url = "http://localhost:9999/api")
public interface AnaliseFinanceiraClient {

    @PostMapping(value = "/solicitacao")
    AnaliseFinanceiraResponse avaliarProposta(@RequestBody AnaliseFinanceiraRequest analiseFinanceiraRequest);
}
