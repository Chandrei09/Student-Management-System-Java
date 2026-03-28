CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    course VARCHAR(50)
);
SELECT * FROM students;