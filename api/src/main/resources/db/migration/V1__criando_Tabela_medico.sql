CREATE TABLE dbMedicos(
    idMedico INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL CHECK( nome <> ''),
    email VARCHAR(50) NOT NULL CHECK(email <> ''),
    telefone VARCHAR(15) NOT NULL CHECK(telefone <> ''),
    crm VARCHAR(6) NOT NULL CHECK(crm <> ''),
    Especialidade VARCHAR(30) CHECK(especialidade <> ''),
    logradouro VARCHAR(30) NOT NULL CHECK(logradouro <> ''),
    endereco VARCHAR(100) NOT NULL CHECK(endereco <> ''),
    numero VARCHAR(10) CHECK(numero <> ''),
    complemento VARCHAR(50) CHECK(complemento <> ''),
    cep VARCHAR(8) NOT NULL CHECK(cep <> ''),
    bairro VARCHAR(50) NOT NULL CHECK(bairro <> ''),
    cidade VARCHAR(100) NOT NULL CHECK(cidade <> ''),
    uf VARCHAR(2) NOT NULL CHECK(uf <> ''),
    ativo boolean NOT NULL DEFAULT true
);