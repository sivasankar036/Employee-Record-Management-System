import java.util.*;

/**
 * Employee Record Management System
 * Standalone version - no database required
 * Demonstrates: Encapsulation, OOP, Exception Handling, ArrayList
 *
 * Author: Bharidu Sivasankar
 * GitHub: github.com/sivasankar036
 */

// Employee class - demonstrates ENCAPSULATION
class Employee {

    // Private fields - cannot be accessed directly from outside
    private int    id;
    private String name;
    private String department;
    private double salary;

    // Constructor - runs when new Employee object is created
    public Employee(int id, String name, String department, double salary) {
        this.id         = id;
        this.name       = name;
        this.department = department;
        this.salary     = salary;
    }

    // Getter methods - only way to READ private data
    public int    getId()         { return id; }
    public String getName()       { return name; }
    public String getDepartment() { return department; }
    public double getSalary()     { return salary; }

    // Setter method - only way to CHANGE private data
    public void setSalary(double salary) { this.salary = salary; }

    // toString override - POLYMORPHISM (Method Overriding)
    // Called automatically when System.out.println(emp) is used
    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | Name: %-20s | Department: %-15s | Salary: Rs.%.2f",
            id, name, department, salary
        );
    }
}

// Main class
public class Main {

    // ArrayList stores Employee objects dynamically (no fixed size)
    static List<Employee> employeeList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextId = 1; // simulates AUTO_INCREMENT like MySQL

    public static void main(String[] args) {

        // Pre-load 3 sample employees
        employeeList.add(new Employee(nextId++, "Ravi Kumar",   "Engineering", 45000));
        employeeList.add(new Employee(nextId++, "Priya Sharma", "HR",          38000));
        employeeList.add(new Employee(nextId++, "Anil Reddy",   "Finance",     52000));

        System.out.println("==========================================");
        System.out.println("   EMPLOYEE RECORD MANAGEMENT SYSTEM     ");
        System.out.println("==========================================");

        // Infinite loop keeps menu running until user exits
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

    // ADD new employee
    static void addEmployee() {
        System.out.println("\n--- ADD EMPLOYEE ---");
        System.out.print("Enter Name       : ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Department : ");
        String dept = sc.nextLine().trim();

        double salary = getDoubleInput("Enter Salary     : ");

        employeeList.add(new Employee(nextId++, name, dept, salary));
        System.out.println("Employee added successfully!");
    }

    // VIEW all employees
    static void viewAll() {
        System.out.println("\n--- ALL EMPLOYEES ---");
        if (employeeList.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        System.out.println(String.format("%-6s %-22s %-17s %s",
            "ID", "NAME", "DEPARTMENT", "SALARY"));
        System.out.println("-".repeat(65));
        for (Employee emp : employeeList) {
            System.out.println(emp); // calls toString() automatically
        }
        System.out.println("Total employees: " + employeeList.size());
    }

    // SEARCH by ID
    static void searchById() {
        System.out.println("\n--- SEARCH EMPLOYEE ---");
        int id = getIntInput("Enter Employee ID: ");
        Employee found = findById(id);
        if (found != null) {
            System.out.println("Employee found:");
            System.out.println(found);
        } else {
            System.out.println("No employee found with ID: " + id);
        }
    }

    // UPDATE salary
    static void updateSalary() {
        System.out.println("\n--- UPDATE SALARY ---");
        int id = getIntInput("Enter Employee ID: ");
        Employee emp = findById(id);
        if (emp == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }
        System.out.println("Current: " + emp);
        double newSalary = getDoubleInput("Enter new Salary: ");
        emp.setSalary(newSalary); // uses setter method - Encapsulation
        System.out.println("Salary updated successfully!");
        System.out.println("Updated: " + emp);
    }

    // DELETE employee
    static void deleteEmployee() {
        System.out.println("\n--- DELETE EMPLOYEE ---");
        int id = getIntInput("Enter Employee ID to delete: ");
        Employee emp = findById(id);
        if (emp == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }
        System.out.println("Deleting: " + emp);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (confirm.equals("yes")) {
            employeeList.remove(emp);
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // Helper: search ArrayList by ID
    static Employee findById(int id) {
        for (Employee emp : employeeList) {
            if (emp.getId() == id) {
                return emp; // found
            }
        }
        return null; // not found
    }

    // Helper: safe integer input with exception handling
    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                // Exception handling - program does not crash on bad input
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Helper: safe double input with exception handling
    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
