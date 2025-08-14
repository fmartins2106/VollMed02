package voll.med2.api.domain.endereco;

public record DadosEndereco(
        String logradouro,
        String endereco,
        String numero,
        String cep,
        String bairro,
        String cidade,
        String uf,
        String complemento) {
}
