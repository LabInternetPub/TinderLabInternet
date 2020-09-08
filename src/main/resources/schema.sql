DROP TABLE if EXISTS tinder_user;
CREATE TABLE tinder_user
(
  email VARCHAR (100) PRIMARY KEY,
  nickname VARCHAR (255),
  gender VARCHAR (50),
  attraction VARCHAR (50),
  passion VARCHAR (100),
  password VARCHAR(70) NOT NULL DEFAULT '123456',
  enabled TINYINT NOT NULL DEFAULT 1
);

DROP TABLE if EXISTS proposal;
CREATE TABLE proposal
(
  origin VARCHAR (100),
  target VARCHAR (100),
  matched TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (origin, target)
);
