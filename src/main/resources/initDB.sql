DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS Person;

CREATE TABLE Person
(
    id            int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name     varchar(50) UNIQUE NOT NULL,
    year_of_birth int         NOT NULL
);

CREATE TABLE Book
(
    id        int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title     varchar(50) NOT NULL,
    author    varchar(50) NOT NULL,
    year      int         NOT NULL,
    person_id int         REFERENCES Person (id) ON DELETE SET NULL
);