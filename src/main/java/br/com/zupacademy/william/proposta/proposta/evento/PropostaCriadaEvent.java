package br.com.zupacademy.william.proposta.proposta.evento;

import br.com.zupacademy.william.proposta.proposta.Proposta;

public class PropostaCriadaEvent {

    private Proposta proposta;

    public PropostaCriadaEvent(Proposta proposta) {
        this.proposta = proposta;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
