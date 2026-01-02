CREATE DATABASE FMS;
USE FMS;

-- 1. Create independent tables first
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    pws VARCHAR(255) NOT NULL,
    Role VARCHAR(20)
);

CREATE TABLE courses (
    ccode VARCHAR(10) PRIMARY KEY,
    cname VARCHAR(100),
    credits INT
);

CREATE TABLE Departments (
    dptN VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    HOD VARCHAR(100),
    noOfStaf INT
);

CREATE TABLE Degrees (
    degree VARCHAR(50) PRIMARY KEY,
    NoOfStd INT
);

-- 2. Create tables with Foreign Key dependencies
CREATE TABLE SDetails (
    userName VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100),
    STDID VARCHAR(20) UNIQUE,
    degree VARCHAR(50),
    email VARCHAR(100),
    mobile VARCHAR(15),
    FOREIGN KEY (userName) REFERENCES users(username),
    FOREIGN KEY (degree) REFERENCES Degrees(degree)
);

CREATE TABLE LDetails (
    userName VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100),
    dpt VARCHAR(10),
    Ccode VARCHAR(10),
    email VARCHAR(100),
    mobile VARCHAR(15),
    FOREIGN KEY (userName) REFERENCES users(username),
    FOREIGN KEY (dpt) REFERENCES Departments(dptN),
    FOREIGN KEY (Ccode) REFERENCES courses(ccode)
);

-- 3. Create Junction/Relationship tables
CREATE TABLE EnrolledCourses (
    Uname VARCHAR(50),
    Ccode VARCHAR(10),
    grade VARCHAR(2),
    PRIMARY KEY (Uname, Ccode),
    FOREIGN KEY (Uname) REFERENCES users(username),
    FOREIGN KEY (Ccode) REFERENCES courses(ccode)
);

CREATE TABLE deptDegrees (
    dptN VARCHAR(10),
    dgree VARCHAR(50),
    PRIMARY KEY (dptN, dgree),
    FOREIGN KEY (dptN) REFERENCES Departments(dptN),
    FOREIGN KEY (dgree) REFERENCES Degrees(degree)
);

CREATE TABLE CourseLec (
    ccode VARCHAR(10),
    Luser VARCHAR(50),
    PRIMARY KEY (ccode, Luser),
    FOREIGN KEY (ccode) REFERENCES courses(ccode),
    FOREIGN KEY (Luser) REFERENCES users(username)
);