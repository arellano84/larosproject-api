CREATE TABLE categoria(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (nombre) values ('Ocio');
INSERT INTO categoria (nombre) values ('Alimentación');
INSERT INTO categoria (nombre) values ('Supermercado');
INSERT INTO categoria (nombre) values ('Farmacia');
INSERT INTO categoria (nombre) values ('Otros');