
DROP TABLE IF EXISTS todo;

CREATE TABLE todo
(
    id int NOT NULL AUTO_INCREMENT,
    content varchar(255) NOT NULL,
     INS_DATETIME datetime NOT NULL,
     UPD_DATETIME datetime NOT NULL,
     PRIMARY KEY (id)
);
