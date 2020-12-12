insert into genres (title) values('Пьеса');
insert into genres (title) values('Роман');
insert into genres (title) values('Автобиография');
insert into genres (title) values('Сказка');
insert into genres (title) values('Комедия');
insert into genres (title) values('Очерк');
commit;

insert into authors(firstname, lastname) values('Антон', 	'Чехов');
insert into authors(firstname, lastname) values('Евгений', 	'Шварц');
insert into authors(firstname, lastname) values('Максим', 	'Горький');
insert into authors(firstname, lastname) values('Шадерло', 	'де Лакло');
insert into authors(firstname, lastname) values('Анри', 	'де Мопасан');
insert into authors(firstname, lastname) values('Николай', 	'Левашов');
commit;

insert into books(title, author_id, genre_id) values('Вишневый сад',			1, 1);
insert into books(title, author_id, genre_id) values('Дядя Ваня',				1, 1);
insert into books(title, author_id, genre_id) values('Обыкновенное чудо',		2, 1);
insert into books(title, author_id, genre_id) values('Чудак',					3, 2);
insert into books(title, author_id, genre_id) values('На дне',					3, 2);
insert into books(title, author_id, genre_id) values('Опасные связи',			4, 2);
insert into books(title, author_id, genre_id) values('Милый друг',				5, 2);
insert into books(title, author_id, genre_id) values('Пышка',					5, 2);
insert into books(title, author_id, genre_id) values('Россия в кривых зеркалах',6, 6);
commit;