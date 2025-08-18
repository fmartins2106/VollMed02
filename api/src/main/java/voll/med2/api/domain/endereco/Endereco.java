package voll.med2.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Endereco {
    private String logradouro;
    private String endereco;
    private String numero;
    private String complemento;
    private String cep;
    private String bairro;
    private String cidade;
    private String uf;

    public Endereco(DadosEndereco dadosEndereco) {
        this.logradouro = dadosEndereco.logradouro();
        this.endereco = dadosEndereco.endereco();
        this.numero = dadosEndereco.numero();
        this.complemento = dadosEndereco.complemento();
        this.cep = dadosEndereco.cep();
        this.bairro = dadosEndereco.bairro();
        this.cidade = dadosEndereco.cidade();
        this.uf = dadosEndereco.uf();
    }

    public void atualizarInformacoes(DadosEndereco endereco1) {
        Optional.ofNullable(endereco1.logradouro()).ifPresent(s -> this.logradouro = s);
        Optional.ofNullable(endereco1.endereco()).ifPresent(s -> this.endereco = s);
        Optional.ofNullable(endereco1.numero()).ifPresent(s -> this.numero = s);
        Optional.ofNullable(endereco1.complemento()).ifPresent(s -> this.complemento = s);
        Optional.ofNullable(endereco1.cep()).ifPresent(s -> this.cep = s);
        Optional.ofNullable(endereco1.bairro()).ifPresent(s -> this.bairro = s);
        Optional.ofNullable(endereco1.cidade()).ifPresent(s -> this.cidade = s);
        Optional.ofNullable(endereco1.uf()).ifPresent(s -> this.endereco = s);
    }
}
