# CSE 241 Final Project-Paolo Bartolucci
## How to run
Upon unzip of folder the Interface can be initiated by running `java -jar xyz123.jar` or simply run `sh run.sh` within the directory. 

Once running the user will be prompted for their username and password to edgar1. When entered, access to the interface will be granted.

## Files within directory 

* DatabaseInterface.java
  * Java file containing code for interface
* DatabaseInterface.class
  * class file for DatabaseInterface
* DatabaseInterface.jar
  * Since deprecated Jar file but archived incase.
* RentalObject.java
  * Object used for grouping rental foreign keys when listing and data creation
* RentalObject.class
  * Class file for RentalObject
* Manifest.txt
  * Manifest file for JDBC
* ojdbc8.jar
  * Driver for Database access
* run.sh
  * Script used to quickly recompile and run interface for development.
* pab425.jar
  * Most current jar file, can be used to run most recent compilation of java files

## Commands in Interface
1. Management
   1. Move Car to different location
      1. Asks originating location
      2. Asks desired location
      3. Asks Which car is to be moved
   2. Create new Discount Group
      1. Asks for user to input 4 digit code to associate with group
      2. Asks for discount %
   3. View All Charges
      1. Asks which of the charges the user wants to see
      2. Shows specific instances of charges
      3. Asks which of these charges would the user want to see
      4. From there shows charged customer data
   4. Add Car to location
      1. Asks for car data
      2. Asks for desired Location
      3. Adds car
   5. print all customers
   6. print all rentals
   7. print all reservations
2. Employee
   1. create new customer
      1. Asks for all customer data
   2. create rental for customer
      1. If user is not exisitng customer asks for data
      2. Asks for all data related to rental
      3. Creates rental and adds car to rental list
   3. return car
      1. Asks which of the existing rentals would the employee like to return.
      2. Charges customer base charge for rental
      3. Asks if more charges are to be added
      4. If so, adds specific charges
3. Customer
   1. View avalible cars
      1. View all avalible cars not existing in rental or reservations by location
   2. Add to discount group
      1. Asks customer to input existing discount code to provide discount to
   3. Reserve car
      1. Similar to rental but user can move car to reservation table

## Thoughts related to Interface

### Complications with inital files
The most strenuous task of this project was creating the files using the preexisting commands. Although this was due to bugs of my own doing, it caused many hours of confusion. After recieving help my understanding of the java file and complilation process has increased 10 fold. 

### Helpfulness of ERD
Overall the implimention of this project was not terribly confusing. Having designed the ERD months prior, the mental image of how my interface would work made this an uncomplicated process. 

### Use of syntax
With that being said, I created my tables in way that enabled me to use simple SQL Querries. The only occasions I used pl/sql syntax was in the creation of tables and data. All of which was useful to test and dev with. Primarily the bulk of my code to display here will be very effective java code and few sql statements. 

With a reptitive input structure I used regex to validate all of my input
```java
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
```

Along with reptitive regex, I also used the process of selecting ids from lists of data constantly through this process. This validation was done through this.

```java
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
```

These methods in particular makes my code very efficient in listing and validating existing data. 

Most of my sql querries were `insert`,`select`, or `delete` statements based on input data. 

The most complicated of querries I do is 
```sql
select * 
from vehicle_data 
where location_id = %d and vehicle_id not in 
(select vehicle_id from rentals) 
and vehicle_id not in 
(select vehicle_id from reservations)
```
and 
```sql
    select *
    from reservations
    join customers on reservations.customer_id = customers.customer_id
    join locations on reservations.location_id = locations.location_id
    join vehicle_data on vehicle_data.vehicle_id = reservations.vehicle_id
```

Although join operations and nested subquerries are relatively trivial operations, it goes to show how effectively the db was designed. 

## References
The thing I referenced most through this process was the Oracle DB documentation:
https://docs.oracle.com/en/database/  



