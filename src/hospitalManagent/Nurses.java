package hospitalManagent;

import java.security.spec.RSAOtherPrimeInfo;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Nurses {
    String nfname, nlname, spl, gen;
    int age, nid, nid1;

    void show() throws SQLException {
        Patients p = new Patients();
        boolean y = true;
        while (y) {
            System.out.println("Enter The Task You Want to Perform :-");
            System.out.println("\u001B[36m" + "          1.New Nurse Entry\n" +
                    "          2.All Nurse's Details\n" +
                    "          3.Search Nurse\n" +
                    "          4.Update Nurse Records\n" +
                    "          5.Delete Record\n" +
                    "          6.Exit" + "\u001B[0m");
            Scanner Sin = new Scanner(System.in);
            boolean x = true;
            int userInput = 1;
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
                    System.out.println("Enter Nurse Details Below:-");
                    System.out.print("Enter Nurse First Name :");

                    nfname = p.nameset();
                    System.out.print("Enter Nurse Last Name :");
                    nlname = p.nameset();
                    System.out.println("Enter Nurse Age Here : ");

                    age = p.ageset(22,60,"Nurse");
                    gen = p.genset();
                    System.out.print("Enter Nurse Specialization Here : ");
                    spl = p.nameset();
                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = today.format(formatter);
                    String query = "INSERT INTO nurses(fname, lname,gen,age,spl,Joindate) VALUES (?, ?, ?, ?, ?, ?)";
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
                        PreparedStatement statement = connection.prepareStatement(query);


                        statement.setString(1, nfname);
                        statement.setString(2, nlname);
                        statement.setString(3, gen);
                        statement.setInt(4, age);
                        statement.setString(5, spl);
                        statement.setString(6, dateString);

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
                    break;
                case 2:
                    datashow();

                    break;
                case 3:
                    System.out.println("Enter Nurse Name You Want To Search : ");
                    String s1 = p.nameset();

                    Connection conn12;
                    conn12 = DatabaseConnection.getConnection();
                    Statement stmt;
                    ResultSet rs;
                    stmt = conn12.createStatement();
                    s1 = s1.toLowerCase();
                    rs = stmt.executeQuery("SELECT * FROM nurses WHERE LOWER(fname) = '" + s1 + "'");
//                    rs = stmt.executeQuery("SELECT * FROM nurses WHERE LOWER(fname) = '" + s1 + "'");
                    if (!rs.next()) {
                        System.out.println("No results found for name: " + s1);
                    } else {
                        System.out.println("\u001B[32m" + "Here IS  Nurse The Data :-" + "\u001B[0m");
                        System.out.println("+------------+------------+------------+------------+------------+------------+------------+");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "NurseID", "FirstName", "LastName","Gender","Age","Spl","JoinDate");

                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+");
                        do {

                            System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("nid"), rs.getString("fname"), rs.getString("lname"), rs.getString("gen"), rs.getInt("age"), rs.getString("spl"), rs.getString("Joindate"));
                        } while (rs.next());
                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+-");
                        // Process the query results
                    }

                    break;
                case 4:
                    System.out.println("Select The NurseID You Want To Update :-- ");
                    datashow();
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = p.intset();

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM nurses WHERE nid = " + id);

                            if (rs.next()) {
                                found = true;
                                String name = rs.getString("fname");
                                nid = id;
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
                            "          3.Age Of Nurse\n" +
                            "          4.Gender Of Nurse\n" +
                            "          5.Nurse's Specialization\n" +
                            "          6.Exit\n");

                    int ch1 = p.chset(1,6);
                    switch (ch1){
                        case 1:
                            System.out.print("Enter The First Name You Want To Replace : ");
                            String rfname = p.nameset();
                            System.out.println(nid);
                            p.updateRow("nurses","fname",rfname,nid,"nid");
                            break;
                        case 2:
                            System.out.print("Enter The Last Name You Want To Replace : ");
                            String rlname = p.nameset();
                            System.out.println(nid);
                            p.updateRow("nurses","lname",rlname,nid,"nid");

                            break;
                        case 3:
                            System.out.print("Enter The Age You Want To Replace : ");
                            int  rage = p.ageset(22,60,"Nurse");
                            System.out.println(nid);
                            p.updateRow1("nurses","age",rage,nid,"nid");
                            break;
                        case 4:
                            System.out.print("Enter Gender You Want To Replace : ");
                            String rgen = p.genset();
                            System.out.println(nid);
                            p.updateRow("nurses","gen",rgen,nid,"nid");
                            break;
                        case 5:
                            System.out.print("Enter Specialization You Want To Replace : ");
                            String rspl = p.nameset();
                            System.out.println(nid);
                            p.updateRow("nurses","spl",rspl,nid,"nid");
                            break;
                        case 6:
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Select The NurseID You Want To Delete:--");
                    datashow();
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = p.intset();

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM nurses WHERE nid = " + id);

                            if (rs.next()) {
                                found = true;
                                String name = rs.getString("fname");
                                nid1 = id;
                                System.out.println("ID: " + id + ", Name: " + name);
                            } else {
                                System.out.println("ID " + id + " not found. Please try again.");
                            }

                        } while (!found);

                    } catch (SQLException e) {
                        System.err.println("Failed to connect to MySQL database!");
                        e.printStackTrace();
                    }
                    p.deleteRow(nid1,"nurses","nid");
                    break;
                case 6:
                    y = false;
                    break;
            }
        }
    }

    public void datashow() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
            System.out.println("Connected to MySQL database!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM nurses");
            System.out.println("+------------+------------+------------+------------+------------+------------+------------+");
            System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Nid", "FName", "LName", "Gender", "Age", "Spl", "JoinDate");
            System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+");
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("nid"), rs.getString("fname"), rs.getString("lname"), rs.getString("gen"), rs.getInt("age"), rs.getString("spl"), rs.getString("Joindate"));

            }
            System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+");

        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL database!");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Failed to close connection to MySQL database!");
                e.printStackTrace();
            }
        }
    }
}
