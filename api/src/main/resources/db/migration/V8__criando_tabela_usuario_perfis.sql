 create table usuarios_perfils(
    usuario_id INTEGER NOT NULL,
    perfil_id INTEGER NOT NULL,
    CONSTRAINT FK_UP_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios(idusuario),
    CONSTRAINT FK_UP_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfis(id)
 );

