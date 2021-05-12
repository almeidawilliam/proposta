package br.com.zupacademy.william.proposta.proposta.cartao.carteira;

import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotNull
    private ProvedorCarteira provedorCarteira;

    public CarteiraRequest() {
    }

    public ProvedorCarteira getProvedorCarteira() {
        return provedorCarteira;
    }
}
