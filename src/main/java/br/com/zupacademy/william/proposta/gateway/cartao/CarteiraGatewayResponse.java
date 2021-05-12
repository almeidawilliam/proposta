package br.com.zupacademy.william.proposta.gateway.cartao;

public class CarteiraGatewayResponse {

    private String id;
    private String resultado;


    public CarteiraGatewayResponse(String id, String resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
