
DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee(
                              id   INT  NOT NULL,
                              name VARCHAR (20)     NOT NULL,
                              experience  DOUBLE    NOT NULL,
                              PRIMARY KEY (id)
                           );