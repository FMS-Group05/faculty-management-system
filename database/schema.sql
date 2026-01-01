
CREATE DATABASE IF NOT EXISTS faculty_db;
USE faculty_db;


CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Student', 'Lecturer') NOT NULL
);


CREATE TABLE departments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    hod_name VARCHAR(100) -- Head of Department [cite: 57]
);


CREATE TABLE degrees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES departments(id) ON DELETE CASCADE
);


CREATE TABLE students (
    student_id VARCHAR(20) PRIMARY KEY, -- Example: SC/2021/001
    user_id INT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    mobile VARCHAR(15),
    degree_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (degree_id) REFERENCES degrees(id)
);


CREATE TABLE lecturers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    dept_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (dept_id) REFERENCES departments(id)
);


CREATE TABLE courses (
    course_code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lecturer_id INT,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);


CREATE TABLE enrollments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(20),
    course_code VARCHAR(10),
    grade VARCHAR(2),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (course_code) REFERENCES courses(course_code)
);