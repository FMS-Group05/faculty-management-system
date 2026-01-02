-- Create the Admin, a Lecturer, and a Student
INSERT INTO users (username, pws, Role) VALUES 
('admin', 'admin', 'Admin'),
('lec1', 'lec123', 'Lecturer'),
('std1', 'std123', 'Student');

-- Add Departments and Degrees
INSERT INTO Departments (dptN, name, HOD, noOfStaf) VALUES 
('CS', 'Computer Science', 'Dr. Aris', 15);

INSERT INTO Degrees (degree, NoOfStd) VALUES 
('BSc Computer Science', 120);

-- Add your current courses with Day and Time
INSERT INTO courses (ccode, cname, credits) VALUES 
('CSCI 21001', 'Object Oriented Programming', 3),
('CSCI 21002', 'Software Engineering', 4);

-- Student Details
INSERT INTO SDetails (userName, Name, STDID, degree, email, mobile) VALUES 
('std1', 'John Doe', 'S10021', 'BSc Computer Science', 'jdoe@uni.edu', '0771122334');

-- Lecturer Details (Linking to CS dept and a course)
INSERT INTO LDetails (userName, Name, dpt, Ccode, email, mobile) VALUES 
('lec1', 'Dr. Smith', 'CS', 'CSCI 21001', 'smith@uni.edu', '0775566778');

-- Enrolling the student in the courses
INSERT INTO EnrolledCourses (Uname, Ccode, grade) VALUES 
('std1', 'CSCI 21001', 'A'),
('std1', 'CSCI 21002', 'B+');

-- Assigning the Lecturer to the Course
INSERT INTO CourseLec (ccode, Luser) VALUES 
('CSCI 21001', 'lec1');

-- Linking Department to Degrees
INSERT INTO deptDegrees (dptN, dgree) VALUES 
('CS', 'BSc Computer Science');