CREATE TABLE contacto (
  	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	codigo_persona BIGINT(20) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
  FOREIGN KEY (codigo_persona) REFERENCES persona(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into contacto (codigo, codigo_persona, nombre, email, telefono) values (1, 1, 'Luis AR2', 'luis2@laros.com', '00 0000-0000');
