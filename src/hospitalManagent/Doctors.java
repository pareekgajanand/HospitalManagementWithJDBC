package hospitalManagent;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Doctors {
    String dfname,dlname,spl,gen;
    int age,did,did1;
    void show() throws SQLException {
        Patients p = new Patients();
        boolean y = true;
        while(y){
            System.out.println("Enter The Task You Want to Perform :-");
            System.out.println("\u001B[36m" + "          1.New Doctor Entry\n" +
                    "          2.All Doctor's Details\n" +
                    "          3.Search Doctor\n" +
                    "          4.Update Doctor Records\n" +
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
                    System.out.println("Enter Doctor Details Below:-");
                    System.out.print("Enter Doctor First Name :");

                    dfname = p.nameset();
                    System.out.print("Enter Doctor Last Name :");
                    dlname = p.nameset();
                    System.out.print("Enter Doctor Age Here : ");

                    age = p.ageset(25,75,"Doctors");
                    System.out.print("Enter Doctor Gender Here : ");

                    gen = p.genset();
                    System.out.print("Enter Doctor Specialization Here : ");

                    spl = p.nameset();




                    LocalDate today = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dateString = today.format(formatter);
                    String query = "INSERT INTO doctors(fname, lname, age, spl,gen,Joindate) VALUES (?, ?, ?, ?, ?, ?)";
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
                        PreparedStatement statement = connection.prepareStatement(query);


                        statement.setString(1, dfname);
                        statement.setString(2, dlname);
                        statement.setInt(3, age);
                        statement.setString(4, spl);
                        statement.setString(5, gen);
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
                    System.out.println("\u001B[32m" + "Here IS Your All Doctors In The Data :-" + "\u001B[0m");
                    datashow();

                    break;
                case 3:
                    System.out.print("Enter The Doctor Name You Want To Search :- ");

                    String s1 = p.nameset();

                    Connection conn12;
                    conn12 = DatabaseConnection.getConnection();
                    Statement stmt;
                    ResultSet rs;
                    stmt = conn12.createStatement();
                    s1 = s1.toLowerCase();
                    rs = stmt.executeQuery("SELECT * FROM doctors WHERE LOWER(fname)= '" + s1 + "'");
                    if (!rs.next()) {
                        System.out.println("No results found for name: " + s1);
                    } else {
                        System.out.println("\u001B[32m" + "Here IS  Doctor The Data :-" + "\u001B[0m");
                        System.out.println("+------------+------------+------------+------------+------------+------------+------------+");
                        System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "DoctorID", "FirstName", "LastName", "Age", "spl", "Gender","JoinDate");

                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+");
                        do {
                            System.out.printf("\u001B[35m" + "| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", rs.getInt("did"), rs.getString("fname"), rs.getString("lname"), rs.getInt("age"), rs.getString("spl"), rs.getString("gen"), rs.getString("Joindate"));
                        } while (rs.next());
                        System.out.println("\u001B[0m" + "+------------+------------+------------+------------+------------+------------+------------+-");
                        // Process the query results
                    }




                    break;
                case 4:
                    System.out.println("\u001B[34m" + "Select DoctorID You Want To Update:- " + "\u001B[0m");
                    datashow();
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = p.intset();

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
                    System.out.print("Enter Your Choice You Want To Update:--\n" +
                            "          1.First Name\n" +
                            "          2.Last Name\n" +
                            "          3.Age Of Doctor\n" +
                            "          4.Gender Of Doctor\n" +
                            "          5.Doctor's Specialization\n" +
                            "          6.Exit\n");

                    int ch1 = p.chset(1,6);
                    switch (ch1){
                        case 1:
                            System.out.print("Enter The First Name You Want To Replace : ");
                            String rfname = p.nameset();
                            System.out.println(did);
                            p.updateRow("doctors","fname",rfname,did,"did");
                            break;
                        case 2:
                            System.out.print("Enter The Last Name You Want To Replace : ");
                            String rlname = p.nameset();
                            System.out.println(did);
                            p.updateRow("doctors","lname",rlname,did,"did");

                            break;
                        case 3:
                            System.out.print("Enter The Age You Want To Replace : ");
                            int  rage = p.ageset(25,75,"Doctors");
                            System.out.println(did);
                            p.updateRow1("doctors","age",rage,did,"did");
                            break;
                        case 4:
                            System.out.print("Enter Gender You Want To Replace : ");
                            String rgen = p.genset();
                            System.out.println(did);
                            p.updateRow("doctors","gen",rgen,did,"did");
                            break;
                        case 5:
                            System.out.print("Enter Specialization You Want To Replace : ");
                            String rspl = p.nameset();
                            System.out.println(did);
                            p.updateRow("doctors","spl",rspl,did,"did");
                            break;
                        case 6:
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Enter The DoctorID You Want To Delete From The Records");
                    datashow();
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
//                            System.out.println("Connected to MySQL database!");

                        boolean found = false;
                        int id;
                        do {

                            id = p.intset();

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM doctors WHERE did = " + id);

                            if (rs.next()) {
                                found = true;
                                String name = rs.getString("fname");
                                did1 = id;
                                System.out.println("ID: " + id + ", Name: " + name);
                            } else {
                                System.out.println("ID " + id + " not found. Please try again.");
                            }

                        } while (!found);

                    } catch (SQLException e) {
                        System.err.println("Failed to connect to MySQL database!");
                        e.printStackTrace();
                    }
                    String query1 = "UPDATE  patients SET did=? WHERE did=?";
                    System.out.println(did);
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
                         PreparedStatement stmt12 = connection.prepareStatement(query1)) {
                        stmt12.setNull(1, java.sql.Types.INTEGER);
                        stmt12.setInt(2, did1);
                        int rowsAffected = stmt12.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Update successful.");
                        } else {
                            System.out.println("Update failed. No rows affected.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error Occured");
                    }
                    String query2 = "UPDATE  emergency SET did=? WHERE did=?";
                    System.out.println(did);
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
                         PreparedStatement stmt121 = connection.prepareStatement(query2)) {
                        stmt121.setNull(1, java.sql.Types.INTEGER);
                        stmt121.setInt(2, did1);
                        int rowsAffected = stmt121.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Update successful.");
                        } else {
                            System.out.println("Update failed. No rows affected.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error Occured");
                    }
                    p.deleteRow(did1,"doctors","did");

                    break;
                case 6:
                    y = false;
                    break;
            }
        }
        //end of the show function
    }


   public  void datashow(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "");
            System.out.println("Connected to MySQL database!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM doctors");
            System.out.println("+------------+------------+------------+------------+------------+------------+------------+");
                        System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n", "Did", "FName","LName","Age","Spl","Gender","JoinDate");
                       System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+------------+");
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                System.out.printf("\u001B[35m"+"| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |%n",rs.getInt("did"),rs.getString("fname"),rs.getString("lname"),rs.getInt("age"),rs.getString("spl"),rs.getString("gen"),rs.getString("Joindate"));

            }
            System.out.println("\u001B[0m"+"+------------+------------+------------+------------+------------+------------+------------+");

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
