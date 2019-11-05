package model;

import java.util.Date;
import java.util.Objects;

/**
 * The description of a journey.
 *
 * @author Jean-Michel Busca
 */
public class Journey {

    private final String departureStation;
    private final String arrivalStation;
    private final int trainNumber;
    private final Date departureDate; // date/time
    private final Date arrivalDate;   // date/time

    public Journey(String departureStation, String arrivalStation, int trainNumber, Date departureDate, Date arrivalDate) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.trainNumber = trainNumber;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return "Journey{" + "departureStation=" + departureStation + ", arrivalStation=" + arrivalStation + ", trainNumber=" + trainNumber + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate + '}';
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.departureStation);
        hash = 89 * hash + Objects.hashCode(this.arrivalStation);
        hash = 89 * hash + this.trainNumber;
        hash = 89 * hash + Objects.hashCode(this.departureDate);
        hash = 89 * hash + Objects.hashCode(this.arrivalDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Journey other = (Journey) obj;
        if (this.trainNumber != other.trainNumber) {
            return false;
        }
        if (!Objects.equals(this.departureStation, other.departureStation)) {
            return false;
        }
        if (!Objects.equals(this.arrivalStation, other.arrivalStation)) {
            return false;
        }
        if (!Objects.equals(this.departureDate, other.departureDate)) {
            return false;
        }
        if (!Objects.equals(this.arrivalDate, other.arrivalDate)) {
            return false;
        }
        return true;
    }

}
