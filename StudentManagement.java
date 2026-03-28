import java.sql.*;
import java.io.*;

public class StudentManagement {

    static final String URL = "jdbc:mysql://localhost:3307/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "Tania@123";

    static Connection con;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Update Student");
                System.out.println("4. Search Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewStudents();
                        break;
                    case 3:
                        updateStudent();
                        break;
                    case 4:
                        searchStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 6);

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }

    // ================= ADD =================
    static void addStudent() throws Exception {
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(br.readLine());

        System.out.print("Enter Name: ");
        String name = br.readLine();

        System.out.print("Enter Course: ");
        String course = br.readLine();

        String query = "INSERT INTO students VALUES (?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, course);

        pst.executeUpdate();
        System.out.println("Student Added Successfully!");
    }

    // ================= VIEW =================
    static void viewStudents() throws Exception {
        String query = "SELECT * FROM students";

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\nID\tName\tCourse");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getString("course")
            );
        }
    }

    // ================= UPDATE =================
    static void updateStudent() throws Exception {
        System.out.print("Enter ID to update: ");
        int id = Integer.parseInt(br.readLine());

        System.out.print("Enter new Name: ");
        String name = br.readLine();

        System.out.print("Enter new Course: ");
        String course = br.readLine();

        String query = "UPDATE students SET name=?, course=? WHERE id=?";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, course);
        pst.setInt(3, id);

        int rows = pst.executeUpdate();

        if (rows > 0)
            System.out.println("Student Updated!");
        else
            System.out.println("Student Not Found!");
    }

    // ================= SEARCH =================
    static void searchStudent() throws Exception {
        System.out.print("Enter ID to search: ");
        int id = Integer.parseInt(br.readLine());

        String query = "SELECT * FROM students WHERE id=?";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("Found:");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Course: " + rs.getString("course"));
        } else {
            System.out.println("Student Not Found!");
        }
    }

    // ================= DELETE =================
    static void deleteStudent() throws Exception {
        System.out.print("Enter ID to delete: ");
        int id = Integer.parseInt(br.readLine());

        String query = "DELETE FROM students WHERE id=?";

        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);

        int rows = pst.executeUpdate();

        if (rows > 0)
            System.out.println("Student Deleted!");
        else
            System.out.println("Student Not Found!");
    }
}
