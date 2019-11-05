package model;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides the application with high-level methods to access the persistent
 * data store. The class hides the fact that data are stored in a RDBMS and also
 * hides all the complex SQL machinery required to access it.
 * <p>
 * The constructor and the methods of this class all throw a
 * {@link DataAccessException} whenever an unrecoverable error occurs, e.g. the
 * the application cannot connect to the database, or the connexion to the
 * database is lost.
 * <p>
 * Pour simplifier votre code : On ne traite pas les correspondances (transfers)
 * <p>
 * Pour simplifier les test : En cas d'erreur de paramètres: renvoyer null /
 * vide, ne pas envoyer d'exception <P> 
 * <p>
 * <b>Note to the implementors</b>: You <b>must not</b> alter the interface of
 * this class' constructor and methods, including the exceptions thrown.
 *
 * @author Jean-Michel Busca
 */
public class DataAccess {

    /**
     * Creates a new <code>DataAccess</code> object that interacts with the
     * specified database, using the specified login and password. Each object
     * maintains a <b>dedicated</b> connection to the database until the
     * {@link close} method is called.
     *
     * @param url the url of the database to connect to
     * @param login the (application) login to use
     * @param password the password
     *
     * @throws DataAccessException if an unrecoverable error occurs
     * @throws SQLException 
     */
    public DataAccess(String url, String login, String password) throws
        DataAccessException, SQLException {
        // TODO
    	Connection con = DriverManager.getConnection(url, login, password);
    	System.out.println(con);
    }

    /**
     * Creates and populates the database according to the examples provided in
     * the requirements of marked lab 2. If the database already exists before
     * the method is called, the method discards the database and creates it
     * again from scratch.
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public void initDatabase() throws DataAccessException {
        /*
         * try (InputFileReader file = new InputFileReader()) {
         *
         * } catch (Exception e) { throw new DataAccessException(e); }
         */
    }

    /**
     * See Operation 2.1.1. sorted by departure time
     *
     * @param departureStation
     * @param arrivalStation
     * @param fromDate
     * @param toDate
     *
     * @return the corresponding list of journeys, including the empty list if
     * no journey is found
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public List<Journey> getTrainTimes(String departureStation,
        String arrivalStation, Date fromDate, Date toDate)
        throws DataAccessException {
        return null;
    }

    /**
     * See Operation 2.1.2 call Bank.getInstance().getAccount("name").withdraw(123);
     *
     * @param departureStation
     * @param arrivalStation
     * @param travelPeriod
     * @param passengerCount
     * @param travelClass
     *
     * @return the bought ticket, or <code>null</code> if some parameter was
     * incorrect
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public Ticket buyTicket(String departureStation, String arrivalStation,
        Period travelPeriod, int passengerCount, Class travelClass)
        throws DataAccessException {
        return null;
    }

    /**
     * See Operation 2.1.3. call Bank.getInstance().getAccount("name").withdraw(123);
     *
     * @param trainNumber
     * @param departureDate
     * @param departureStation
     * @param arrivalStation
     * @param passengerCount
     * @param travelClass
     * @param customerEmail
     *
     * @return the booking, or <code>null</code> if some parameter was incorrect
     * or not enough seats were available
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public Booking buyTicketAndBook(int trainNumber, Date departureDate,
        String departureStation, String arrivalStation, int passengerCount,
        Class travelClass, String customerEmail)
        throws DataAccessException {
        // TODO
        return null;
    }

    /**
     * See Operation 2.1.4. call Bank.getInstance().getAccount("name").refund(123);
     *
     * @param bookingID
     * @param customerEmail
     *
     * @return <code>true</code> if the booking was cancelled, and
     * <code>false</code> otherwise
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public boolean cancelBooking(String bookingID, String customerEmail)
        throws DataAccessException {
        // TODO
        return false;
    }

    /**
     * See Operation 2.2.2
     *
     * @param trainNumber
     * @param departureDate
     * @param beginStation
     * @param endStation
     *
     * @return the list of available seats, including the empty list if no seat
     * is available
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public List<Integer> getAvailableSeats(int trainNumber, Date departureDate,
        String beginStation, String endStation)
        throws DataAccessException {
        // TODO
        return null;
    }

    /**
     * Closes the underlying connection and releases all related ressources. The
     * application must call this method when it is done accessing the data
     * store.
     *
     * @throws DataAccessException if an unrecoverable error occurs
     */
    public void close() throws DataAccessException {
        // TODO
    }

}
