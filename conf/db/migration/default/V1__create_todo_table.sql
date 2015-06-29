
drop table if exists todo_type;
create table todo_type
(
    id int not null auto_increment,
    title varchar(255) not null,
    ins_datetime datetime not null,
    upd_datetime datetime not null,
    primary key (id)
);

drop table if exists todo;
create table todo
(
    id int not null auto_increment,
    todo_type_id int,
    content varchar(255) not null,
    ins_datetime datetime not null,
    upd_datetime datetime not null,
    primary key (id)
);

