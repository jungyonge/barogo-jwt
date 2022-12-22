


INSERT INTO "user" (username, password, nickname, activated, user_type, created, user_status) VALUES ('admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1,'ADMIN', now(),'JOINED');
INSERT INTO "user" (username, password, nickname, activated, user_type, created, user_status) VALUES ('user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1, 'COURIER', now(),'JOINED');


INSERT INTO role (id, created, name) VALUES (1, now(), 'ROLE_NORMAL_USER');
INSERT INTO role (id, created, name) VALUES (2, now(), 'ROLE_ADMIN');
INSERT INTO role (id, created, name) VALUES (3, now(), 'ROLE_SUPER_ADMIN');


INSERT INTO USER_AUTHORITY (USER_ID, ROLE_ID)  VALUES (1,2);
INSERT INTO USER_AUTHORITY (USER_ID, ROLE_ID)  VALUES (2,1);
