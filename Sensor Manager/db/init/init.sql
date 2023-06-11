drop table if exists Containers;
drop table if exists Parameters;
drop table if exists Containers_Parameters;

create table if not exists Containers 
(
    id bigserial primary key,
    name varchar(255) not null
);

create table if not exists Parameters 
(
    id bigserial primary key,
    name varchar(255) not null
);

create table if not exists Containers_Parameters 
(
    container_id bigint references Containers (id) on delete cascade,
    parameter_id bigint references Parameters (id) on delete cascade,
    reading_time timestamp not null,
    parameter_value numeric not null,
    primary key (container_id, parameter_id, reading_time)
);