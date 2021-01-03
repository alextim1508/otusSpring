drop table if exists persons;
create table persons(	id bigint not null auto_increment,
						name varchar(32) not null,
						primary key(id));
