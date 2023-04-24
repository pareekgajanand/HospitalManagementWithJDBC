package hospitalManagent;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static java.sql.Types.NULL;

public class Patients {
    String fname, lname, dname, city, gen;
    int age,id1;
    int delid;
    int did = NULL;

    //    void Patients(){
//        show();
//    }
    void show() throws IOException, SQLException {
        boolean y = true;
        while (y) {
            System.out.println("Enter The Task You Want to Perform :-");
            System.out.println("\u001B[36m" + "          1.New Patient Entry\n" +
                    "          2.All Patient Details\n" +
                    "          3.Search Patient\n" +
                    "          4.Update Patient Record\n" +
                    "          5.Delete Record\n" +
                    "          6.Exit" + "\u001B[0m");
            Scanner Sin = new Scanner(System.in);
            boolean x = true;
//            Sin.nextLine();
            int userInput = 0;
            while (x) {
                System.out.print("Enter Your Choice Here : ");
                if (Sin.hasNextInt()) {

                    userInput = Sin.nextInt();
                    System.out.println("You entered: " + userInput);
                    if (userInput < 1 || userInput > 6) {
                        System.out.println("Please Select Valid Option");
                    } else {
                        x = false;
                    }
                } else {
                    System.out.println("Error: input must be an integer.");
                    Sin.nextLine();
                }
            }
            switch (userInput) {
                case 1:
                    System.out.println("Enter Patient Details Below:-");
                    System.out.print("Enter Patient First Name :");

                    fname = nameset();
                    System.out.print("Enter Patient Last Name :");
                    lname = nameset();
                    System.out.println("Enter Patient Age Here : ");

                    age = ageset(0,140,"Patients");
                    gen = genset();
                    System.out.print("Enter Patient City Here : ");

                    city = nameset();
                    System.out.print("Enter Your Choice For Selecting Doctor\n" +
                            "        1.NOT WANT\n" +
                            "        2.WANT TO SELECT NOW\n");
                    int ch = 6;
                    while (ch < 1 || ch > 2) {
                        ch = intset();
                        if (ch < 1 || ch > 2) {
                            System.out.println("Enter a Valid Option......");
                        }
                    }
                    Statement stmt;
                    ResultSet rs;
                    if (ch == 1) {
                        did = 0;

                    } else {
                        System.out.println("Select Available Doctors From This Table :-");
                        Doctors d = new Doctors();
                        d.datashow();
                        System.out.print("Enter The Doctor ID Patient Want To Consult :");
                        Connection conn = null;
                        stmt = null;
                        rs = null;
                        Scanner scanner = new Scanner(System.in);

                        try {
                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                            boolean found = false;
                            int id;
                            do {

                                id = intset();

                                stmt = conn.createStatement();
                                rs = stmt.executeQuery("SELECT * FROM doctors WHERE did = " + id);

                                if (rs.next()) {
                                    found = true;
                                    String name = rs.getString("fname");
                                    did = id;
                                    System.out.println("ID: " + id + ", Name: " + name);
                                } else {
                                    System.out.println("ID " + id + " not found. Please try again.");
                                }

                            } while (!found);

                        } catch (SQLException e) {
                            System.err.println("Failed to connect to MySQL database!");
                            e.printStackTrace();
                        }

                    }
                    Connection conn = DatabaseConnection.getConnection();
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = today.format(formatter);
                    String query = "INSERT INTO patients (fname, lname, age, gen, city, did, date) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
                        PreparedStatement statement = connection.prepareStatement(query);


                        statement.setString(1, fname);
                        statement.setString(2, lname);
                        statement.setInt(3, age);
                        statement.setString(4, gen);
                        statement.setString(5, city);
                        if (ch == 1) {
                            statement.setNull(6, java.sql.Types.INTEGER);
                        } else {
                            statement.setInt(6, did);
                        }
                        statement.setString(7, dateString);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("A new row has been inserted!");
                        } else {
                            System.out.println("Failed to insert a new row!");
                        }
                    } catch (SQLException e) {
                        System.err.println("SQL error occurred!");
                        e.printStackTrace();
                    }
//                    boolean d =true;
//                    while(d) {
//                        System.out.println("Available Doctors:-: ");
//                        System.out.println("+------------+------------+------------+------------+------------+------------+");
//                        System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Name", "Age","City","Gender", "Dept","JoinDate");
//                        System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+");
//
//
//                    }


                    break;
                case 2:
                    System.out.println("\u001B[32m" + "Here IS Your All Patient In The Data :-" + "\u001B[0m");
                    System.out.println("+------------+------------+------------+------------+------------+------------+------------+------------+");
                    System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "PatientID", "FirstName", "LastName", "Age", "Gender", "City", "DoctorID", "Date");

                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
                    Connection conn1;
                    conn1 = DatabaseConnection.getConnection();

                    stmt = conn1.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM patients");
                    while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("pid"), rs.getString("fname"), rs.getString("lname"), rs.getInt("age"), rs.getString("gen"), rs.getString("city"), rs.getString("did"), rs.getString("Date"));

                    }
                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");

//                    conn1.close();
                    break;
                case 3:
                    System.out.print("Enter The Patient Name You Want TO Search :- ");

                    String s1 = nameset();

                    Connection conn12;
                    conn12 = DatabaseConnection.getConnection();
                     s1 = s1.toLowerCase();
                    stmt = conn12.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM patients WHERE LOWER(fname) = '" + s1 + "'");
                    if (!rs.next()) {
                        System.out.println("No results found for name: " + s1);
                    } else {
                        System.out.println("\u001B[32m" + "Here IS Your All Patient In The Data :-" + "\u001B[0m");
                        System.out.println("+------------+------------+------------+------------+------------+------------+------------+------------+");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "PatientID", "FirstName", "LastName", "Age", "Gender", "City", "DoctorID", "Date");

                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
                        do {
                            System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("pid"), rs.getString("fname"), rs.getString("lname"), rs.getInt("age"), rs.getString("gen"), rs.getString("city"), rs.getString("did"), rs.getString("Date"));
                        } while (rs.next());
                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
                        // Process the query results
                    }


//                    conn12.close();


                    break;
                case 4:
                    System.out.println("\u001B[34m" + "Select PatientID You Want To Update:- " + "\u001B[0m");
                    System.out.println("+------------+------------+------------+------------+------------+------------+------------+------------+");
                    System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "PatientID", "FirstName", "LastName", "Age", "Gender", "City", "DoctorID", "Date");

                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
//                    Connection conn1;
                    conn1 = DatabaseConnection.getConnection();

                    stmt = conn1.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM patients");
                    while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("pid"), rs.getString("fname"), rs.getString("lname"), rs.getInt("age"), rs.getString("gen"), rs.getString("city"), rs.getString("did"), rs.getString("Date"));

                    }
                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
                    System.out.println("ENTER THE ID HERE : ");
                    try {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = intset();

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM patients WHERE pid = " + id);

                            if (rs.next()) {
                                found = true;
                                String name = rs.getString("fname");
                                id1 = id;
                                System.out.println("ID: " + id + ", Name: " + name);
                            } else {
                                System.out.println("ID " + id + " not found. Please try again.");
                            }

                        } while (!found);

                    } catch (SQLException e) {
                        System.err.println("Failed to connect to MySQL database!");
                        e.printStackTrace();
                    }
                    System.out.print("Enter Your Choice You Want To Update:--\n" +
                            "          1.First Name\n" +
                            "          2.Last Name\n" +
                            "          3.Age Of Patient\n" +
                            "          4.Gender Of Patient\n" +
                            "          5.City\n" +
                            "          6.Doctor ID\n" +
                            "          7.Exit\n");

                    int ch1 = chset(1,7);
                    switch (ch1){
                        case 1:
                            System.out.print("Enter The First Name You Want To Replace : ");
                            String rfname = nameset();
                            System.out.println(id1);
                            updateRow("patients","fname",rfname,id1,"pid");

                            break;
                        case 2:
                            System.out.print("Enter The Last Name You Want To Replace : ");
                            String lfname = nameset();
                            System.out.println(id1);
                            updateRow("patients","lname",lfname,id1,"pid");
                            break;
                        case 3:
                            System.out.print("Enter The Age You Want To Replace : ");
                            int  rage = ageset(0,150,"Patients");
                            System.out.println(id1);
                            updateRow1("patients","age",rage,id1,"pid");
                            break;
                        case 4:
                            System.out.print("Enter Gender You Want To Replace : ");
                            String rgen = genset();
                            System.out.println(id1);
                            updateRow("patients","gen",rgen,id1,"pid");
                            break;
                        case 5:
                            System.out.print("Enter The City You Want To Replace : ");
                            String rcity = nameset();
                            System.out.println(id1);
                            updateRow("patients","city",rcity,id1,"pid");
                            break;
                        case 6:
                            System.out.print("Enter The Doctor ID You Want To Replace : ");
                            Doctors d1 = new Doctors();
                            d1.datashow();
                            try {
                                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                                boolean found = false;
                                int id;
                                do {

                                    id = intset();

                                    stmt = conn.createStatement();
                                    rs = stmt.executeQuery("SELECT * FROM doctors WHERE did = " + id);

                                    if (rs.next()) {
                                        found = true;
                                        String name = rs.getString("fname");
                                        did = id;
                                        System.out.println("ID: " + id + ", Name: " + name);
                                    } else {
                                        System.out.println("ID " + id + " not found. Please try again.");
                                    }

                                } while (!found);

                            } catch (SQLException e) {
                                System.err.println("Failed to connect to MySQL database!");
                                e.printStackTrace();
                            }
                            updateRow1("patients","did",did,id1,"pid");

                            break;
                        case 7:
                            break;
                    }
                    break;
                case 5:
                    System.out.println("\u001B[32m" + "Here IS Your All Patient In The Data :-" + "\u001B[0m");
                    System.out.println("+------------+------------+------------+------------+------------+------------+------------+------------+");
                    System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "PatientID", "FirstName", "LastName", "Age", "Gender", "City", "DoctorID", "Date");

                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
//                    Connection conn1;
                    conn1 = DatabaseConnection.getConnection();

                    stmt = conn1.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM patients");
                    while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("pid"), rs.getString("fname"), rs.getString("lname"), rs.getInt("age"), rs.getString("gen"), rs.getString("city"), rs.getString("did"), rs.getString("Date"));

                    }
                    System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+------------+");
                    System.out.println("Enter The Patient ID You Want To  Delete: ");

                    try {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = intset();

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM patients WHERE pid = " + id);

                            if (rs.next()) {
                                found = true;
                                String name = rs.getString("fname");
                                delid = id;
                                System.out.println("ID: " + id + ", Name: " + name);
                            } else {
                                System.out.println("ID " + id + " not found. Please try again.");
                            }

                        } while (!found);

                    } catch (SQLException e) {
                        System.err.println("Failed to connect to MySQL database!");
                        e.printStackTrace();
                    }
                    deleteRow(delid,"patients","pid");


                    break;
                case 6:
                    y = false;
//                    conn.close();
                    break;
            }
        }


    }

    String nameset() {

        String n1;
        Scanner scanner = new Scanner(System.in);

        do {
//         System.out.print("Enter a string: ");
            n1 = scanner.nextLine();
            if (!n1.matches("[a-zA-Z]+")) {
                System.out.println("Invalid input. Please enter a string containing only alphabetic characters.");
            }
        } while (!n1.matches("[a-zA-Z]+"));

        // Do something with the valid input string

//     scanner.nextLine(); // Clear the input buffer
        return n1;
    }

    int intset() {
        int n;
        Scanner Sin2 = new Scanner(System.in);
//        Sin2.nextLine();
        while (!Sin2.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid Number:");
            Sin2.next();

        }
        n = Sin2.nextInt();
//        Sin2.nextLine();
        return n;
    }

    String genset() {
        System.out.print("Enter Number For Gender\n" +
                "       1.Male\n" +
                "       2.Female\n" +
                "       3.Others\n");
        int ch = 6;
        while (1 > ch || ch > 3) {
            ch = intset();
            if (ch < 1 || ch > 3) {
                System.out.println("Please Enter A Valid Option.....");
            }
        }
        switch (ch) {
            case 1:
                return "male";
            case 2:
                return "female";
            case 3:
                return "Others";
        }
        return "null";
    }

    int ageset(int a,int b ,String name) {
        int n1=200;
        System.out.print("Enter The Age :");
        while (n1 > b || n1<a) {
            n1 = intset();
            if (n1 > b||n1<a) {
                System.out.println("\u001B[31m" + "Please Enter A Valid  Age Number For " +name+"......." + "\u001B[0m");
            }
        }
        return n1;
    }

    int chset(int a, int b) {
        int ch = a + b;
        System.out.println("Enter Your Option Here");
        while (ch < a || ch > b) {
            ch = intset();
            if (ch < a || ch > b) {
                System.out.println("Enter a Valid Option......");
            }

        }

        return ch;
    }
    public void updateRow1(String tableName, String columnName, int newValue, int id,String tid) {
        String query = "UPDATE " + tableName + " SET " + columnName + "=? WHERE "+tid+"=?";
        System.out.println(id);
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newValue);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed. No rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error Occured");
        }
    }
    public void updateRow(String tableName, String columnName, String newValue, int id,String tid) {
        String query = "UPDATE " + tableName + " SET " + columnName + "=? WHERE "+tid+"=?";
        System.out.println(id);
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newValue);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed. No rows affected.");
            }
        } catch (SQLException e) {
            System.out.println("Error Occured");
        }
    }
    public  void deleteRow(int id, String tableName,String idname) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/hospital";
        String username = "root";
        String password = "";

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement to delete the row with the given ID from the specified table
            String sql = "DELETE FROM " + tableName + " WHERE "+idname+" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            // Execute the SQL statement
            int rowsDeleted = statement.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");

            // Close the database connection
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
