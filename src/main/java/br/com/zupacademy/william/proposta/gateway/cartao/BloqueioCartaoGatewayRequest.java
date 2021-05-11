package br.com.zupacademy.william.proposta.gateway.cartao;

public class BloqueioCartaoGatewayRequest {

    private String sistemaResponsavel;

    public BloqueioCartaoGatewayRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
