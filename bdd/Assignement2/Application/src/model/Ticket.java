package model;

/**
 *
 * @author Jean-Michel Busca
 */
public class Ticket {

    private final String departureStation;
    private final String arrivalStation;
    private final Period travelPeriod;
    private final int passengerCount;
    private final Class travelClass;
    private final float totalPrice;

    public Ticket(String departureStation, String arrivalStation,
        Period travelPeriod, int passengerCount, Class travelClass,
        float totalPrice) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.travelPeriod = travelPeriod;
        this.passengerCount = passengerCount;
        this.travelClass = travelClass;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" + "departureStation=" + departureStation + ", arrivalStation=" + arrivalStation + ", period=" + travelPeriod + ", clazz=" + travelClass + ", passengerCount=" + passengerCount + '}';
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public Period getTravelPeriod() {
        return travelPeriod;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public Class getTravelClass() {
        return travelClass;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

}
