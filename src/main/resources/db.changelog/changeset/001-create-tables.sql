--liquibase formatted sql

--changeset dholubeuu:001_create_tables

set schema 'drivers_schema';

create table drivers
(
    id bigserial,
    email varchar(100) not null unique,
    password varchar(100) not null,
    name varchar(60) not null,
    surname varchar(60) not null,
    date_of_birth date not null,
    phone_number varchar(12) not null,
    is_activated boolean not null default false,
    car_id bigint,
    card_id bigint,
    driving_experience decimal(10, 1) not null,
    status varchar(30),
    rating real default 0.0,
    balance decimal(5,2) not null default 0.0,
    primary key (id)
);

create table cars
(
    id bigserial,
    mark varchar(30) not null unique,
    model varchar(30) not null,
    number varchar(15) not null,
    current_address varchar(200) not null,
    primary key (id)
);

create table cards
(
    id bigserial,
    driver_id bigint not null,
    number varchar(16) not null,
    validity date not null,
    cvv varchar(3) not null,
    balance decimal(5,2) not null default 0.0,
    primary key (id)
);