CREATE TABLE estado (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estado (codigo, nombre) VALUES(1, 'Madrid');
INSERT INTO estado (codigo, nombre) VALUES(2, 'Cataluña');



CREATE TABLE ciudad (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50) NOT NULL,
  	codigo_estado BIGINT(20) NOT NULL,
  FOREIGN KEY (codigo_estado) REFERENCES estado(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO ciudad (codigo, nombre, codigo_estado) VALUES (1, 'Madrid', 1);
INSERT INTO ciudad (codigo, nombre, codigo_estado) VALUES (2, 'Getafe', 1);
INSERT INTO ciudad (codigo, nombre, codigo_estado) VALUES (3, 'Pinto', 1);
INSERT INTO ciudad (codigo, nombre, codigo_estado) VALUES (8, 'Barcelona', 2);
INSERT INTO ciudad (codigo, nombre, codigo_estado) VALUES (9, 'Tarragona', 2);


ALTER TABLE persona DROP COLUMN ciudad;
ALTER TABLE persona DROP COLUMN municipio;
ALTER TABLE persona ADD COLUMN codigo_ciudad BIGINT(20);
ALTER TABLE persona ADD CONSTRAINT fk_persona_ciudad FOREIGN KEY (codigo_ciudad) REFERENCES ciudad(codigo);

UPDATE persona SET codigo_ciudad = 2;