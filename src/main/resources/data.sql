insert into "user" (user_id, password, nickname, activated) values ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
insert into "user" (user_id, password, nickname, activated) values ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);

INSERT INTO role (id, created, name) VALUES (1, now(), 'ROLE_NORMAL_USER');
INSERT INTO role (id, created, name) VALUES (2, now(), 'ROLE_ADMIN');
INSERT INTO role (id, created, name) VALUES (3, now(), 'ROLE_SUPER_ADMIN');