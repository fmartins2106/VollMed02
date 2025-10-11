package voll.med2.api.domain.usuario;

public record DadosListagemUsuarios(
        Long id,
        String nomeCompleto,
        String login,
        boolean ativo,
        boolean verificado) {

    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getId(), usuario.getNomeCompleto(), usuario.getLogin(),
                usuario.getAtivo(), usuario.getVerificado());
    }
}
