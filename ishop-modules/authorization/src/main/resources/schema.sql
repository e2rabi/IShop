drop table if exists authority CASCADE;
drop table if exists login_failure CASCADE;
drop table if exists role CASCADE;
drop table if exists role_authority CASCADE;
drop table if exists user_role CASCADE;
drop table if exists users CASCADE ;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

create table authority (id varchar(36) not null, created_date timestamp, last_modified_date timestamp, version bigint, permission varchar(255), primary key (id));
create table login_failure (id integer not null, created_date timestamp, last_modified_date timestamp, source_ip varchar(255), user_name varchar(255), user_id varchar(36), primary key (id));
create table role (id varchar(36) not null, created_date timestamp, last_modified_date timestamp, version bigint, name varchar(255), primary key (id));
create table role_authority (role_id varchar(36) not null, authority_id varchar(36) not null, primary key (role_id, authority_id));
create table user_role (user_id varchar(36) not null, role_id varchar(36) not null, primary key (user_id, role_id));
create table users (id varchar(36) not null, created_date timestamp, last_modified_date timestamp, version bigint, account_non_expired boolean not null, account_non_locked boolean not null, address varchar(255), credentials_non_expired boolean not null, email varchar(255), enabled boolean not null, first_name varchar(255), google2fa_secret varchar(255), last_name varchar(255), password varchar(255), phone varchar(255), use_google2fa boolean, username varchar(255), primary key (id));
alter table login_failure add constraint FKd3cxxh5gmyv9autnr3jl6ad foreign key (user_id) references users;
alter table role_authority add constraint FKqbri833f7xop13bvdje3xxtnw foreign key (authority_id) references authority;
alter table role_authority add constraint FK2052966dco7y9f97s1a824bj1 foreign key (role_id) references role;
alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role;
alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users