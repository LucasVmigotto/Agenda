drop database if exists agenda;
create database if not exists agenda;
use agenda;

drop table if exists contato;
create table if not exists contato(
	id int primary key auto_increment,
    nome varchar(150) not null,
    numeroTelefone varchar(16),
    favorito boolean null default 0
);