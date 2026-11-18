SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS laboratorio_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE laboratorio_db;

CREATE TABLE IF NOT EXISTS equipamento (
    patrimonio   INT          PRIMARY KEY,
    tipo         VARCHAR(20)  NOT NULL,
    descricao    VARCHAR(100) NOT NULL,
    laboratorio  VARCHAR(50)  NOT NULL,
    situacao     VARCHAR(20)  NOT NULL,
    memoria_ram  INT NULL,
    luminosidade INT NULL,
    CONSTRAINT chk_patrimonio CHECK (patrimonio > 0),
    CONSTRAINT chk_tipo CHECK (tipo IN ('COMPUTADOR','PROJETOR')),
    CONSTRAINT chk_situacao CHECK (situacao IN ('Disponível','Em uso','Em manutenção')),
    CONSTRAINT chk_computador CHECK (tipo <> 'COMPUTADOR' OR (memoria_ram > 0 AND luminosidade IS NULL)),
    CONSTRAINT chk_projetor CHECK (tipo <> 'PROJETOR' OR (luminosidade > 0 AND memoria_ram IS NULL)),
    CONSTRAINT chk_descricao CHECK (TRIM(descricao) <> ''),
    CONSTRAINT chk_laboratorio CHECK (TRIM(laboratorio) <> '')
);
