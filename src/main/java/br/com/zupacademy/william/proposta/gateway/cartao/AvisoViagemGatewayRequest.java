package br.com.zupacademy.william.proposta.gateway.cartao;

import java.time.LocalDate;

public class AvisoViagemGatewayRequest {

    private String destino;
    private LocalDate validoAte;

    public AvisoViagemGatewayRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
