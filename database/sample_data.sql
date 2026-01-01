USE faculty_db;


INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'Admin');


INSERT INTO departments (name, hod_name) VALUES ('Software Engineering', 'Dr. Smith');
INSERT INTO departments (name, hod_name) VALUES ('Network Technology', 'Dr. Brown');


INSERT INTO degrees (name, dept_id) VALUES ('BSc in Software Engineering', 1);