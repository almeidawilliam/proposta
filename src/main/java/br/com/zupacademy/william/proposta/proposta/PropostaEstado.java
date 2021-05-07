package br.com.zupacademy.william.proposta.proposta;

public enum PropostaEstado {

    ELEGIVEL("Elegível"),
    NAO_ELEGIVEL("Não Elegível");

    private String nomeRepresentacao;

    PropostaEstado(String nomeRepresentacao) {
        this.nomeRepresentacao = nomeRepresentacao;
    }

    public String getNomeRepresentacao() {
        return nomeRepresentacao;
    }
}
