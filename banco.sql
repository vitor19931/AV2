CREATE DATABASE IF NOT EXISTS laboratorio_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE laboratorio_db;

CREATE TABLE IF NOT EXISTS equipamento (
    patrimonio INT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    laboratorio VARCHAR(50) NOT NULL,
    situacao VARCHAR(30) NOT NULL,
    memoria_ram INT DEFAULT NULL,
    luminosidade INT DEFAULT NULL
);