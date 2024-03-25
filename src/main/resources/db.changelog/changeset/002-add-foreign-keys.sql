--liquibase formatted sql

--changeset dholubeuu:002-add-foreign-keys

alter table drivers add constraint fk_car_id foreign key (car_id) references cars(id);

alter table drivers add constraint fk_card_id foreign key (card_id) references cards(id);