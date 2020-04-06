package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import model.DataAccess;

public class Test {

	//
	// CONSTANTS
	//
	private static final int ACCOUNT_NUMBER = 5;
	private static final int TRANSFERT_THREAD_NUMBER = 5;

	private static final long EXECUTION_TIME = 10 * 100;

	//
	// CLASS FIELDS
	//
	private static volatile AtomicInteger operationCount = new AtomicInteger(0);
	private static volatile long startDate = System.currentTimeMillis();

	//
	// INNER CLASSES
	//
	/**
	 * Money transfert thread.
	 */
	private static class TransfertThread extends Thread {

		private final DataAccess data;
		private final Random random = new Random();

		public TransfertThread(DataAccess data, String name) {
			super(name);
			this.data = data;
		}

		@Override
		public String toString() {
			return getName();
		}

		@Override
		public void run() {
			System.out.println(this + ": starting on " + data);

			final long start = System.currentTimeMillis();
			while (System.currentTimeMillis() < start + EXECUTION_TIME) {
				// wait for a while
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					System.out.println(this + ": interrupted");
					break;
				}

				// get the account set
				List<Integer> accounts = new ArrayList<Integer>();
				try {
					accounts = data.getAccounts();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if (accounts.isEmpty()) {
					continue;
				}
				/*
				for (int account : accounts) {
					try {
						System.out.println(account + " " + data.getBalance(account));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				*/

				// transfert random amount between random accounts
				int from = accounts.get(random.nextInt(accounts.size()));
				int to = accounts.get(random.nextInt(accounts.size()));
				long amount = random.nextInt(1000);
				//System.out.println(this + ": transfering €" + amount + " from #" + from + " to #" + to);
				try {
					if (data.transfert(from, to, amount)) {
						operationCount.incrementAndGet();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println(this + ": exiting");
		}
	}

	/**
	 * Holdings checking thread.
	 */
	private static class CheckHoldingsThread extends Thread {

		private final DataAccess data;
		//private final Random random = new Random();

		public CheckHoldingsThread(DataAccess data, String name) {
			super(name);
			this.data = data;
		}

		@Override
		public String toString() {
			return getName();
		}

		@Override
		public void run() {
			System.out.println(this + ": starting on " + data);

			final long start = System.currentTimeMillis();
			while (System.currentTimeMillis() < start + EXECUTION_TIME + 5 * 1000L) {
				// wait for a while
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println(this + ": interrupted");
					break;
				}

				// compute and print hodings
				try {
					System.out.println(this + ": holdings = " + data.getHoldings());
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// compute and print throughput
				long now = System.currentTimeMillis();
				long throughput = (operationCount.get() * 1000) / (now - startDate);
				System.out.println(this + ": throughput = " + throughput + " op/s");
				operationCount.set(0);
				startDate = now;

			}

			System.out.println(this + ": exiting");
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Remove before posting
		args = new String[3];
		args[0] = "jdbc:mysql://localhost/bank";
		args[1] = "root";
		args[2] = "";

		// work around Netbeans bug
		if (args.length == 2) {
			args = Arrays.copyOf(args, 3);
			args[2] = "";
		}

		// check parameters
		if (args.length != 3) {
			System.err.println("usage: Test <url> <login> <password>");
			System.exit(1);
		}

		// create the database
		DataAccess data = new DataAccess(args[0], args[1], args[2]);
		if (!data.createDatabase()) {
			System.err.println("Test: failed to create the database");
			System.exit(1);
		}
		System.out.println("Test: database created");
		for (int i = 101; i < 101 + ACCOUNT_NUMBER; i++) {
			if (!data.createAccount(i) || !data.setBalance(i, 1000)) {
				System.err.println("Test: failed to create account #" + i);
				System.exit(1);
			}
			System.out.println("Test: account #" + i + " created");
		}

		// run the threads
		data = new DataAccess(args[0], args[1], args[2]);
		new CheckHoldingsThread(data, "Check Holdings").start();

		Thread[] transfertThreads = new TransfertThread[TRANSFERT_THREAD_NUMBER];
		for (int i = 0; i < transfertThreads.length; i++) {
			data = new DataAccess(args[0], args[1], args[2]);
			transfertThreads[i] = new TransfertThread(data, "Transfers " + i);
			transfertThreads[i].start();
		}

		// wait for the trasfert threads to complete
		for (Thread thread : transfertThreads) {
			thread.join();
		}
		System.out.println("done.");
	}
}
