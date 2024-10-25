CREATE SCHEMA IF NOT EXISTS bank;

CREATE TABLE bank.accounts (
	account_id varchar(50) NOT NULL,
	user_id varchar(50) NULL,
	account_number int4 NOT NULL,
	account_type varchar(10) NOT NULL,
	balance int4 DEFAULT 0.00 NOT NULL,
	transfer_limit_count int4 NULL,
	currency varchar(3) NOT NULL,
	is_deleted bool NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT accounts_pkey PRIMARY KEY (account_id)
);

CREATE TABLE bank.transfers (
	transfer_id varchar(50) NOT NULL,
	sender_account_id varchar(100) NOT NULL,
	receiver_account_id varchar(100) NOT NULL,
	amount int4 DEFAULT 0.00 NOT NULL,
	currency varchar(3) NOT NULL,
	transfer_date date DEFAULT CURRENT_TIMESTAMP NULL,
	status varchar(10) NOT NULL,
	is_deleted bool NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT transfers_pkey PRIMARY KEY (transfer_id)
);

CREATE TABLE bank.users (
	user_id varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(100) NOT NULL,
	phone_number varchar(15) NOT NULL,
	address text NULL,
	date_of_birth date NULL,
	gender varchar(15) NOT NULL,
	is_deleted bool NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	updated_at timestamp DEFAULT CURRENT_TIMESTAMP NULL,
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);