-- Departments
INSERT INTO Departments (dptN, name, HOD, noOfStaf) VALUES
('AC', 'Applied Computing', 'Dr. Laalitha S. I. Liyanage', 0),
('CSE', 'Computer Systems Engineering', 'Prof. N. G. J. D. Dias', 0),
('SE', 'Software Engineering', 'TBD', 0);

-- Degrees
INSERT INTO Degrees (degree, NoOfStd) VALUES
('Bachelor of Information and Communication Technology', 0),
('Bachelor of Engineering Technology', 0),
('Bachelor of Science in Computer Science', 0),
('Bachelor of Biosystems Technology', 0);

-- Courses
INSERT INTO courses (ccode, cname, credits) VALUES
('CTEC 21042', 'Web Programming', 2),
('CTEC 21063', 'Database Systems', 3),
('ETEC 21062', 'Object Oriented Programming for Engineering Technology', 2),
('ETEC 21043', 'Engineering Materials - I', 3),
('CSCI 21052', 'Object Oriented Programming', 2),
('CSCI 21042', 'Software Engineering', 2),
('BTEC 11053', 'Mathematics for Biosystems Technology', 3),
('BTEC 11013', 'Physics for Biosystems Technology – I', 3);

-- Users
INSERT INTO users (username, pws, Role) VALUES
('admin1', 'admin', 'Admin');

INSERT INTO users (username, pws, Role) VALUES
('lect_ac_1', 'lect1', 'Lecturer'),
('lect_cse_1', 'lect2', 'Lecturer'),
('lect_se_1', 'lect3', 'Lecturer');

INSERT INTO users (username, pws, Role) VALUES
('stud_cs_001', 'stud1', 'Student'),
('stud_cs_002', 'stud2', 'Student'),
('stud_et_001', 'stud3', 'Student'),
('stud_ct_001', 'stud4', 'Student'),
('stud_ct_002', 'stud5', 'Student');

-- Student Details
INSERT INTO SDetails (userName, Name, STDID, degree, email, mobile) VALUES
('stud_cs_001', 'Alice Perera', 'CS/2022/001', 'Bachelor of Science in Computer Science', 'alicep@kln.ac.lk', '0710000001'),
('stud_cs_002', 'Bimal Fernando', 'CS/2022/002', 'Bachelor of Science in Computer Science', 'bimalf@kln.ac.lk', '0710000002'),
('stud_et_001', 'Chamal Silva', 'ET/2023/001', 'Bachelor of Engineering Technology', 'chamals@kln.ac.lk', '0710000003'),
('stud_ct_001', 'Dilini Jayasuriya', 'CT/2024/001', 'Bachelor of Information and Communication Technology', 'dilini@kln.ac.lk', '0710000004'),
('stud_ct_002', 'Eshan Wijesinghe', 'CT/2024/002', 'Bachelor of Information and Communication Technology', 'eshanw@kln.ac.lk', '0710000005');

-- Lecturer Details
INSERT INTO LDetails (userName, Name, dpt, Ccode, email, mobile) VALUES
('lect_ac_1', 'Dr. Laalitha S. I. Liyanage', 'AC', NULL, 'laalitha@kln.ac.lk', '0770000001'),
('lect_cse_1', 'Prof. N. G. J. D. Dias', 'CSE', NULL, 'dias@kln.ac.lk', '0770000002'),
('lect_se_1', 'Mr. S. Perera', 'SE', NULL, 'sperera@kln.ac.lk', '0770000003');

-- Enrolled Courses
INSERT INTO EnrolledCourses (Uname, Ccode, grade) VALUES
('stud_cs_001', 'CSCI 21052', NULL),
('stud_cs_001', 'CSCI 21042', NULL),
('stud_cs_002', 'CSCI 21052', NULL),
('stud_et_001', 'ETEC 21062', NULL),
('stud_ct_001', 'CTEC 21042', NULL),
('stud_ct_002', 'CTEC 21063', NULL);

-- Department Degrees
INSERT INTO deptDegrees (dptN, dgree) VALUES
('AC', 'Bachelor of Information and Communication Technology'),
('CSE', 'Bachelor of Engineering Technology'),
('SE', 'Bachelor of Science in Computer Science'),
('AC', 'Bachelor of Biosystems Technology');

-- Course Lecturers
INSERT INTO CourseLec (ccode, Luser) VALUES
('CTEC 21042', 'lect_ac_1'),
('CTEC 21063', 'lect_ac_1'),
('ETEC 21062', 'lect_cse_1'),
('ETEC 21043', 'lect_cse_1'),
('CSCI 21052', 'lect_se_1'),
('CSCI 21042', 'lect_se_1'),
('BTEC 11053', 'lect_ac_1'),
('BTEC 11013', 'lect_ac_1');
