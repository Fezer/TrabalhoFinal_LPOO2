-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema lpoo2_banco
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema lpoo2_banco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lpoo2_banco` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `lpoo2_banco` ;

-- -----------------------------------------------------
-- Table `lpoo2_banco`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lpoo2_banco`.`cliente` (
  `cpf` VARCHAR(11) NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `sobrenome` VARCHAR(255) NOT NULL,
  `rg` VARCHAR(9) NOT NULL,
  `endereco` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`cpf`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lpoo2_banco`.`contas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lpoo2_banco`.`contas` (
  `cpf_cliente` VARCHAR(11) NOT NULL,
  `tipo` ENUM('corrente', 'investimento') NOT NULL,
  `saldo` DOUBLE NOT NULL,
  PRIMARY KEY (`cpf_cliente`),
  UNIQUE INDEX `id_UNIQUE` (`cpf_cliente` ASC) VISIBLE,
  CONSTRAINT `fk_cliente_conta`
    FOREIGN KEY (`cpf_cliente`)
    REFERENCES `lpoo2_banco`.`cliente` (`cpf`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lpoo2_banco`.`contas_correntes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lpoo2_banco`.`contas_correntes` (
  `num_conta` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cpf_cliente` VARCHAR(11) NOT NULL,
  `deposito_inicial` DOUBLE UNSIGNED NULL DEFAULT NULL,
  `limite` DOUBLE UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`num_conta`),
  UNIQUE INDEX `cpf_cliente_UNIQUE` (`cpf_cliente` ASC) VISIBLE,
  UNIQUE INDEX `num_conta_UNIQUE` (`num_conta` ASC) VISIBLE,
  CONSTRAINT `fk_conta_corrente`
    FOREIGN KEY (`cpf_cliente`)
    REFERENCES `lpoo2_banco`.`contas` (`cpf_cliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `lpoo2_banco`.`contas_poupança`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `lpoo2_banco`.`contas_poupança` (
  `cpf_cliente` VARCHAR(11) NOT NULL,
  `mont_min` DOUBLE UNSIGNED NULL DEFAULT NULL,
  `dep_min` DOUBLE UNSIGNED NULL DEFAULT NULL,
  `dep_inicial` DOUBLE UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`cpf_cliente`),
  UNIQUE INDEX `cpf_cliente_UNIQUE` (`cpf_cliente` ASC) VISIBLE,
  CONSTRAINT `fk_conta_poupança`
    FOREIGN KEY (`cpf_cliente`)
    REFERENCES `lpoo2_banco`.`contas` (`cpf_cliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
