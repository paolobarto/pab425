import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("\n\nEnter a Management Option[1-8]:");
        System.out.println(" [1]: Move Car To Different Location");
        System.out.println(" [2]: Create New Discount Group");
        System.out.println(" [3]: View all Charges");
        System.out.println(" [4]: Add Car to Location");
        System.out.println(" [5]: Print All Customers");
        System.out.println(" [6]: Print all Rentals");
        System.out.println(" [7]: Print all Reservations");
        System.out.println(" [8]: Quit");
    }

    public static void PrintEmployeeMenu() {
        System.out.println("\n\nEnter an Employee Option[1-4]:");
        System.out.println(" [1]: Create New Customer");
        System.out.println(" [2]: Create Rental For Customer");
        System.out.println(" [3]: Return Car");
        System.out.println(" [4]: Quit");
    }

    public static void PrintCustomerMenu() {
        System.out.println("\n\nEnter an Customer Option[1-4]:");
        System.out.println(" [1]: View Avalibile Cars");
        System.out.println(" [2]: Add to discount group");
        System.out.println(" [3]: Reserve Car");
        System.out.println(" [4]: Quit");
    }

    public static void ManagementMenu(Scanner in, Statement s) {
        boolean inManagementMenu = true;
        while (inManagementMenu) {
            PrintManagementMenu();
            String line = RegexChecker(in, "[1-8]", "Please enter a number between 1-8");
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

                    List carIds = listCars(in, s, id);
                    if (carIds.size() == 0) {
                        return;
                    }

                    System.out.println("\n Please enter the id of the car you would like to move");
                    int carId = LoopChecker(in, "[0-9]+", "Please enter a number", carIds,
                            "Please enter an id from the list of cars");
                    moveCar(s, carId, secondId);
                    break;
                case "2":
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
                    listCustomers(in, s);
                    break;
                case "6":
                    listRentals(in, s);
                    break;
                case "7":
                    listReservations(in, s);
                    break;
                case "8":
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
                    createRental(in, s);
                    break;
                case "3":
                    returnRental(in, s);
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
                    printCars(in, s);
                    // view availible cars
                    break;
                case "2":
                    addToDiscountGroup(in, s);
                    // associate with group
                    break;
                case "3":
                    makeReservation(in, s);
                    // reserve car
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
            String q = String.format(
                    "select * from vehicle_data where location_id = %d and vehicle_id not in (select vehicle_id from rentals) and vehicle_id not in (select vehicle_id from reservations)",
                    location_id);
            ResultSet cars;

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
                System.out.println(String.format("%-10s%-20s%-40s%-8s", "id", "name", "Address", "Liscense ID"));

                do {
                    customerIds.add(customers.getInt("customer_id"));
                    System.out.println(String.format("%-10s%-20s%-40s%-8s", customers.getInt("customer_id"),
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

    public static List listRentals(Scanner in, Statement s) {
        try {
            String q = "select *"
                    + " from rentals"
                    + " join customers on rentals.customer_id = customers.customer_id"
                    + " join locations on rentals.location_id = locations.location_id"
                    + " join vehicle_data on vehicle_data.vehicle_id = rentals.vehicle_id";
            ResultSet rentals = s.executeQuery(q);
            if (!rentals.next()) {
                System.out.println("There are currently no rentals");
                return new ArrayList<>();
            }

            System.out.println(
                    String.format("%-5s%-20s%-40s%-20s%-20s%-12s%-12s", "id", "Name", "Address", "Make", "Model",
                            "Start",
                            "End"));
            List<RentalObject> rentalList = new ArrayList<>();
            int index = 0;
            do {
                System.out.println(String.format("%-5d%-20s%-40s%-20s%-20s%-12s%-12s", index, rentals.getString("name"),
                        rentals.getString("address"), rentals.getString("make"), rentals.getString("model"),
                        rentals.getString("rental_start_date"), rentals.getString("rental_end_date")));

                rentalList.add(new RentalObject(index++, rentals.getInt("customer_id"), rentals.getInt("location_id"),
                        rentals.getInt("vehicle_id")));
            } while (rentals.next());
            return rentalList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with listing all current rentals");
            return new ArrayList<>();
        }
    }

    public static List listReservations(Scanner in, Statement s) {
        try {
            String q = "select *"
                    + " from reservations"
                    + " join customers on reservations.customer_id = customers.customer_id"
                    + " join locations on reservations.location_id = locations.location_id"
                    + " join vehicle_data on vehicle_data.vehicle_id = reservations.vehicle_id";
            ResultSet reservations = s.executeQuery(q);
            if (!reservations.next()) {
                System.out.println("There are currently no reservations");
                return new ArrayList<>();
            }

            System.out.println(
                    String.format("%-5s%-20s%-40s%-20s%-20s%-12s", "id", "Name", "Address", "Make", "Model",
                            "Start"));
            List<RentalObject> reservationList = new ArrayList<>();
            int index = 0;
            do {
                System.out.println(String.format("%-5d%-20s%-40s%-20s%-20s%-12s", index,
                        reservations.getString("name"),
                        reservations.getString("address"), reservations.getString("make"),
                        reservations.getString("model"),
                        reservations.getString("reservation_date")));

                reservationList.add(new RentalObject(index++, reservations.getInt("customer_id"),
                        reservations.getInt("location_id"),
                        reservations.getInt("vehicle_id")));
            } while (reservations.next());
            return reservationList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with listing all current reservations");
            return new ArrayList<>();
        }
    }

    public static void moveCar(Statement s, int vehicle_id, int secondLocation) {
        String q = String.format("update vehicle_data set location_id = %d where vehicle_id= %d", secondLocation,
                vehicle_id);

        try {
            int result = s.executeUpdate(q);
            System.out.println("\nCar Moved!");
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
        int groupId = 0;
        while (gettingNumber) {
            groupId = Integer.parseInt(RegexChecker(in, "^[0-9]{4}$", "Please enter a 4 digit number"));
            if (ids.contains(groupId))
                System.out.println("Please enter another group_id, this one currently exists");
            else
                gettingNumber = false;
        }
        // int groupId = Integer.parseInt(RegexChecker(in, "^[0-9]{4}$", "Please enter a
        // 4 digit number"));
        System.out.println("Please enter a number between 1-100 to denote the % discount assoicated with this group");
        String discount = RegexChecker(in, "^[1-9][0-9]?$|^100$", "Please enter a number between 1-100");
        discount += '%';
        String q = String.format("insert into discount (group_id,discount) values (%d,\'%s\')", groupId, discount);
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
                    if (ChargeIds.size() == 0)
                        continue;
                    boolean selectingCharge = true;
                    System.out.println("Please enter the an id from any of the chargeIds");
                    int chargeIdChoice = LoopChecker(in, "[0-9]+", "Please enter a number", ChargeIds,
                            "Please enter a number from the list of charges");

                    String q = String.format(
                            "select * from customer_charges natural join customers where id = %d",
                            chargeIdChoice);
                    ResultSet userCharges = s.executeQuery(q);
                    if (!userCharges.next()) {
                        System.out.println("No Charges were found at that id");
                        return;
                    } else {
                        System.out.println(String.format("%-30s%-15s%-20s", "Name", "Charge Amount", "Date of Charge"));
                        do {
                            System.out.println(
                                    String.format("%-30s%-15d%-20s", userCharges.getString("name"),
                                            userCharges.getInt("charge_amount"),
                                            userCharges.getString("Date_of_charge")));
                        } while (userCharges.next());
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
                tableName = "other_charge";
                chargeName = "price_of_charge";
                break;
        }

        try {
            String q = String.format("select * from %s", tableName);
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
        List locationIDs = listLocations(in, s);
        System.out.println("Please Enter the id of the location to add a car to");
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
            String q = String.format(
                    "insert into vehicle_Data(location_id,make,model,type,odometer) values (%d,\'%s\',\'%s\',\'%s\',\'%s\')",
                    locationId,
                    make,
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
        try {
            System.out.println("Please enter the name of the customer");
            String name = RegexChecker(in, "[a-zA-Z ]+", "Please enter a name as a String of letters only.");
            System.out.println("Please enter the house number");
            String houseNumber = RegexChecker(in, "[0-9]+", "Please enter a number");
            System.out.println("Please enter the street");
            String street = RegexChecker(in, "[a-zA-Z ]+", "Please enter the Street in words only");
            System.out.println("Please enter the city");
            String city = RegexChecker(in, "[a-zA-Z ]+", "Please enter the city in words only");
            System.out.println("Please enter the State in 2 letters");
            String state = RegexChecker(in, "^[A-Z]{2}$", "Please enter the State in 2 capital letters");

            String address = houseNumber + " " + street + " " + city + " " + state;
            System.out.println("Please enter the drivers 8 digit liscense number");
            String liscenseNumber = RegexChecker(in, "^[0-9]{8}$", "Please enter an 8 digit number");

            String q = String.format(
                    "insert into customers(name,address,drivers_liscense) values (\'%s\',\'%s\',\'%s\')", name,
                    address,
                    liscenseNumber);
            s.executeQuery(q);
            System.out.println("New Customer Successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error with adding this new customer");
        }
    }

    public static void createRental(Scanner in, Statement s) {
        System.out.println("Is the customer a returning customer? (y or n)");
        String returingCustomer = RegexChecker(in, "^[yn]{1}$", "Please enter y or n");

        if (returingCustomer.equals("n"))
            addCustomer(in, s);

        List customerIds = listCustomers(in, s);

        System.out.println("Please enter the id of the returing user");
        int customerId = LoopChecker(in, "[0-9]+", "Please enter a number", customerIds,
                "Please enter an Id from the list of Customers");

        boolean selectingLocations = true;
        int locationId = 0;
        List vehicleIds = new ArrayList<>();
        while (selectingLocations) {
            List locationIds = listLocations(in, s);
            System.out.println("Please enter the id of the starting location");
            locationId = LoopChecker(in, "[0-9]+", "Please enter a number", locationIds,
                    "Please enter a number from the list of locations");
            vehicleIds = listCars(in, s, locationId);
            if (vehicleIds.size() == 0)
                System.out.print("No cars are present at this location, please select another");
            else
                selectingLocations = false;
        }

        System.out.println("Please enter the id of the desired vehicle");
        int vehicleId = LoopChecker(in, "[0-9]+", "Please enter a number", vehicleIds,
                "Please enter a number from the list of vehicle ids");

        System.out.println("Please enter the starting date of the rental in the format dd/mm/yy");
        String startDate = RegexChecker(in, "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$",
                "Please enter the date in the format of dd/mm/yy");

        System.out.println("Please enter the ending date of the rental in the format dd/mm/yy");
        String endDate = RegexChecker(in, "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$",
                "Please enter the date in the format of dd/mm/yy");

        try {
            String q = String.format("insert into rentals values (%d,%d,%d,\'%s\',\'%s\')", customerId,
                    vehicleId, locationId, startDate, endDate);
            s.executeQuery(q);
            System.out.println("Rental Added");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an issue with creating the rental");
        }

    }

    public static void returnRental(Scanner in, Statement s) {
        List<RentalObject> rentals = listRentals(in, s);
        System.out.println("Please Enter the id of the rental you would like to return");
        if (rentals.size() == 0) {
            System.out.println("There are currently no Rentals!");
            return;
        }

        boolean choosingRental = true;
        int rentalId = 0;
        RentalObject rental = new RentalObject();
        while (choosingRental) {
            rentalId = Integer.parseInt(RegexChecker(in, "[0-9]+", "Please enter a number"));
            for (RentalObject rentalObject : rentals) {
                if (rentalObject.id == rentalId) {
                    choosingRental = false;
                    rental = rentalObject;
                }
            }
            if (choosingRental)
                System.out.println("Please enter an Id from the list of rentals");
        }

        System.out.println("Please enter the id of the location the car is returned to");
        int newLocationId = LoopChecker(in, "[0-9]+", "Please enter a number", listLocations(in, s),
                "Please select an id from the list");

        try {
            String q = String.format(
                    "delete from rentals where customer_id=%d and location_id = %d and vehicle_id = %d",
                    rental.customer_id, rental.location_id, rental.vehicle_id);
            s.execute(q);
            q = String.format("update vehicle_data set location_id = %d where vehicle_id = %d", newLocationId,
                    rental.vehicle_id);
            s.execute(q);
            System.out.println("Rental Returned!");

            System.out.println("What is the total price of just the car rental");
            int price = Integer.parseInt(RegexChecker(in, "[0-9]+", "Please enter a number"));
            System.out.println("How is the customer paying today? (card, cash, check)");
            String payMethod = RegexChecker(in, "card|cash|check", "Please only enter the values (card cash check)");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            q = String.format(
                    "insert into customer_charges(customer_id,charge_amount,charge_period,date_of_charge,payment_method) values(%d,%d,1,\'%s\',\'%s\')",
                    rental.customer_id, price, date, payMethod);
            s.execute(q);
            System.out.println("Added Charge to record.");

            System.out.println("Would you like to add additional charges? (y or n)");

            if (RegexChecker(in, "y|n", "Please enter y or n only").equals("n"))
                return;

            boolean addingCharge = true;
            while (addingCharge) {
                System.out.println(" [1]: Fuel Charges");
                System.out.println(" [2]: Location Charges");
                System.out.println(" [3]: Insurance Charges");
                System.out.println(" [4]: Other Charges");
                System.out.println(" [5]: Quit");

                int chargeChoice = Integer.parseInt(RegexChecker(in, "[1-5]", "Please enter a number between 1-5"));

                String tableName = "";
                String chargeName = "";
                switch (chargeChoice) {
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
                        tableName = "other_charge";
                        chargeName = "price_of_charge";
                        break;
                    case 5:
                        addingCharge = false;
                        return;
                }

                System.out.println("What was the " + chargeName + "?");
                int chargeAmount = Integer.parseInt(RegexChecker(in, "[0-9]+", "Please enter a number"));
                q = String.format(
                        "insert into customer_charges(customer_id,charge_amount,charge_period,date_of_charge,payment_method) values(%d,%d,1,\'%s\',\'%s\')",
                        rental.customer_id, chargeAmount, date, payMethod);
                s.execute(q);

                q = String.format(
                        "select max(id) as charge_id from customer_charges where customer_id = %d and charge_amount = %d and"
                                + " date_of_charge = \'%s\' and payment_method=\'%s\'",
                        rental.customer_id, chargeAmount, date, payMethod);

                ResultSet chargeResult = s.executeQuery(q);
                if (!chargeResult.next()) {
                    System.out.println("There is no charge added");
                    return;
                }

                int chargeId = chargeResult.getInt("charge_id");

                q = String.format("insert into %s values(%d,%d)", tableName, chargeId, chargeAmount);
                s.execute(q);
                System.out.println("Charge Added!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error returning the rental");
        }
    }

    public static void printCars(Scanner in, Statement s) {
        System.out.println("Please enter the id of the Desired Location");
        int locationId = LoopChecker(in, "[0-9]+", "Please enter a number", listLocations(in, s),
                "Please enter an id from an existing location");

        listCars(in, s, locationId);
    }

    public static void addToDiscountGroup(Scanner in, Statement s) {
        System.out.println("Are you a pre-existing member? (y or n)");
        if (RegexChecker(in, "y|n", "Please enter (y or n)").equals("n"))
            addCustomer(in, s);

        System.out.println("Please enter your own id from the list");
        int customerId = LoopChecker(in, "[0-9]+", "Please enter a number", listCustomers(in, s),
                "Please select an id from the list of customers");

        try {
            String q = "select group_id from discount";
            ResultSet discounts = s.executeQuery(q);
            List discountList = new ArrayList<>();
            while (discounts.next()) {
                discountList.add(discounts.getInt("group_id"));
            }

            System.out.println("Please enter a pre-existing 4-digit dicount code");
            int code = Integer.parseInt(RegexChecker(in, "^[0-9]{4}$", "Please enter a 4 digit number"));

            if (!discountList.contains(code)) {
                System.out.println("Sorry this discount code does not exist. Try again another time,");
                return;
            }

            q = String.format("insert into group_name values (%d,%d)", code, customerId);
            s.execute(q);
            System.out.println("Discount Added to profile!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error retrieving current discounts");
        }
    }

    public static void makeReservation(Scanner in, Statement s) {
        System.out.println("Are you a pre-existing member? (y or n)");
        if (RegexChecker(in, "y|n", "Please enter (y or n)").equals("n"))
            addCustomer(in, s);

        System.out.println("Please enter your own id from the list");
        int customerId = LoopChecker(in, "[0-9]+", "Please enter a number", listCustomers(in, s),
                "Please select an id from the list of customers");

        System.out.println("Please enter the id of the Desired Location");
        int locationId = LoopChecker(in, "[0-9]+", "Please enter a number", listLocations(in, s),
                "Please enter an id from an existing location");
        System.out.println("Please enter the id of your desired car");
        int vehicle_id = LoopChecker(in, "[0-9]+", "Please enter a number", listCars(in, s, locationId),
                "Please enter an Id from the list of cars");

        System.out.println("Please enter the date of the desired reservation (dd/mm/yy)");
        String date = RegexChecker(in, "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$",
                "Please enter the date in the format of dd/mm/yy");

        try {
            String q = String.format("insert into reservations values (%d,%d,%d,\'%s\')", customerId, vehicle_id,
                    locationId,
                    date);
            s.execute(q);
            System.out.println("Reservation Added!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem with adding a reservation");
        }
    }
}