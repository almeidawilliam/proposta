package br.com.zupacademy.william.proposta.biometria;

import br.com.zupacademy.william.proposta.proposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String fingerPrint;

    public BiometriaRequest(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(fingerPrint, cartao);
    }
}
