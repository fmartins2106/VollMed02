package voll.med2.api.infra.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import voll.med2.api.domain.ValidacaoException;
import voll.med2.api.domain.usuario.Usuario;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // Constantes para padronizar origem e remetente dos e-mails
    @Value("${spring.mail.origem}")
    private String emailOrigem;

    @Value("${spring.mail.nomeRemetente}")
    private String nomeRemetente;

    @Value("${spring.app.urlSite}")
    private String urlSite;



    @Async // Executa o envio de e-mails em uma thread separada (não trava a aplicação)
    public void enviarEmail(String emailUsuario, String assunto, String conteudo){
        MimeMessage message = javaMailSender.createMimeMessage(); // Cria a mensagem MIME
        MimeMessageHelper helper = new MimeMessageHelper(message); // Ajuda a montar o e-mail
        try {
            // Define remetente, destinatário, assunto e conteúdo (HTML habilitado)
            helper.setFrom(emailOrigem, nomeRemetente);
            helper.setTo(emailUsuario);
            helper.setSubject(assunto);
            helper.setText(conteudo, true);
        }catch (MessagingException | UnsupportedEncodingException exception){
            // Lança exceção personalizada caso algo dê errado
            throw new ValidacaoException("Erro ao enviar o email.");
        }

        // Envia a mensagem
        javaMailSender.send(message);
    }

    // Método público para enviar e-mail de verificação de conta
    public void enviarEmailVerificacao(Usuario usuario){
        String assunto = "Aqui está seu link para verificar o email.";

        // Monta o conteúdo do e-mail a partir de um "template"
        String conteudo = gerarConteudoEmail(
                "Olá [[name]], <br>" +
                        "Por favor clieque no link abaixo para verificar seu conta <br>" +
                        "<h3> <a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>" +
                        "Obrigado <br>" +
                        "Forun Hub :).",
                usuario.getNomeCompleto(),
                urlSite + "/verificar-conta?codigo="+usuario.getToken()
        );

        // Chama o método privado de envio
        enviarEmail(usuario.getUsername(), assunto, conteudo);
    }

    // Substitui placeholders [[name]] e [[URL]] no template pelo conteúdo real
    private String gerarConteudoEmail(String template, String nomeCompleto, String url) {
        return template.replace("[[name]]", nomeCompleto)
                .replace("[[URL]]", url);
    }


}
