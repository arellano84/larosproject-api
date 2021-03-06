CREATE TABLE usuario (
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permiso (
	codigo BIGINT(20) PRIMARY KEY,
	descipcion VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permiso (
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_permiso BIGINT(20) NOT NULL,
	PRIMARY KEY (codigo_usuario, codigo_permiso),
	FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_permiso) REFERENCES permiso(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (codigo, nome, email, senha) values (1, 'Administrador', 'admin@laros.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha) values (2, 'Luis Aros', 'luis@laros.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permiso (codigo, descipcion) values (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permiso (codigo, descipcion) values (2, 'ROLE_PESQUISAR_CATEGORIA');

INSERT INTO permiso (codigo, descipcion) values (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permiso (codigo, descipcion) values (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permiso (codigo, descipcion) values (5, 'ROLE_PESQUISAR_PESSOA');

INSERT INTO permiso (codigo, descipcion) values (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permiso (codigo, descipcion) values (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permiso (codigo, descipcion) values (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 1);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 2);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 3);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 4);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 5);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 6);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 7);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (1, 8);

-- luis
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (2, 2);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (2, 5);
INSERT INTO usuario_permiso (codigo_usuario, codigo_permiso) values (2, 8);
