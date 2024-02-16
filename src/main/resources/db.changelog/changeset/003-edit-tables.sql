--liquibase formatted sql

--changeset dholubeu:003_edit_tables

alter table cards drop column if exists driver_id;

alter table drivers drop column if exists password;

alter table cars drop constraint IF exists cars_mark_key;

alter table cars add constraint cars_number_key unique (number);