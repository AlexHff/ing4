package test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.DataAccess;
import model.Journey;

/**
 * A simple test program for {@link DataAccess}.
 *
 * @author Jean-Michel Busca
 *
 */
public class SimpleTests {

    //
    // CONSTANTS
    //
    private static final int MAX_CUSTOMERS = 5;

    //
    // CLASS FIELDS
    //
    private static final TestStats tests = new TestStats();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyy-MM-dd");
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm");

    //
    // HELPER CLASSES
    //
    /**
     * Emulates a user interacting with the system These operations are defined
     * in the {@link #run()} method.
     * <p>
     * This class is used to perform multi-user tests. See the
     * {@link SimpleTests#main(String[])} method.
     *
     * @author Jean-Michel Busca
     *
     */
    static class UserEmulator extends Thread {

        private final DataAccess data;
        private final String user;

        /**
         * Creates a new user emulator with the specified name, using the
         * specified data acces object.
         *
         * @param data the data access object to use
         * @param user the name of the user running the test
         */
        public UserEmulator(DataAccess data, String user) {
            this.data = data;
            this.user = user;
        }

        @Override
        public String toString() {
            return user + "[" + data + "]";
        }

        @Override
        public void run() {
            System.out.println(this + ": starting");

            try {
                multiUserTests(data, user);
            } catch (Exception e) {
                System.out.println(this + ": " + e);
            }

            System.out.println(this + ": exiting");
        }

    }

    //
    // TEST METHODS
    //
    /**
     * Runs a single-user test suite on the specified data access object, on
     * behalf of the specified user.
     *
     * @param data the data access object to use
     * @param user the name of the user running the test
     *
     * @throws Exception if anything goes wrong
     */
    private static void singleUserTests(DataAccess data, String user)
        throws Exception {

        // NOTE: some of the tests below throw an NullPointerException because 
        // the DataAccess methods are not implemented yet
        // 1- train times
        // 2- tickets
        // TODO complete the test
    }

    private void testTrainTimes(DataAccess data, String user) throws Exception {
        
        // Lyon - Avignon on 2017-10-29 [08:00, 18:00]
        Date fromDate = dateTimeFormat.parse("2017-10-29 08:00");
        Date toDate = dateTimeFormat.parse("2017-10-29 18:00");
        List<Journey> journeys = 
            data.getTrainTimes("Lyon", "Avignon", fromDate, toDate);
        boolean ok1 = journeys != null && journeys.size() == 2;
        tests.check("getTrainTimes:1", ok1);
        boolean ok2 = ok1 && 
            journeys.get(0).getTrainNumber() == 6607 && 
            journeys.get(1).getTrainNumber() == 6650;
        tests.check("getTrainTimes:2", ok2);
        if (ok1) {
            journeys.sort(
                (Journey j1, Journey j2) ->
                    j1.getDepartureDate().compareTo(j2.getDepartureDate()));
        }
        //boolean ok3 = ok1 && 


    }

    private void testTicketPrices(DataAccess data, String user) throws Exception {

    }

    private void testAvailableSeats(DataAccess data, String user) throws Exception {
        tests.check("getAvailableSeats",
            data.getAvailableSeats(6607, dateTimeFormat.parse(
                "2017-10-29 00:00:00"),
                "Lyon", "Avignon").size() == 1093);

    }

    /**
     * Runs a multi-user test suite on the specified data access object, on
     * behalf of the specified user.
     *
     * @param data the data access object to use
     * @param user the name of the user running the test
     *
     * @throws Exception if anything goes wrong
     */
    private static void multiUserTests(DataAccess data, String user)
        throws Exception {

        // TODO complete the test
    }

    //
    // MAIN
    //
    /**
     * Runs the simple test program.
     *
     * @param args url login password
     *
     */
    public static void main(String[] args) {

        // check parameters
        if (args.length != 3) {
            System.err.println("usage: SimpleTest <url> <login> <password>");
            System.exit(1);
        }

        DataAccess data = null;
        List<DataAccess> datas = new ArrayList<>();
        try {

            // create the main data access object
            data = new DataAccess(args[0], args[1], args[2]);

            // create and population the database
            data.initDatabase();

            // execute single-user tests
            System.out.println("Running single-user tests...");
            singleUserTests(data, "single user");

            // execute multi-users tests
            System.out.println("Running multi-users tests...");
            List<UserEmulator> emulators = new ArrayList<>();
            for (int i = 0; i < MAX_CUSTOMERS; i++) {
                DataAccess data2 = new DataAccess(args[0], args[1], args[2]);
                datas.add(data2);
                UserEmulator emulator = new UserEmulator(data2, "user#" + i);
                emulators.add(emulator);
                emulator.start();
            }

            // wait for the test to complete
            for (UserEmulator e : emulators) {
                e.join();
            }

            // you may add some tests here:
            // TODO
        } catch (Exception e) {

            System.err.println("test aborted: " + e);
            e.printStackTrace();

        } finally {

            tests.printStats();

            // close all data access objects
            if (data != null) {
                try {
                    data.close();
                } catch (Exception e) {
                    System.err.println("unexpected exception: " + e);
                }
            }

            for (DataAccess m : datas) {
                try {
                    m.close();
                } catch (Exception e) {
                    System.err.println("unexpected exception: " + e);
                }
            }

        }

    }
}
