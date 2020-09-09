INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('josep@tecnocampus.cat', 'El crack', 'Indefinite', 'Bisexual', 'Sport');
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('jordi@tecnocampus.cat', 'The best', 'Man', 'Bisexual', 'Sport');
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('maria@tecnocampus.cat', 'Maria', 'Woman', 'Man', 'Sport');
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('marta@tecnocampus.cat', 'Marta', 'Woman', 'Man', 'Sport');
INSERT INTO tinder_user (email, nickname, gender, attraction, passion) VALUES ('pepe@tecnocampus.cat', 'Pepeillo', 'Man', 'Bisexual', 'Sport');

INSERT INTO proposal(origin, target, creation_date) VALUES ('josep@tecnocampus.cat', 'marta@tecnocampus.cat', current_date());
INSERT INTO proposal(origin, target, creation_date) VALUES ('josep@tecnocampus.cat', 'maria@tecnocampus.cat', current_date());
INSERT INTO proposal(origin, target, creation_date) VALUES ('josep@tecnocampus.cat', 'jordi@tecnocampus.cat', current_date());
INSERT INTO proposal(origin, target, creation_date) VALUES ('jordi@tecnocampus.cat', 'marta@tecnocampus.cat', current_date());
INSERT INTO proposal(origin, target, creation_date) VALUES ('jordi@tecnocampus.cat', 'maria@tecnocampus.cat', current_date());
INSERT INTO proposal(origin, target, creation_date) VALUES ('marta@tecnocampus.cat', 'pepe@tecnocampus.cat', current_date());
