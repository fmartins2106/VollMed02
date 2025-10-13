ALTER TABLE usuarios_perfis DROP CONSTRAINT FK_UP_usuario_id;
ALTER TABLE usuarios_perfis
ADD CONSTRAINT FK_UP_usuario_id
FOREIGN KEY (usuario_id) REFERENCES usuarios(id);

