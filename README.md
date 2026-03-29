# Employee Record Management System

A menu-driven Java application to manage employee records using Object-Oriented Programming principles.

## Features
- Add new employees
- View all employees
- Search employee by ID
- Update employee salary
- Delete employee with confirmation
- Input validation with exception handling

## Technologies Used
- Java (Core)
- OOP Concepts (Encapsulation, Inheritance, Polymorphism)
- ArrayList (Data Structure)
- Exception Handling (try-catch)
- JDBC + MySQL (Full database version)

## OOP Concepts Demonstrated
| Concept | Where Used |
|---|---|
| Encapsulation | Employee class — private fields with getters/setters |
| Polymorphism | toString() method override |
| Exception Handling | NumberFormatException in input methods |
| Collections | ArrayList to store Employee objects |

## How to Run (Online — No Setup Required)
1. Go to [OnlineGDB Java Compiler](https://www.onlinegdb.com/online_java_compiler)
2. Delete existing code
3. Paste the contents of `Main.java`
4. Click **Run**

## How to Run (Local with MySQL)
### Prerequisites
- Java JDK 8 or above
- MySQL Server 8.0
- MySQL Connector JAR (`mysql-connector-j-8.x.x.jar`)

### Step 1 — Setup Database
Open MySQL Workbench and run:
```sql
CREATE DATABASE employee_db;
USE employee_db;
CREATE TABLE employees (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    department VARCHAR(50)    NOT NULL,
    salary     DECIMAL(10,2)  NOT NULL
);
INSERT INTO employees (name, department, salary) VALUES
('Ravi Kumar',   'Engineering', 45000.00),
('Priya Sharma', 'HR',          38000.00),
('Anil Reddy',   'Finance',     52000.00);
```

### Step 2 — Update Password
In `MainJDBC.java` find this line and change to your MySQL password:
```java
private static final String PASSWORD = "your_password_here";
```

### Step 3 — Compile and Run
```bash
javac -cp ".;mysql-connector-j-8.0.33.jar" MainJDBC.java
java  -cp ".;mysql-connector-j-8.0.33.jar" MainJDBC
```

## Project Structure
```
EmployeeSystem/
    Main.java         # Standalone version (no database needed)
    MainJDBC.java     # Full JDBC + MySQL version
    README.md         # Project documentation
```

## Sample Output
```
==========================================
   EMPLOYEE RECORD MANAGEMENT SYSTEM
==========================================

------------------------------------------
  1. Add Employee
  2. View All Employees
  3. Search Employee by ID
  4. Update Employee Salary
  5. Delete Employee
  6. Exit
------------------------------------------
Enter your choice: 2

--- ALL EMPLOYEES ---
ID     NAME                   DEPARTMENT        SALARY
-----------------------------------------------------------------
ID: 1    | Name: Ravi Kumar          | Department: Engineering    | Salary: Rs.45000.00
ID: 2    | Name: Priya Sharma        | Department: HR             | Salary: Rs.38000.00
ID: 3    | Name: Anil Reddy          | Department: Finance        | Salary: Rs.52000.00
Total employees: 3
```

## Author
**Bharidu Sivasankar**
- LinkedIn: [linkedin.com/in/siva-sankar36](https://linkedin.com/in/siva-sankar36)
- GitHub: [github.com/sivasankar036](https://github.com/sivasankar036)
- Email: sivasankar102030@gmail.com
