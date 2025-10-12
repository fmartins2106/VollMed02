 create table perfis (
    id SERIAL PRIMARY KEY,
    perfilNome VARCHAR(50) NOT NULL UNIQUE
 );

 INSERT INTO perfis(perfilNome)
 values
 ('PACIENTE'),
 ('MEDICO'),
 ('ADMINISTRADOR'),
 ('COORDENADOR'),
 ('COLABORADOR');


