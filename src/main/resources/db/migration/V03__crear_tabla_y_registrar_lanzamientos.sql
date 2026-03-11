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

INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Nómina', '2020-04-01', '2020-04-01', 1000.00, 'Nómina mensual', 'INGRESO', 1, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Vuelo', '2020-04-01', '2020-04-06', 500.00, 'Vuelo ida-vuelta', 'GASTO', 2, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Abono', '2020-04-01', '2020-04-06', 400.00, 'Abono mensual', 'GASTO', 2, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Alquiler', '2020-04-01', '2020-04-02', 450.00, null, 'GASTO', 2, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Gastos', '2020-04-01', null, 0.00, null, 'GASTO', 2, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Comida', '2020-04-01', '2020-04-18', 200.00, 'Restaurante', 'GASTO', 2, 1);
INSERT INTO lanzamiento (descripcion, fecha_vencimiento, fecha_pago, valor, observacion, tipo, codigo_categoria, codigo_persona) values ('Intereses', '2020-04-05', '2020-04-15', 10.00, 'Intereses de ahorros', 'INGRESO', 1, 1);

