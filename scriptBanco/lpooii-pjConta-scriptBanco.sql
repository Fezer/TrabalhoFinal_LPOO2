/* LPOOII - PJ Banco - Diagrama BD - Logico Relacional: */
CREATE TABLE Cliente (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100),
    sobrenome VARCHAR(100),
    rg VARCHAR(20),
    endereco VARCHAR(250)
);

CREATE TABLE ContaCorrente (
    numero INTEGER PRIMARY KEY,
    limite DOUBLE,
    saldo DOUBLE,
    cpfCliente VARCHAR(11)
);

CREATE TABLE ContaInvestimento (
    numero INTEGER PRIMARY KEY,
    depositoMinimo DOUBLE,
    montanteMinimo DOUBLE,
    saldo DOUBLE,
    cpfCliente VARCHAR(11)
);

 
ALTER TABLE ContaCorrente ADD CONSTRAINT fkContaCorrenteCpfCliente
    FOREIGN KEY (cpfCliente)
    REFERENCES Cliente (cpf)
    ON DELETE CASCADE;
 
ALTER TABLE ContaInvestimento ADD CONSTRAINT fkContaInvestimentoCpfCliente
    FOREIGN KEY (cpfCliente)
    REFERENCES Cliente (cpf)
    ON DELETE CASCADE;
