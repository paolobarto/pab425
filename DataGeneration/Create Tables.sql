create table Customers(
    customer_id int generated always as Identity(Start with 1 increment by 1),
    name varchar(50),
    address varchar(50),
    drivers_liscense varchar(50),
    primary key(customer_id)
);

create table Discount(
    group_id int not null,
    discount varchar(50),
    primary key(group_id)
);

create table Group_Name(
    group_id int not null,
    customer_id int not null,
    primary key(group_id,customer_id),
    
    FOREIGN key (group_id)
        references Discount(group_id),
    FOREIGN key (customer_id)
        references Customers(customer_id)
);

create table Customer_Charges(
    id int generated always as Identity(Start with 1 increment by 1),
    customer_id int not null,
    charge_amount int,
    charge_period varchar(50),
    date_of_charge varchar(50),
    payment_method varchar(50),
    
    primary key(id)
);

create table Fuel_charge(
    charge_id int not null,
    price_per_gallon int,
    
    primary key(charge_id),
    
    foreign key(charge_id)
        references Customer_Charges(id)
);

create table Location_charge(
    charge_id int not null,
    price_per_mile int,
    
    primary key(charge_id),
    
    foreign key(charge_id)
        references Customer_Charges(id)
);

create table Insurance_charge(
    charge_id int not null,
    price_per_period int,
    
    primary key(charge_id),
    
    foreign key(charge_id)
        references Customer_Charges(id)
);

create table Other_charge(
    charge_id int not null,
    price_of_charge int,
    primary key(charge_id),
    
    foreign key(charge_id)
        references Customer_Charges(id)
);


create table Locations(
    location_id int generated always as Identity(Start with 1 increment by 1),
    address varchar(50),
    
    primary key(location_id)
);

create table Vehicle_Data(
    vehicle_id int generated always as Identity(Start with 1 increment by 1),
    location_id int not null,
    make varchar(50),
    model varchar(50),
    type varchar(50),
    odometer int,
    
    primary key(vehicle_id,location_id),
    
    foreign key(location_id)
        references Locations(location_id)
);

create table Rentals(
    customer_id int not null,
    vehicle_id int not null,
    location_id int not null,
    rental_Start_Date varchar(50),
    rental_end_Date varchar(50),
    
    primary key(customer_id,vehicle_id,location_id),
    
    foreign key(customer_id)
        references Customers(customer_id),
    foreign key(vehicle_id,location_id)
        references Vehicle_Data(vehicle_id,location_id)
);

create table Reservations(
    customer_id int not null,
    vehicle_id int not null,
    location_id int not null,
    reservation_date varchar(50),
    
    primary key(customer_id,vehicle_id,location_id),
    
    foreign key(customer_id)
        references Customers(customer_id),
    foreign key(vehicle_id,location_id)
        references Vehicle_Data(vehicle_id,location_id)
);