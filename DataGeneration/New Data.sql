    insert into Customers (name,address,drivers_liscense) values ('Paolo Bartolucci','108 Johnson rd Bangor PA','73328551');
    insert into Customers (name,address,drivers_liscense) values ('Rosa Bartolucci','108 Johnson rd Bangor PA','44061398');
    insert into Customers (name,address,drivers_liscense) values ('Joe Bartolucci','108 Johnson rd Bangor PA','86276714');
    insert into Customers (name,address,drivers_liscense) values ('Lauren Harrison','110 Bangor Junction rd Bangor PA','89427788');

    insert into Discount  (discount.group_id,discount) values (1,'20%');
    insert into Discount  (discount.group_id,discount) values (2,'15%');
    
    insert into group_name (group_id,customer_id) values (1,(select customer_id from customers where name='Paolo Bartolucci'));
    insert into group_name (group_id,customer_id) values (2,(select customer_id from customers where name ='Rosa Bartolucci'));
    
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name='Paolo Bartolucci'),20,'day','11/14/2022','card');
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name='Paolo Bartolucci'),40,'day','11/14/2022','card');
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name ='Rosa Bartolucci'),20,'day','11/14/2022','card');
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name ='Rosa Bartolucci'),30,'day','11/14/2022','card');
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name ='Rosa Bartolucci'),50,'day','11/14/2022','card');
    insert into customer_charges (customer_id,charge_amount,charge_period,date_of_charge,payment_method) values ((select customer_id from customers where name ='Joe Bartolucci'),5,'day','11/14/2022','card');
    
    insert into locations(address) values ('4921 Cody Ridge Road Caunte OK');
    insert into locations(address) values ('3111 Stoney Lane Kaufman TX');
    insert into locations(address) values ('1087 Arlington Avenue Little Rock AR');
    
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='4921 Cody Ridge Road Caunte OK'),'subaru','crosstrek','crossover',10000);
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='4921 Cody Ridge Road Caunte OK'),'subaru','legacy','sedan',10000);
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='4921 Cody Ridge Road Caunte OK'),'subaru','outback','suv',20000);
    
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='3111 Stoney Lane Kaufman TX'),'chevrolet','malibu','sedan',10000);
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='3111 Stoney Lane Kaufman TX'),'chevrolet','malibu','sedan',100000);
    insert into vehicle_data(location_id,make,model,type,odometer) values ((select location_id from locations where address='3111 Stoney Lane Kaufman TX'),'chevrolet','trailblaizer','suv',0);
    commit;
    
