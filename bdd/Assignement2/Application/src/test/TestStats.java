package test;

/**
 * Test statistics.
 * 
 * @author Jean-Michel Busca
 */
class TestStats {
    
    //
    // CLASS FIELDS
    //
    private int testTotal = 0;
    private int testOK = 0;

    //
    // METHODS
    //
    /**
     * Checks whether the specified test was successful and updates the fields
     * <code>testTotal</code> and <code>testOK</code> accordingly.
     *
     * @param test the name of the test
     * @param ok <code>true</code> if the test was sucessful and
     * <code>false</code> otherwise
     */
    synchronized void check(String test, boolean ok) {
        testTotal += 1;
        System.out.print(test + ": ");
        if (ok) {
            testOK += 1;
            System.out.println("ok");
        } else {
            System.out.println("FAILED");
        }
    }

    /**
     * Prints test statistics to standard output.
     * 
     */
    synchronized void printStats() {
        // print test results
        if (testTotal == 0) {
            System.out.println("no test performed");
        } else {
            String r = "test stats: ";
            r += "total=" + testTotal;
            r += ", ok=" + testOK + " (" + ((testOK * 100) / testTotal) + "%)";
            System.out.println(r);
        }

    }
}

