package voll.med2.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import voll.med2.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank // Valida que o campo não é nulo, vazio ou só com espaços
        String nome,

        @NotBlank // Valida que o campo não é nulo, vazio ou só com espaços
        String email,

        @NotBlank // Valida que o campo não é nulo, vazio ou só com espaços
        String telefone,

        @NotBlank(message = "Erro. CRM não informado.") // Mensagem personalizada se o campo estiver vazio/nulo
        @Pattern(regexp = "\\d{4,6}") // Valida que o campo possui apenas dígitos e entre 4 e 6 caracteres
        String crm,

        @NotNull // Valida que o campo não pode ser nulo
        Especialidade especialidade,

        @NotNull // Valida que o campo não pode ser nulo
        @Valid // Faz a validação dos campos internos do objeto 'endereco'
        DadosEndereco endereco){
}
