DROP TABLE BOOK_CATEGORY;
CREATE TABLE BOOK_CATEGORY (
 ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 NAME VARCHAR(100) NOT NULL
);

DROP TABLE BOOK;
CREATE TABLE BOOK (
 ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 CATEGORY_ID INT NOT NULL,
 ISBN VARCHAR(100) NOT NULL,
 NAME VARCHAR(100) NOT NULL,
 AUTHOR VARCHAR(100) NOT NULL,
 QUANTITY NUMBER(2),
 REMAINING_QUANTITY NUMBER(2)
);

DROP TABLE BOOK_REQUEST;
CREATE TABLE BOOK_REQUEST (
 ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 ISBN VARCHAR(100),
 NAME VARCHAR(100) NOT NULL,
 AUTHOR VARCHAR(100) NOT NULL
);

DROP TABLE USER;
CREATE TABLE USER (
 ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
 USERNAME VARCHAR(100) NOT NULL,
 FULL_NAME VARCHAR(100) NOT NULL,
 PASSWORD VARCHAR(100) NOT NULL,
 ROLE VARCHAR(100) NOT NULL,
 CONSTRAINT UC_USERNAME UNIQUE (USERNAME)
);

DROP TABLE RENTAL;
CREATE TABLE RENTAL (
  ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  USER_ID INT NOT NULL,
  BOOK_ID INT NOT NULL,
  RENT_DATE VARCHAR(10),
  RETURN_DATE VARCHAR(10),
  STATUS VARCHAR(10)
);


INSERT INTO BOOK (NAME, AUTHOR, QUANTITY, REMAINING_QUANTITY) VALUES('标准日本语', '不知道', 10, 10);
INSERT INTO BOOK (NAME, AUTHOR, QUANTITY, REMAINING_QUANTITY) VALUES('计算中的上帝', '索耶', 5, 5);

INSERT INTO USER (USERNAME, FULL_NAME, PASSWORD, ROLE) VALUES('admin', '管理员', '{noop}admin', 'ADMIN');