package hospitalManagent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospital {
    public static void hospital() throws SQLException, IOException {
        System.out.println("\u001B[32m" + "----------------------------------------------------------------------\n" +
                "|                                                                    |\n" +
                "|           Welcome To Hospital Management System                    |\n" +
                "|                                                                    |\n" +
                "----------------------------------------------------------------------\n" + "\u001B[0m");
        boolean y = true;
        while (y) {
            System.out.println("\u001B[36m" + "Please Select The Fields You Want To Work Below:-");
            System.out.println("        1.Patient\n" +
                    "        2.Doctors\n" +
                    "        3.Nurses\n" +
                    "        4.Employee\n" +
                    "        5.Emergency\n" +
                    "        6.Exit" + "\u001B[0m");

            Scanner Sin = new Scanner(System.in);

            boolean x = true;
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
                    System.out.println("\u001B[33m" + "Patient Related Work Is Here" + "\u001B[0m");
                    Patients p = new Patients();
//                    p.nameset();
                    p.show();

                    break;
                case 2:
                    System.out.println("\u001B[33m" + "Doctor's Work Is Here" + "\u001B[0m");
                    Doctors d = new Doctors();
                    d.show();

                    break;
                case 3:
                    Nurses n = new Nurses();
                    n.show();
                    System.out.println("\u001B[33m" + "Nurses Work Is Here" + "\u001B[0m");

                    break;
                case 4:
                    System.out.println("\u001B[33m" + "Employee Work Is Here" + "\u001B[0m");
                    Employes e = new Employes();
                    e.show();

                    break;
                case 5:
                    System.out.println("\u001B[33m" + "Emergency Work Is Here" + "\u001B[0m");
                    Emergency e1 = new Emergency();
                    e1.show();

                    break;
                case 6:
                    System.out.println("\u001B[31m" + "Have A Nice Day......." + "\u001B[0m");
                    System.exit(0);
                    break;
            }
            //End Of your programme
        }
    }
}
