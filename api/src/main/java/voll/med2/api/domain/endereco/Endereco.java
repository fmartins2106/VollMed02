package voll.med2.api.domain.endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    private String logradouro;
    private String endereco;
    private String numero;
    private String cep;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

}
