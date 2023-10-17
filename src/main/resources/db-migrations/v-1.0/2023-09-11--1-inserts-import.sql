INSERT INTO role (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (username, password)
VALUES ('user', '$2a$12$l7R59Z4EzXqzPwKB1wEhU.QMdvp.ITrKlEN5SJkQRaSriVwxbeNlG'),
       ('admin', '$2a$12$ObOzgB2NDmPgrOJAFEitre0vrZnZRkxrnmscmYoUWXozgbGhLFAsC');


INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO chop (number, name, description)
VALUES ('123232','Armenia43','Aaaaaaaaaaaaaaaaaaaaaaaa'),
       ('465356','Jeludz','Bbbbbbbbbbbbbbbbbbbbbbbbbb'),
       ('834593','Yuri','Oooooooooooooooooooooooooo'),
       ('904365','Allem','Pppppppppppppppppppppppppppp'),
       ('546547','Argom','Nnnnnnnnnnnnnnnnnnnnnnnnnnnnn'),
       ('439003','Libero','Llllllllllllllllllllll'),
       ('333215','Kiraldj','Cccccccccccccccccccccccccccc');