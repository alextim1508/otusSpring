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
                                 CONSTRAINT fk_Roles_User FOREIGN KEY (user_id) REFERENCES Users (id));

DROP TABLE IF EXISTS  acl_entry;
DROP TABLE IF EXISTS  acl_object_identity;
DROP TABLE IF EXISTS  acl_sid;
DROP TABLE IF EXISTS  acl_class;


CREATE TABLE acl_sid( id BIGINT NOT NULL PRIMARY KEY auto_increment,
                      principal TINYINT(1) NOT NULL,
                      sid VARCHAR(100) NOT NULL,
                      CONSTRAINT unique_uk_1 UNIQUE(sid, principal));

CREATE TABLE acl_class( id BIGINT NOT NULL PRIMARY KEY auto_increment,
                        class VARCHAR(100) NOT NULL,
                        CONSTRAINT unique_uk_2 UNIQUE(class));

CREATE TABLE acl_object_identity( id BIGINT PRIMARY KEY auto_increment,
                                  object_id_class BIGINT NOT NULL,
                                  object_id_identity BIGINT NOT NULL,
                                  parent_object BIGINT,
                                  owner_sid BIGINT,
	                                entries_inheriting TINYINT(1) NOT NULL,
                                  CONSTRAINT unique_uk_3 UNIQUE(object_id_class, object_id_identity),
                                  CONSTRAINT foreign_fk_1 FOREIGN KEY(parent_object) REFERENCES acl_object_identity(id),
                                  CONSTRAINT foreign_fk_2 FOREIGN KEY(object_id_class) REFERENCES acl_class(id),
                                  CONSTRAINT foreign_fk_3 FOREIGN KEY(owner_sid) REFERENCES acl_sid(id));

CREATE TABLE acl_entry( id BIGINT PRIMARY KEY auto_increment,
	                      acl_object_identity BIGINT NOT NULL,
	                      ace_order INT NOT NULL,
	                      sid BIGINT NOT NULL,
	                      mask INTEGER NOT NULL,
	                      granting TINYINT(1) NOT NULL,
                        audit_success TINYINT(1) NOT NULL,
                        audit_failure TINYINT(1) NOT NULL,
                        CONSTRAINT unique_uk_4 UNIQUE(acl_object_identity, ace_order),
                        CONSTRAINT foreign_fk_4 FOREIGN KEY(acl_object_identity) REFERENCES acl_object_identity(id),
                        CONSTRAINT foreign_fk_5 FOREIGN KEY(sid) REFERENCES acl_sid(id));

