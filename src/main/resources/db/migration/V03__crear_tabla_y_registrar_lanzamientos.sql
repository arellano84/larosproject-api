CREATE TABLE lanzamiento (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	descripcion VARCHAR(50) NOT NULL,
	fecha_vencimiento DATE NOT NULL,
	fecha_pago DATE,
	valor DECIMAL(10,2) NOT NULL,
	observacion VARCHAR(100),
	tipo VARCHAR(20) NOT NULL,
	codigo_categoria BIGINT(20) NOT NULL,
	codigo_persona BIGINT(20) NOT NULL,
	FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
	FOREIGN KEY (codigo_persona) REFERENCES persona(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'RECETA', 1, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'GASTO', 2, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Top Club', '2017-06-10', null, 120, null, 'RECETA', 3, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'RECETA', 3, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('DMAE', '2017-06-10', null, 200.30, null, 'GASTO', 3, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'RECETA', 2, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Bahamas', '2017-06-10', null, 500, null, 'RECETA', 1, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'GASTO', 4, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'GASTO', 3, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'RECETA', 3, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Café', '2017-06-10', null, 8.32, null, 'GASTO', 1, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'GASTO', 1, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'GASTO', 4, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'GASTO', 4, 2);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Lanche', '2017-06-10', null, 10.20, null, 'GASTO', 4, 1);
