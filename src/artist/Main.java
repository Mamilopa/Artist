package artist;

//Group: Anna, Shannie, Pauline

    

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {


    static Scanner sc = new Scanner(System.in);
    static Statement myStmt = null;
    static ResultSet myRs = null;

    public static void main(String[] args) throws SQLException, InterruptedException {

        Connection myConn;

        String dbUrl = "jdbc:mysql://localhost:3306/demo?useSSL=false";
        String user = "root";
        String pass = "lexicon";

        myConn = DriverManager.getConnection(dbUrl, user, pass);
        myStmt = myConn.createStatement();

        menu(myConn);

    }

    public static void menu(Connection myConn) throws SQLException, InterruptedException {

        System.out.println("**********MENU************");
        System.out.println("What would you like to do?");
        System.out.println("1. Add an artist.");
        System.out.println("2. Delete an artist.");
        System.out.println("3. Update an artist.");
        System.out.println("4. Show all artists.");
        System.out.println("5. Find an artist by ID.");
        System.out.println("6. Exit.\n");

        int inputMenu = sc.nextInt();

        switch (inputMenu) {
            case 1:
                addArtist(myConn);
                break;
            case 2:
                delete(myConn);
                break;
            case 3:
                updateArtist(myConn);
                break;
            case 4:
                show(myConn);
                break;
            case 5:
                findById(myConn);
                break;
            case 6:
                System.out.println("Thanks for using our database!");
                break;

            default:
                System.out.println("Please select another number!\n");
                menu(myConn);

        }
    }

    public static void addArtist(Connection myConn) throws SQLException, InterruptedException {
        sc.nextLine();
        System.out.println("Add firstname: ");
        String inputAdd1 = sc.nextLine();
        System.out.println("Add lastname: ");
        String inputAdd2 = sc.nextLine();
        System.out.println("Add age: ");
        int inputAdd3 = sc.nextInt();
        try (
                PreparedStatement p = myConn.prepareStatement("insert into artists "
                        + "(first_name, last_name, age)"
                        + "values"
                        + "(?,?,?)")) {
            p.setString(1, inputAdd1);
            p.setString(2, inputAdd2);
            p.setInt(3, inputAdd3);
            p.executeUpdate();
        }

        System.out.println("\nYou added a new artist!\n");
        System.out.println("You are now directed to the menu ... \n");
        TimeUnit.SECONDS.sleep(2);
        menu(myConn);

    }

    public static void delete(Connection myConn) throws SQLException, InterruptedException {
        show(myConn);
        System.out.println("Artists's table:");
        System.out.println("--------------------------");
        System.out.println("Enter the artist's id that should be deleted: ");
        int inputDelete1 = sc.nextInt();

        try (
                PreparedStatement p = myConn.prepareStatement("delete from artists where id= ? ")) {
            //System.out.println("You are deleting " + p.toString());
            p.setInt(1, inputDelete1);
            p.executeUpdate();
        }

        System.out.println("\nYou deleted successfully!\n");
        System.out.println("You are now directed to the menu ... \n");
        TimeUnit.SECONDS.sleep(2);
        menu(myConn);
    }

    public static void show(Connection myConn) throws SQLException, InterruptedException {
        System.out.println("Artists's table:");
        System.out.println("--------------------------");
        myRs = myStmt.executeQuery("select * from artists order by id");
        while (myRs.next()) {
            System.out.println(myRs.getInt("id") + ", " + myRs.getString("last_name") + ", " + myRs.getString("first_name") + ", " + myRs.getInt("age"));
        }
        System.out.println("\nArtist's information has been shown successfully!\n");
        System.out.println("You are now directed to the menu ... \n");
        TimeUnit.SECONDS.sleep(2);
        menu(myConn);

    }

    public static void updateArtist(Connection myConn) throws SQLException, InterruptedException {
        show(myConn);
        System.out.println("");

        System.out.println("Which field would you like to update?");
        System.out.println("1. Update firstname. ");
        System.out.println("2. Update lastname. ");
        System.out.println("3. Update age. ");
        System.out.println("4. I  want to update one more. ");
        System.out.println("5. Exit. ");
        int input = sc.nextInt();
        switch (input) {
            case 1:
                updateArtistFirstName(myConn);
                break;
            case 2:
                updateArtistLastName(myConn);
                break;
            case 3:
                updateArtistAge(myConn);
                break;
            case 4:
                updateArtist(myConn);

                break;
            case 5:
                menu(myConn);
                break;
            default:
                System.out.println("Please enter the correct information");
                updateArtist(myConn);

        }
    }

    public static void updateArtistFirstName(Connection myConn) throws SQLException, InterruptedException {

        System.out.println("");
        System.out.println("Which id would you like to update?");
        int inputId = sc.nextInt();
        System.out.println("Please enter first name:");
        sc.nextLine();
        String inputFirstName = sc.nextLine();

        try (
                PreparedStatement p = myConn.prepareStatement("update artists set first_name=? where id=? "
                )) {
                    p.setInt(2, inputId);
                    p.setString(1, inputFirstName);
                    p.executeUpdate();
                }

                System.out.println("\nYou updated successfully!\n");
                System.out.println("You are now directed to the menu ... \n");
                TimeUnit.SECONDS.sleep(2);
                menu(myConn);

    }

    public static void updateArtistLastName(Connection myConn) throws SQLException, InterruptedException {

        System.out.println("Which id would you like to update?");
        int inputId = sc.nextInt();
        System.out.println("Please enter last name:");
        sc.nextLine();
        String inputLastName = sc.nextLine();

        try (
                PreparedStatement p = myConn.prepareStatement("update artists set last_name=? where id=? "
                )) {
                    p.setInt(2, inputId);
                    p.setString(1, inputLastName);
                    p.executeUpdate();
                }

                System.out.println("\nYou updated successfully!\n");
                System.out.println("You are now directed to the menu ... \n");
                TimeUnit.SECONDS.sleep(2);
                menu(myConn);

    }

    public static void updateArtistAge(Connection myConn) throws SQLException, InterruptedException {

        System.out.println("Which id would you like to update?");
        int inputId = sc.nextInt();
        System.out.println("Please enter age:");
        sc.nextLine();
        int inputAge = sc.nextInt();

        try (
                PreparedStatement p = myConn.prepareStatement("update artists set age=? where id=? "
                )) {
                    p.setInt(2, inputId);
                    p.setInt(1, inputAge);
                    p.executeUpdate();
                }

                System.out.println("\nYou updated successfully!\n");
                System.out.println("You are now directed to the menu ... \n");
                TimeUnit.SECONDS.sleep(2);
                menu(myConn);

    }

    public static void findById(Connection myConn) throws SQLException {

        System.out.println("Enter the ID:");
        int inputId = sc.nextInt();

        try (
                PreparedStatement p = myConn.prepareStatement("select * from artists where id = ?"
                )) {
                    p.setInt(1, inputId);
                    ResultSet rs2 = p.executeQuery();

                    while (rs2.next()) {
                        int id = rs2.getInt("id");
                        String first_name = rs2.getString("first_name");
                        String last_name = rs2.getString("last_name");
                        int age = rs2.getInt("age");

                        System.out.printf("%s, %s, %s, %s\n", id, first_name, last_name, age);
                    }

                }
        System.out.println("\nThe Artist has been found successfully!\n");
        System.out.println("You are now directed to the menu ... \n");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            Logger.getLogger(Artist.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            menu(myConn);
        } catch (InterruptedException ex) {
            Logger.getLogger(Artist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
