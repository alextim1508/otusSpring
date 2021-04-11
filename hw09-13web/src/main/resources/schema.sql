drop table if exists authors;
create table authors(	id bigint not null auto_increment,
						firstname varchar(32) not null,
						lastname varchar(32) not null,
                        primary key(id));

drop table if exists genres;
create table genres(id bigint not null auto_increment,
					title varchar(32) not null,
                    primary key(id));

drop table if exists books;
create table books(	id bigint not null auto_increment,
					title varchar(32) not null,
                    author_id bigint not null,
                    genre_id bigint not null,
                    primary key(id),
                    constraint fk_books_authors foreign key(author_id) references authors(id),
                    constraint fk_books_genres foreign key(genre_id) references genres(id));


create unique index unicNameAuthor on authors(firstname, lastname);
create unique index unicTitleBook on books(title);
create unique index unicTitleGenre on genres(title);

DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Users (  id BIGINT NOT NULL AUTO_INCREMENT,
                                    username VARCHAR(45) NOT NULL,
                                    name VARCHAR(45) NOT NULL,
                                    surname VARCHAR(45) NOT NULL,
                                    email VARCHAR(45) NOT NULL,
                                    encoded_password VARCHAR(45) NOT NULL,
                                    phone VARCHAR(45) NULL,
                                    sent_sms VARCHAR(45) NULL,
                                    account_non_expired BIT NULL,
                                    account_non_locked BIT NULL,
                                    credentials_non_expired BIT NULL,
                                    enabled BIT NULL,
                                    PRIMARY KEY (id),
                                    UNIQUE INDEX email_user_unique_index (email ASC),
                                    UNIQUE INDEX username_user_unique_index (username ASC));

DROP TABLE IF EXISTS Roles;
CREATE TABLE IF NOT EXISTS Roles(user_id BIGINT NOT NULL,
                                 role VARCHAR(45) NULL,
                                 UNIQUE INDEX role_roles_unique_index (role ASC),
                                 CONSTRAINT fk_Roles_User FOREIGN KEY (user_id) REFERENCES Users (id));

