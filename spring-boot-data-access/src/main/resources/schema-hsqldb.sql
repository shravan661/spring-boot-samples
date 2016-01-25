create table pet_types (
        id bigint not null,
        name varchar(255),
        primary key (id)
    );
/;

create table pets (
        id bigint not null,
        birth_date date,
        name varchar(255),
        type_id bigint,
        primary key (id)
    );
 /;


alter table pets 
        add constraint FK_5avf7qbbcwdrfspmfktmmv197 
        foreign key (type_id) 
        references pet_types;
/;

create sequence PET_SEQ start with 1;
/;

create sequence PET_TYPE_SEQ start with 1;
/;

DROP procedure IF EXISTS plus1inout;
/;

CREATE PROCEDURE plus1inout (IN arg int, OUT res int)
BEGIN ATOMIC
	SET res = arg + 1;
END;
/;

CREATE PROCEDURE plus2inout (IN arg int, OUT res int)
BEGIN ATOMIC
	SET res = arg + 2;
END;
/;