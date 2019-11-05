package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Defines a booking: the customer who made it, the total amount, the order
 * date, and the number of booked seats.
 *
 * @author Jean-Michel Busca
 */
public class Booking {

    private final String customer;
    private final float amount;
    private final Date date;
    private final List<Seat> seats;

    public Booking(String customer, float amount, Date date, List<Seat> seats) {
        this.customer = customer;
        this.amount = amount;
        this.date = date;
        this.seats = new ArrayList<Seat>(seats);
    }

    @Override
    public String toString() {
        return "BookingInfo{" + "customer=" + customer + ", amount=" + amount + ", date=" + date + ", seats=" + seats + '}';
    }

    public String getCustomer() {
        return customer;
    }

    public float getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public List<Seat> getSeats() {
        return seats;
    }

}
