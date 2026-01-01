INSERT INTO Users (userName, pws, Role) VALUES ('admin', 'admin', 'Admin');

-- Departments
INSERT INTO Dept (deptN, name, HOD, degree, NoOfStaf) VALUES 
('CS', 'Computer Science', 'Dr. Smith', 'BSc Computer Science', 15),
('SE', 'Software Engineering', 'Dr. Doe', 'BSc Software Engineering', 12);

-- Degrees
INSERT INTO Degrees (dgree, dept, NoOfStd) VALUES 
('BSc Computer Science', 'CS', 120),
('BSc Software Engineering', 'SE', 100);

-- Users (Lecturers)
INSERT INTO Users (userName, pws, Role) VALUES 
('lec1', '123', 'Lecturer'),
('lec2', '123', 'Lecturer');

-- Lecturer Details
INSERT INTO LDetails (userName, Name, deptN, email, mobile) VALUES 
('lec1', 'Dr. Alan Turing', 'CS', 'alan@uok.ac.lk', '0711234567'),
('lec2', 'Dr. Grace Hopper', 'SE', 'grace@uok.ac.lk', '0717654321');

-- Users (Students)
INSERT INTO Users (userName, pws, Role) VALUES 
('std1', '123', 'Student'),
('std2', '123', 'Student');

-- Student Details
INSERT INTO SDetails (STDID, userName, Name, degree, email, mobile) VALUES 
('S24001', 'std1', 'Kamal Perera', 'BSc Computer Science', 'kamal@uok.ac.lk', '0771112233'),
('S24002', 'std2', 'Nimal Silva', 'BSc Software Engineering', 'nimal@uok.ac.lk', '0774445566');
