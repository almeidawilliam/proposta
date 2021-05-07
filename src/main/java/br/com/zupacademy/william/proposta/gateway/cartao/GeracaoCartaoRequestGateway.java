package br.com.zupacademy.william.proposta.gateway.cartao;

public class GeracaoCartaoRequestGateway {

    private String idProposta;
    private String documento;
    private String nome;

    public GeracaoCartaoRequestGateway(String idProposta, String documento, String nome) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }
}
