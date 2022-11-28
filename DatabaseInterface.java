import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.IllegalFormatWidthException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import javax.swing.JTable.PrintMode;
import javax.xml.namespace.QName;

public class DatabaseInterface {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        try {
            // boolean correctPasswordEntered = false;
            // Connection con = null;
            // while (!correctPasswordEntered) {

            // System.out.println("Please input your Oracle username on Edgar1:");
            // String user_name = in.nextLine();

            // System.out.println("Please input your Oracle password on Edgar1:");
            // // designed for inputting password without displaying the password:
            // Console console = System.console();
            // char[] pwd = console.readPassword();
            // try {
            // con =
            // DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",
            // user_name,
            // new String(pwd));
            // correctPasswordEntered = true;
            // } catch (Exception badLogin) {
            // System.out.println("Incorrect login, try again.");
            // continue;
            // }
            // }
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241",
                    "pab425", "PBartolucci2001*");

            boolean inInterface = true;
            Statement s = con.createStatement();
            while (inInterface) {

                PrintMenu();
                String interfaceChoice = RegexChecker(in, "[1-4]", "Please Enter a number from 1-4");
                switch (interfaceChoice) {
                    case "1":
                        ManagementMenu(in, s);
                        break;
                    case "2":
                        EmployeeMenu(in, s);
                        break;
                    case "3":
                        CustomerMenu(in, s);
                        break;
                    case "4":
                        inInterface = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error with the current application");
            System.out.println("Quitting Application");
        }
    }

    public static void PrintMenu() {
        System.out.println("Enter an Interface Option[1-4]:");
        System.out.println(" [1]: Management Interface");
        System.out.println(" [2]: Employee Interface");
        System.out.println(" [3]: Customer Interface");
        System.out.println(" [4]: Quit");
    }

    public static String RegexChecker(Scanner in, String regex, String returnStatement) {
        boolean enteredCorrectly = false;
        while (!enteredCorrectly) {
            String line = in.nextLine();
            if (line.matches(regex)) {
                return line;
            } else {
                System.out.println(returnStatement);
            }
        }
        return "";
    }

    public static int LoopChecker(Scanner in, String regex, String returnStatement, List list,
            String missingSelection) {
        boolean selecting = true;
        int choice = 0;
        while (selecting) {
            choice = Integer.parseInt(RegexChecker(in, regex, returnStatement));
            if (list.contains(choice))
                selecting = false;
            else
                System.out.println(missingSelection);
        }
        return choice;
    }

    public static void PrintManagementMenu() {
        System.out.println("\nEnter a Management Option[1-5]:");
        System.out.println(" [1]: Move Car To Different Location");
        System.out.println(" [2]: Create New Discount Group");
        System.out.println(" [3]: Add new Charge");
        System.out.println(" [4]: Add Car to Location");
        System.out.println(" [5]: Quit");
    }

    public static void PrintEmployeeMenu() {
        System.out.println("\nEnter an Employee Option[1-4]:");
        System.out.println(" [1]: Create New Customer");
        System.out.println(" [2]: Create Rental For Customer");
        System.out.println(" [3]: Return Car");
        System.out.println(" [4]: Quit");
    }

    public static void PrintCustomerMenu() {
        System.out.println("\nEnter an Customer Option[1-4]:");
        System.out.println(" [1]: Make Reservation");
        System.out.println(" [2]: View Avalibile Cars");
        System.out.println(" [3]: Add to discount group");
        System.out.println(" [4]: Quit");
    }

    public static void ManagementMenu(Scanner in, Statement s) {
        boolean inManagementMenu = true;
        while (inManagementMenu) {
            PrintManagementMenu();
            String line = RegexChecker(in, "[1-5]", "Please enter a number between 1-5");
            switch (line) {
                case "1":
                    System.out.println("Printing list of locations...");
                    List ids = listLocations(in, s);

                    System.out.println(
                            "\n Please enter the location id the car is currently");

                    int id = LoopChecker(in, "[0-9]+", "Please enter a number", ids,
                            "Please enter one of the ids from the list");

                    System.out.println("\nPlease enter the location id of destination location");
                    boolean enteredLocationCorrectly = true;
                    int secondId = 0;
                    while (enteredLocationCorrectly) {
                        secondId = Integer.parseInt(RegexChecker(in, "[0-9]+", "Please enter a number"));
                        if (ids.contains(secondId) && secondId != id)
                            enteredLocationCorrectly = false;
                        else
                            System.out.println("Please enter one of the ids in the list.");
                    }

                    System.out.println("\n Please enter the id of the car you would like to move");
                    List carIds = listCars(in, s, id);
                    int carId = LoopChecker(in, "[0-9]+", "Please enter a number", carIds,
                            "Please enter an id from the list of cars");
                    moveCar(s, carId, secondId);
                    break;
                case "2":
                    System.out.println("Please enter the name of the new Discount Group");
                    createDiscountGroup(in, s);
                    break;
                case "3":
                    System.out.println("Printing list of charges...");
                    viewAllCharges(in, s);
                    break;
                case "4":
                    System.out.println("Printing list of locations...");
                    addCarToLocation(in, s);
                    break;
                case "5":
                    inManagementMenu = false;
                    break;
            }
        }
    }

    public static void EmployeeMenu(Scanner in, Statement s) {
        boolean inMenu = true;
        while (inMenu) {
            PrintEmployeeMenu();
            String line = RegexChecker(in, "[1-4]", "Please enter a number between 1-4");
            switch (line) {
                case "1":
                    addCustomer(in, s);
                    break;
                case "2":
                    // make rental
                    break;
                case "3":
                    // return car
                    break;
                case "4":
                    inMenu = false;
                    break;
            }
        }
    }

    public static void CustomerMenu(Scanner in, Statement s) {
        boolean inMenu = true;
        while (inMenu) {
            PrintCustomerMenu();
            String line = RegexChecker(in, "[1-4]", "Please enter a number between 1-4");
            switch (line) {
                case "1":
                    // make reservation
                    break;
                case "2":
                    // view avalible cars
                    break;
                case "3":
                    // get discount
                    break;
                case "4":
                    inMenu = false;
                    break;
            }
        }
    }

    public static List listLocations(Scanner in, Statement s) {
        try {
            String q;
            ResultSet locations;

            locations = s.executeQuery("select * from locations");
            if (!locations.next()) {
                System.out.println("There are currently no locations registered");
                return new ArrayList<>();
            }
            System.out.println("ID\tAddress");
            List ids = new ArrayList<>();
            do {
                ids.add(locations.getInt("location_id"));
                System.out.println(locations.getInt("location_id") + "\t" + locations.getString("address"));
            } while (locations.next());
            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with getting data for locations.");
        }
        return new ArrayList<>();
    }

    public static List listCars(Scanner in, Statement s, int location_id) {
        try {
            String q = String.format("select * from vehicle_data where location_id = %d", location_id);
            ResultSet cars;

            System.out.println(q);
            cars = s.executeQuery(q);
            if (!cars.next()) {
                System.out.println("There are currently no vehicles registered");
                return new ArrayList<>();
            }
            System.out
                    .println(String.format("%-10s %-15s %-15s %-15s %-10s", "id", "make", "model", "type", "odometer"));
            List ids = new ArrayList<>();
            do {
                ids.add(cars.getInt("vehicle_id"));
                System.out.println(String.format("%-10s %-15s %-15s %-15s %-10s", cars.getString("vehicle_id"),
                        cars.getString("make"),
                        cars.getString("model"), cars.getString("type"), cars.getString("odometer")));
            } while (cars.next());
            return ids;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with getting data for locations.");
        }
        return new ArrayList<>();
    }

    public static List listCustomers(Scanner in, Statement s) {
        try {
            List customerIds = new ArrayList<>();
            String q = "select * from customers";
            ResultSet customers = s.executeQuery(q);
            if (!customers.next()) {
                System.out.println("Currently no Customers Exist");
            } else {
                System.out.println(String.format("%-10s%-20s%-30s%-8s", "id", "name", "Address", "Liscense ID"));

                do {
                    customerIds.add(customers.getInt("customer_id"));
                    System.out.println(String.format("%-10s%-20s%-30s%-8s", customers.getInt("customer_id"),
                            customers.getString("name"), customers.getString("address"),
                            customers.getString("drivers_liscense")));
                } while (customers.next());
                return customerIds;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem with getting all customers");
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public static void moveCar(Statement s, int vehicle_id, int secondLocation) {
        String q = String.format("update vehicle_data set location_id = %d where vehicle_id= %d", secondLocation,
                vehicle_id);

        try {
            int result = s.executeUpdate(q);
        } catch (Exception e) {
            System.out.println("unable to update car location");
        }
    }

    public static void createDiscountGroup(Scanner in, Statement s) {
        List ids = new ArrayList<>();
        try {
            ResultSet results = s.executeQuery("select group_id from discount");
            if (results.next()) {
                do {
                    ids.add(results.getInt("group_id"));
                } while (results.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with getting current discounts");
            return;
        }

        System.out.println("Please enter a 4 digit id to denote group");

        boolean gettingNumber = true;
        while (gettingNumber) {
            int groupId = Integer.parseInt(RegexChecker(in, "^[0-9]{4}$", "Please enter a 4 digit number"));
            if (ids.contains(groupId))
                System.out.println("Please enter another group_id, this one currently exists");
            else
                gettingNumber = false;
        }

        int groupId = Integer.parseInt(RegexChecker(in, "^[0-9]{4}$", "Please enter a 4 digit number"));
        System.out.println("Please enter a number between 1-100 to denote the % discount assoicated with this group");
        String discount = RegexChecker(in, "^[1-9][0-9]?$|^100$", "Please enter a number between 1-100");
        discount += '%';
        String q = String.format("insert into discounts (group_id,discount) values (%d,\'%s\')", groupId, discount);
        try {
            s.executeQuery(q);
            System.out.println("Discount Added");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with adding discount");
            return;
        }
    }

    public static void viewAllCharges(Scanner in, Statement s) {
        try {
            List customerCharges = new ArrayList<>();

            boolean viewingCharges = true;
            int viewingOption = 0;
            while (viewingCharges) {

                System.out.println(" [1]: Fuel Charges");
                System.out.println(" [2]: Location Charges");
                System.out.println(" [3]: Insurance Charges");
                System.out.println(" [4]: Other Charges");
                System.out.println(" [5]: Quit");

                viewingOption = Integer.parseInt(RegexChecker(in, "[1-5]", "Please enter a number between 1-5"));
                if (viewingOption == 5)
                    viewingCharges = false;
                else {
                    List ChargeIds = printCharge(s, viewingOption);
                    boolean selectingCharge = true;
                    int chargeIdChoice = LoopChecker(in, "[0-9]+", "Please enter a number", ChargeIds,
                            "Please enter a number from the list of charges");

                    String q = String.format(
                            "select * from customer_charges natual join customers where charge_id = %d",
                            chargeIdChoice);
                    ResultSet userCharges = s.executeQuery(q);
                    if (!userCharges.next()) {
                        System.out.println("No Charges were found at that id");
                        return;
                    } else {
                        System.out.println(String.format("%-20s%-5s%-20s", "Name", "Charge Amount", "Date of Charge"));
                        do {
                            System.out.println(
                                    String.format("%-20s%-5d%-20s", userCharges.getString("name"),
                                            userCharges.getInt("charge_amount"),
                                            userCharges.getString("Date_of_charge")));
                        } while (selectingCharge);
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error with printing all charges");
            return;
        }
        return;
    }

    public static List printCharge(Statement s, int choice) {
        String tableName = "";
        String chargeName = "";
        switch (choice) {
            case 1:
                tableName = "fuel_charge";
                chargeName = "price_per_gallon";
                break;
            case 2:
                tableName = "Location_charge";
                chargeName = "price_per_mile";
                break;
            case 3:
                tableName = "Insurance_Charge";
                chargeName = "price_per_period";
                break;
            case 4:
                tableName = "other_charges";
                chargeName = "price_of_charge";
                break;
        }

        try {
            String q = String.format("select * from %s)", tableName);
            ResultSet charges = s.executeQuery(q);
            if (!charges.next()) {
                System.out.println("There are no Charges of this type");
                return new ArrayList<>();
            } else {
                List chargeIds = new ArrayList<>();
                System.out.println(String.format("%-10s%-10s", "Charge_id", chargeName));
                do {
                    chargeIds.add(charges.getInt("charge_id"));
                    System.out.println(
                            String.format("%-10d%-10d", charges.getInt("charge_id"), charges.getInt(chargeName)));
                } while (charges.next());
                return chargeIds;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with printing the charges");
            return new ArrayList<>();
        }
    }

    public static void addCarToLocation(Scanner in, Statement s) {
        System.out.println("Please Enter the id of the location to add a car to");
        List locationIDs = listLocations(in, s);
        int locationId = LoopChecker(in, "[0-9]+", "Please enter a number", locationIDs,
                "Please enter an Id from the list of locations");
        System.out.println("Please enter the name of the car's make");
        String make = RegexChecker(in, "[a-zA-Z]+", "Please enter the name of the car's make");
        System.out.println("Please enter the name of the car's model");
        String model = RegexChecker(in, "[a-zA-Z]+", "Please enter the name of the car's model");
        System.out.println("Please enter the name of the car's type");
        String type = RegexChecker(in, "[a-zA-Z]+", "Please enter the name of the car's type");
        System.out.println("Please enter the number of miles on the car");
        String odometer = RegexChecker(in, "[0-9]+", "Please enter the number of miles on the car");

        try {
            String q = String.format("insert into vehicle_Data (%d,\'%s\',\'%s\',\'%s\',\'%s\')", locationId, make,
                    model, type, odometer);
            s.executeQuery(q);
            System.out.println("Car successfully added.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with Adding a car to a location");
            return;
        }

    }

    public static void addCustomer(Scanner in, Statement s) {
        System.out.println("Please enter the name of the customer");
        String name = RegexChecker(in, "[a-zA-Z]+", "Please enter a name as a String of letters only.");
        System.out.println("Please enter the house number");
        String houseNumber = RegexChecker(in, "[0-9]+", "Please enter a number");
        System.out.println("Please enter the street");
        String street = RegexChecker(in, "[a-zA-Z ]+", "Please enter the Street in words only");
        System.out.println("Please enter the city");
        String city = RegexChecker(in, "[a-zA-Z ]+", "Please enter the city in words only");
        System.out.println("Please enter the State in 2 letters");
        String state = RegexChecker(in, "^[A-Z}{2}$", "Please enter the State in 2 capital letters");

        String address = houseNumber + " " + street + " " + city + " " + state;
        System.out.println("Please enter the drivers 8 digit liscense number");
        String liscenseNumber = RegexChecker(in, "^[0-9]{8}$", "Please enter an 8 digit number");

        try {
            String q = String.format("insert into customers values (\'%s\',\'%s\',\'%s\')", name, address,
                    liscenseNumber);
            s.executeQuery(q);
            System.out.println("New Customer Successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with adding this new customer");
        }
    }
}
