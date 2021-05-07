package br.com.zupacademy.william.proposta.gateway.cartao;

import br.com.zupacademy.william.proposta.proposta.cartao.bloqueio.Bloqueio;

public class BloqueioGatewayResponse {

    private Long id;
    private Long idCartao;

    public Bloqueio toModel() {
        return new Bloqueio();
    }
}
