package br.com.zupacademy.william.proposta.gateway.cartao;

import br.com.zupacademy.william.proposta.proposta.cartao.vencimento.Vencimento;

import java.time.LocalDateTime;

public class VencimentoGatewayResponse {

    private String id;
    private int dia;
    private LocalDateTime dataCriacao;

    public VencimentoGatewayResponse(String id, int dia, LocalDateTime dataCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataCriacao = dataCriacao;
    }

    public Vencimento toModel() {
        return new Vencimento(id, dia, LocalDateTime.now());
    }

}
