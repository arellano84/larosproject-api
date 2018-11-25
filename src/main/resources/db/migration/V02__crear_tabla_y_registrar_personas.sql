CREATE TABLE persona(
	codigo BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
	documento VARCHAR(20) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	apellido1 VARCHAR(100) NOT NULL,
	apellido2 VARCHAR(100) NULL,
	activo BOOLEAN NOT NULL,
	tipo VARCHAR(20) NULL,
	calle VARCHAR(200) NULL,
	numero VARCHAR(10) NULL,
	complemento VARCHAR(100) NULL,
	cp VARCHAR(10) NULL,
	municipio VARCHAR(50) NULL,
	ciudad VARCHAR(50) NULL,
	pais VARCHAR(50) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO persona (documento,nombre,apellido1,apellido2,activo,pais) values ('03181607V','Luis','Arellano','', true,'México');
INSERT INTO persona (documento,nombre,apellido1,apellido2,activo,pais) values ('03181607V','Rayssa','Camargo','', true,'Brasil');
