CREATE TABLE IF NOT EXISTS lpoo2_banco.cliente (
  cpf VARCHAR(11) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  sobrenome VARCHAR(255) NOT NULL,
  rg VARCHAR(9) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  PRIMARY KEY (cpf),
  UNIQUE INDEX ukCpfCiente (cpf ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE lpoo2_banco.ContaCorrente (
    numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
    limite DOUBLE UNSIGNED NULL DEFAULT NULL,
    saldo DOUBLE NOT NULL,
    cpfCliente VARCHAR(11) NOT NULL,
    PRIMARY KEY (numero),
    UNIQUE INDEX ukCpfCliente (cpfCliente ASC) VISIBLE,
    CONSTRAINT fkClienteContaCorrente
    FOREIGN KEY (cpfCliente)
    REFERENCES lpoo2_banco.cliente (cpf))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE lpoo2_banco.ContaInvestimento (
    numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
    depositoMinimo DOUBLE UNSIGNED NULL DEFAULT NULL,
    montanteMinimo DOUBLE UNSIGNED NULL DEFAULT NULL,
    saldo DOUBLE NOT NULL,
    cpfCliente VARCHAR(11) NOT NULL,
    PRIMARY KEY (numero),
    UNIQUE INDEX ukCpfCliente (cpfCliente ASC) VISIBLE,
    CONSTRAINT fkContaInvestimento
    FOREIGN KEY (cpfCliente)
    REFERENCES lpoo2_banco.cliente (cpf))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
 
