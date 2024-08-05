CREATE DATABASE nttdata_db;

-- Usar la base de datos recién creada
USE nttdata_db;

CREATE SCHEMA IF NOT EXISTS client_person;

CREATE SCHEMA IF NOT EXISTS account_movements;

CREATE TABLE client_person.persona (
	dtype varchar(31) NOT NULL,
	persona_id uuid NOT NULL,
	direccion varchar(255) NULL,
	edad int4 NULL,
	genero varchar(255) NULL,
	identificacion varchar(255) NULL,
	nombre varchar(255) NULL,
	telefono varchar(255) NULL,
	cliente_id varchar(255) NOT NULL,
	contrasenia varchar(255) NULL,
	estado bool NULL,
	CONSTRAINT persona_pkey PRIMARY KEY (persona_id),
	CONSTRAINT uksk2ttlpp6eid09hulymqx6cc5 UNIQUE (cliente_id)
);

CREATE TABLE account_movements.cuenta (
	cuenta_id uuid NOT NULL,
	numero_cuenta int4 NOT NULL,
	saldo_inicial float8 NULL,
	persona_id uuid NULL,
	estado bool NULL,
	tipo_cuenta varchar(255) NULL,
	CONSTRAINT cuenta_pkey PRIMARY KEY (cuenta_id),
	CONSTRAINT cuenta_tipo_cuenta_check CHECK (((tipo_cuenta)::text = ANY ((ARRAY['AHO'::character varying, 'CORR'::character varying])::text[]))),
	CONSTRAINT ukpj7ncg765kt4klndu25bwbwe4 UNIQUE (numero_cuenta),
	CONSTRAINT fk_cuenta_persona FOREIGN KEY (persona_id) REFERENCES client_person.persona(persona_id)
);

CREATE TABLE account_movements.movimientos (
	movimiento_id uuid NOT NULL,
	cuenta_id uuid NULL,
	saldo float8 NULL,
	fecha_movimiento date NULL,
	tipo_movimiento varchar(255) NULL,
	valor float8 NULL,
	CONSTRAINT movimientos_pkey PRIMARY KEY (movimiento_id),
	CONSTRAINT movimientos_tipo_movimiento_check CHECK (((tipo_movimiento)::text = ANY ((ARRAY['DEP'::character varying, 'RET'::character varying])::text[]))),
	CONSTRAINT fk_movement_account FOREIGN KEY (cuenta_id) REFERENCES account_movements.cuenta(cuenta_id)
);