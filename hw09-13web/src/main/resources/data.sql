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
insert into authors(firstname, lastname) values('Александр', 'Пушкин');
insert into authors(firstname, lastname) values('Виктор', 'Пелевин');
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
insert into books(title, author_id, genre_id) values('Empire V',                8, 2);
insert into books(title, author_id, genre_id) values('Священная книга оборотня',8, 2);
insert into books(title, author_id, genre_id) values('Generation П',            8, 2);
commit;

insert into Users(username, name, surname, phone, email, encoded_password,account_non_expired, account_non_locked, credentials_non_expired, enabled)
            values('Admin', 'admin', 'admin', '+79602574201', 'admin@yandex.ru', 'admin', 1, 1, 1, 1);
commit;

insert into Roles(user_id, role ) values(1, 'ADMIN');
commit;

insert into Users(username, name, surname, phone, email, encoded_password,account_non_expired, account_non_locked, credentials_non_expired, enabled)
            values('User1', 'user1', 'user1', '+79602574202', 'user1@yandex.ru', 'user1', 1, 1, 1, 1);
commit;

insert into Roles(user_id, role ) values(2, 'GUEST');
commit;

insert into Users(username, name, surname, phone, email, encoded_password,account_non_expired, account_non_locked, credentials_non_expired, enabled)
            values('User2', 'user2', 'user2', '+79602574203', 'user2@yandex.ru', 'user2', 1, 1, 1, 1);
commit;

insert into Roles(user_id, role ) values(3, 'GUEST');
commit;