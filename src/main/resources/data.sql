INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (to_char(random_uuid()), 'josep@tecnocampus.cat', 'El crack', 'Indefinite', 'Bisexual', 'Sport');
INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (to_char(random_uuid()), 'jordi@tecnocampus.cat', 'The best', 'Man', 'Bisexual', 'Sport');
INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (to_char(random_uuid()), 'maria@tecnocampus.cat', 'Maria', 'Woman', 'Man', 'Sport');
INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (to_char(random_uuid()), 'marta@tecnocampus.cat', 'Marta', 'Woman', 'Man', 'Sport');
INSERT INTO tinder_user (id, email, nickname, gender, attraction, passion) VALUES (to_char(random_uuid()), 'pepe@tecnocampus.cat', 'Pepeillo', 'Man', 'Bisexual', 'Sport');

INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'marta@tecnocampus.cat'), current_date() from tinder_user where email = 'josep@tecnocampus.cat';
INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'maria@tecnocampus.cat'), current_date() from tinder_user where email = 'josep@tecnocampus.cat';
INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'jordi@tecnocampus.cat'), current_date() from tinder_user where email = 'josep@tecnocampus.cat';
INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'marta@tecnocampus.cat'), current_date() from tinder_user where email = 'jordi@tecnocampus.cat';
INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'maria@tecnocampus.cat'), current_date() from tinder_user where email = 'jordi@tecnocampus.cat';
INSERT INTO tinder_like(origin, target, creation_date) select id, (select id from tinder_user where email = 'pepe@tecnocampus.cat'), current_date() from tinder_user where email = 'marta@tecnocampus.cat';

