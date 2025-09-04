CREATE TABLE consultas(
    idconsulta INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_medico INTEGER NOT NULL,
    id_paciente INTEGER NOT NULL,
    dataHoraConsulta TIMESTAMP NOT NULL,
    CONSTRAINT FK_id_medico_consulta FOREIGN KEY (id_medico) references dbmedicos(idmedico),
    CONSTRAINT FK_id_paciente_consulta FOREIGN KEY (id_paciente) references dbpacientes(idpaciente)
);