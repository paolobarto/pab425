public class RentalObject {
    public int id;
    public int customer_id;
    public int location_id;
    public int vehicle_id;

    RentalObject(int id, int customer_id, int location_id, int vehicle_id) {
        this.id = id;
        this.customer_id = customer_id;
        this.location_id = location_id;
        this.vehicle_id = vehicle_id;
    }

    RentalObject() {
        id = 0;
        customer_id = 0;
        location_id = 0;
        vehicle_id = 0;
    }
}
