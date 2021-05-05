package br.com.zupacademy.william.proposta.proposta.analisefinanceira;

public class AnaliseFinanceiraRequest {

    /**
     * descomentar jsonproperty caso spring.jackson.property-naming-strategy=SNAKE_CASE
     */
//    @JsonProperty("idProposta")
    private String idProposta;
    private String documento;
    private String nome;

    public AnaliseFinanceiraRequest(String idProposta, String documento, String nome) {
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
