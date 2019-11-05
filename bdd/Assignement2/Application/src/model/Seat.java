package model;

/**
 *
 * @author Jean-Michel Busca
 */
public class Seat {
    private final int carNumber;
    private final int seatNumber;

    public Seat(int carNumber, int seatNumber) {
        this.carNumber = carNumber;
        this.seatNumber = seatNumber;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.carNumber;
        hash = 79 * hash + this.seatNumber;
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
        final Seat other = (Seat) obj;
        if (this.carNumber != other.carNumber) {
            return false;
        }
        if (this.seatNumber != other.seatNumber) {
            return false;
        }
        return true;
    }
    
}
