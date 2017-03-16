
DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee(
                              id   INT(4)  NOT NULL,
                              name VARCHAR (20)     NOT NULL,
                              experience  DOUBLE(10,2)    NOT NULL,
                              PRIMARY KEY (id)
                           );



DROP TABLE IF EXISTS project;

CREATE TABLE IF NOT EXISTS project(
                              projectId   INT(4)  NOT NULL,
                              empId INT(4) NOT NULL,
                              pName  VARCHAR(15)    NOT NULL,
                              projectDuration DOUBLE(10,2) NOT NULL,
                              PRIMARY KEY (projectId),
                              FOREIGN KEY(empId) REFERENCES employee(id)
                           );