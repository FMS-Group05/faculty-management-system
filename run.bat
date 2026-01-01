@echo off
echo Compiling...
if not exist bin mkdir bin
javac -d bin -cp "mysql-connector-j-9.5.0\mysql-connector-j-9.5.0\mysql-connector-j-9.5.0.jar" -sourcepath src src\com\faculty\main\MainApp.java
if %errorlevel% neq 0 exit /b %errorlevel%

echo Running...
java -cp "bin;mysql-connector-j-9.5.0\mysql-connector-j-9.5.0\mysql-connector-j-9.5.0.jar" com.faculty.main.MainApp
