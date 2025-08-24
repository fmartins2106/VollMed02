package voll.med2.api.domain.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
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

    public void alterarDadosEndereco(DadosEndereco dadosEndereco){
        Optional.ofNullable(dadosEndereco.logradouro()).ifPresent(s -> this.logradouro = s);
        Optional.ofNullable(dadosEndereco.endereco()).ifPresent(s -> this.endereco = s);
        Optional.ofNullable(dadosEndereco.numero()).ifPresent(s -> this.numero = s);
        Optional.ofNullable(dadosEndereco.complemento()).ifPresent(s -> this.complemento = s);
        Optional.ofNullable(dadosEndereco.cep()).ifPresent(s -> this.cep = s);
        Optional.ofNullable(dadosEndereco.bairro()).ifPresent(s -> this.bairro = s);
        Optional.ofNullable(dadosEndereco.cidade()).ifPresent(s -> this.cidade = s);
        Optional.ofNullable(dadosEndereco.uf()).ifPresent(s -> this.uf = s);
    }
}
