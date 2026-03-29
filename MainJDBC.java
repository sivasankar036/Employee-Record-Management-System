import java.sql.*;
import java.util.*;

/**
 * Employee Record Management System
 * Full JDBC + MySQL version
 * Demonstrates: JDBC, PreparedStatement, ResultSet, OOP, Exception Handling
 *
 * Prerequisites:
 *   - MySQL Server running
 *   - employee_db database created (see README.md)
 *   - mysql-connector-j-8.x.x.jar in same folder
 *
 * Compile: javac -cp ".;mysql-connector-j-8.0.33.jar" MainJDBC.java
 * Run:     java  -cp ".;mysql-connector-j-8.0.33.jar" MainJDBC
 *
 * Author: Bharidu Sivasankar
 * GitHub: github.com/sivasankar036
 */

// Database connection helper class
class DatabaseConnection {
    private static final String URL      = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password_here"; // CHANGE THIS

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

// Employee class - ENCAPSULATION
class EmployeeJDBC {
    private int    id;
    private String name;
    private String department;
    private double salary;

    public EmployeeJDBC(int id, String name, String department, double salary) {
        this.id = id; this.name = name;
        this.department = department; this.salary = salary;
    }

    public EmployeeJDBC(String name, String department, double salary) {
        this.name = name; this.department = department; this.salary = salary;
    }

    public int    getId()         { return id; }
    public String getName()       { return name; }
    public String getDepartment() { return department; }
    public double getSalary()     { return salary; }
    public void   setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | Name: %-20s | Department: %-15s | Salary: Rs.%.2f",
            id, name, department, salary
        );
    }
}

// Data Access Object - all database operations
class EmployeeDAO {

    // INSERT - add new employee
    public boolean addEmployee(EmployeeJDBC emp) {
        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getDepartment());
            stmt.setDouble(3, emp.getSalary());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // SELECT ALL - get all employees
    public List<EmployeeJDBC> getAllEmployees() {
        List<EmployeeJDBC> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new EmployeeJDBC(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    // SELECT ONE - search by ID
    public EmployeeJDBC getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new EmployeeJDBC(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    // UPDATE - change salary
    public boolean updateSalary(int id, double newSalary) {
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newSalary);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    // DELETE - remove employee
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}

// Main class
public class MainJDBC {

    static Scanner sc = new Scanner(System.in);
    static EmployeeDAO dao = new EmployeeDAO();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   EMPLOYEE RECORD MANAGEMENT SYSTEM     ");
        System.out.println("         (JDBC + MySQL Version)           ");
        System.out.println("==========================================");

        while (true) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1: addEmployee();    break;
                case 2: viewAll();        break;
                case 3: searchById();     break;
                case 4: updateSalary();   break;
                case 5: deleteEmployee(); break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Enter 1 to 6.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n------------------------------------------");
        System.out.println("  1. Add Employee");
        System.out.println("  2. View All Employees");
        System.out.println("  3. Search Employee by ID");
        System.out.println("  4. Update Employee Salary");
        System.out.println("  5. Delete Employee");
        System.out.println("  6. Exit");
        System.out.println("------------------------------------------");
    }

    static void addEmployee() {
        System.out.println("\n--- ADD EMPLOYEE ---");
        System.out.print("Enter Name       : ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Department : ");
        String dept = sc.nextLine().trim();
        double salary = getDoubleInput("Enter Salary     : ");
        if (dao.addEmployee(new EmployeeJDBC(name, dept, salary))) {
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Failed to add employee.");
        }
    }

    static void viewAll() {
        System.out.println("\n--- ALL EMPLOYEES ---");
        List<EmployeeJDBC> list = dao.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println(String.format("%-6s %-22s %-17s %s",
            "ID", "NAME", "DEPARTMENT", "SALARY"));
        System.out.println("-".repeat(65));
        for (EmployeeJDBC emp : list) {
            System.out.println(emp);
        }
        System.out.println("Total employees: " + list.size());
    }

    static void searchById() {
        System.out.println("\n--- SEARCH EMPLOYEE ---");
        int id = getIntInput("Enter Employee ID: ");
        EmployeeJDBC emp = dao.getEmployeeById(id);
        if (emp != null) {
            System.out.println("Employee found:");
            System.out.println(emp);
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    }

    static void updateSalary() {
        System.out.println("\n--- UPDATE SALARY ---");
        int id = getIntInput("Enter Employee ID: ");
        EmployeeJDBC emp = dao.getEmployeeById(id);
        if (emp == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }
        System.out.println("Current: " + emp);
        double newSalary = getDoubleInput("Enter new Salary: ");
        if (dao.updateSalary(id, newSalary)) {
            System.out.println("Salary updated successfully!");
        } else {
            System.out.println("Failed to update.");
        }
    }

    static void deleteEmployee() {
        System.out.println("\n--- DELETE EMPLOYEE ---");
        int id = getIntInput("Enter Employee ID to delete: ");
        EmployeeJDBC emp = dao.getEmployeeById(id);
        if (emp == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }
        System.out.println("Deleting: " + emp);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("yes")) {
            if (dao.deleteEmployee(id)) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Failed to delete.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
