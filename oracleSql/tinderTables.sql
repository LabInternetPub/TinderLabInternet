create table tinder_user
(
  id VARCHAR (256) PRIMARY KEY ,
  email VARCHAR (100) ,
  nickname VARCHAR (255),
  gender VARCHAR (50),
  attraction VARCHAR (50),
  passion VARCHAR (100),
  password VARCHAR(70) NOT NULL,
  enabled number(3) NOT NULL
);

CREATE TABLE tinder_like
(
  origin VARCHAR (256),
  target VARCHAR (256),
  creation_date DATE,
  match_date DATE DEFAULT NULL,
  matched number(3) NOT NULL,
  PRIMARY KEY (origin, target),
  FOREIGN KEY (origin) REFERENCES tinder_user(id),
  FOREIGN KEY (target) REFERENCES tinder_user(id)
);

create or replace trigger tinder_user_password
before insert or update on tinder_user
for each row
begin

IF :new.password is null
THEN
    :new.password := 'abcdef';
END IF;

end;

create or replace trigger tinder_user_enabled
before insert or update on tinder_user
for each row
begin

IF :new.enabled is null
THEN
    :new.enabled := 1;
END IF;

end;

create or replace trigger tinder_like_matched
before insert or update on tinder_like
for each row
begin

IF :new.matched is null
THEN
    :new.matched := 0;
END IF;

end;