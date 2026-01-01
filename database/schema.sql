CREATE DATABASE FMS;
USE FMS;

-- Core User Table
CREATE TABLE Users (
    userName VARCHAR(50) PRIMARY KEY,
    pws VARCHAR(255),
    Role VARCHAR(20)
);

-- Department Table
CREATE TABLE Dept (
    deptN VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    HOD VARCHAR(100),
    degree VARCHAR(50),
    NoOfStaf INT
);

-- Degrees Table
CREATE TABLE Degrees (
    dgree VARCHAR(50) PRIMARY KEY,
    dept VARCHAR(10),
    NoOfStd INT,
    FOREIGN KEY (dept) REFERENCES Dept(deptN)
);

-- Student Details
CREATE TABLE SDetails (
    STDID VARCHAR(20) PRIMARY KEY,
    userName VARCHAR(50),
    Name VARCHAR(100),
    degree VARCHAR(50),
    email VARCHAR(100),
    mobile VARCHAR(15),
    FOREIGN KEY (userName) REFERENCES Users(userName),
    FOREIGN KEY (degree) REFERENCES Degrees(dgree)
);

-- Lecturer Details
CREATE TABLE LDetails (
    userName VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100),
    deptN VARCHAR(10),
    email VARCHAR(100),
    mobile VARCHAR(15),
    FOREIGN KEY (userName) REFERENCES Users(userName),
    FOREIGN KEY (deptN) REFERENCES Dept(deptN)
);

-- Courses Table
CREATE TABLE Courses (
    ccode VARCHAR(10) PRIMARY KEY,
    cname VARCHAR(100),
    credits INT,
    Luser VARCHAR(50),
    FOREIGN KEY (Luser) REFERENCES LDetails(userName)
);

-- Enrollment (Linking Users to Courses)
CREATE TABLE EnrolledCourses (
    Uname VARCHAR(50),
    Ccode VARCHAR(10),
    grade VARCHAR(2),
    PRIMARY KEY (Uname, Ccode),
    FOREIGN KEY (Uname) REFERENCES Users(userName),
    FOREIGN KEY (Ccode) REFERENCES Courses(ccode)
);