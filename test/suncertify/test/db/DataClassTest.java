package suncertify.test.db;

import org.junit.Test;

import suncertify.application.ApplicationProperties;
import suncertify.db.Data;
import suncertify.domain.RecordState;
import suncertify.domain.Subcontractor;

public class DataClassTest {

	@Test(timeout = 1000)
	public void startTests() {
		try {

			Data.getInstance()
					.initialize(ApplicationProperties.getInstance().getDatabaseLocation());
			/*
			 * Practically, it is not necessary to execute this loop more than 1
			 * time, but if you want, you can increase the controller variable,
			 * so it is executed as many times as you want
			 */
			for (int i = 0; i < 100; i++) {
				final Thread updatingRandom = new UpdatingRandomRecordThread();
				updatingRandom.start();
				final Thread updatingRecord1 = new UpdatingRecord1Thread();
				updatingRecord1.start();
				final Thread creatingRecord = new CreatingRecordThread();
				creatingRecord.start();
				final Thread deletingRecord = new DeletingRecord1Thread();
				deletingRecord.start();
				final Thread findingRecords = new FindingRecordsThread();
				findingRecords.start();
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private class UpdatingRandomRecordThread extends Thread {

		@Override
		public void run() {
			final String[] dataEntry = new String[6];
			dataEntry[0] = "Tooler Inc";
			dataEntry[1] = "Athlone";
			dataEntry[2] = "Programming, Agile";
			dataEntry[3] = "10";
			dataEntry[4] = "$100.00";
			dataEntry[5] = "12345678";
			final Subcontractor subContractor = new Subcontractor(RecordState.valid, dataEntry);
			final int recNo = (int) (Math.random() * 50);

			try {
				System.out.println(Thread.currentThread().getId() + " trying to lock record #"
						+ recNo + " on UpdatingRandomRecordThread");

				/*
				 * The generated record number may not exist in the database, so
				 * a RecordNotFoundException must be thrown by the lock method.
				 * Since the database records are in a cache, it is not
				 * necessary to put the unlock instruction in a finally block,
				 * because an exception can only occur when calling the lock
				 * method (not when calling the update/delete methods),
				 * therefore it is not necessary to call the unlock method in a
				 * finally block, but you can customize this code according to
				 * your reality
				 */
				final long cookie = Data.getInstance().lock(recNo);
				System.out.println(Thread.currentThread().getId() + " trying to update record #"
						+ recNo + " on UpdatingRandomRecordThread");

				/*
				 * An exception cannot occur here, otherwise, the unlock
				 * instruction will not be reached, and the record will be
				 * locked forever. In this case, I created a class called
				 * RoomRetriever, which transforms from Room to String array,
				 * and vice-versa, but it could also be done this way:
				 * 
				 * data.update(recNo, new String[] {"Palace", "Smallville", "2",
				 * "Y", "$150.00", "2005/07/27", null});
				 */
				Data.getInstance().update(recNo, subContractor.getData(), cookie);
				System.out.println(Thread.currentThread().getId() + " trying to unlock record #"
						+ recNo + " on UpdatingRandomRecordThread");
				Data.getInstance().unlock(recNo, cookie);
			} catch (final Exception e) {
				System.out.println(e);
			}
		}
	}

	private class UpdatingRecord1Thread extends Thread {

		@Override
		public void run() {
			final String[] dataEntry = new String[6];
			dataEntry[0] = "Damien O'Toole";
			dataEntry[1] = "Cappamore";
			dataEntry[2] = "Cycling, Football";
			dataEntry[3] = "24";
			dataEntry[4] = "$80.99";
			dataEntry[5] = "12345678";
			final Subcontractor subContractor = new Subcontractor(RecordState.valid, dataEntry);

			try {
				System.out.println(Thread.currentThread().getId() + " trying to lock record #1 on"
						+ " UpdatingRecord1Thread");
				final long cookie = Data.getInstance().lock(1);
				System.out.println(Thread.currentThread().getId()
						+ " trying to update record #1 on" + " UpdatingRecord1Thread");
				Data.getInstance().update(1, subContractor.getData(), cookie);
				System.out.println(Thread.currentThread().getId()
						+ " trying to unlock record #1 on" + "UpdatingRecord1Thread");

				/*
				 * In order to see the deadlock, this instruction can be
				 * commented, and the other Threads, waiting to update/delete
				 * record #1 will wait forever and the deadlock will occur
				 */
				Data.getInstance().unlock(1, cookie);
			} catch (final Exception e) {
				System.out.println(e);
			}
		}
	}

	private class CreatingRecordThread extends Thread {

		@Override
		public void run() {
			final String[] dataEntry = new String[6];
			dataEntry[0] = "Sarah & Isabelle & Mya";
			dataEntry[1] = "Limerick";
			dataEntry[2] = "Playing, Trouble Making";
			dataEntry[3] = "44";
			dataEntry[4] = "$1.00";
			dataEntry[5] = "12345678";
			final Subcontractor subContractor = new Subcontractor(RecordState.valid, dataEntry);

			try {
				System.out.println(Thread.currentThread().getId() + " trying to create a record");
				Data.getInstance().create(subContractor.getData());
			} catch (final Exception e) {
				System.out.println(e);
			}
		}
	}

	private class DeletingRecord1Thread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getId() + " trying to lock record #1 on "
						+ "DeletingRecord1Thread");
				final long cookie = Data.getInstance().lock(1);
				System.out.println(Thread.currentThread().getId()
						+ " trying to delete record #1 on " + "DeletingRecord1Thread");
				Data.getInstance().delete(1, cookie);
				System.out.println(Thread.currentThread().getId()
						+ " trying to unlock record #1 on " + "DeletingRecord1Thread");
				Data.getInstance().unlock(1, cookie);
			} catch (final Exception e) {
				System.out.println(e);
			}
		}
	}

	private class FindingRecordsThread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getId() + " trying to find records");
				final String[] criteria = { "Tooler Inc", "Athlone", null, null, null, null };
				final int[] results = Data.getInstance().find(criteria);

				for (int i = 0; i < results.length; i++) {
					System.out.println(results.length + " results found.");
					try {
						final String message = Thread.currentThread().getId()
								+ " going to read record #" + results[i]
								+ " in FindingRecordsThread - still " + (results.length - 1 - i)
								+ " to go.";
						System.out.println(message);
						final String[] room = Data.getInstance().read(results[i]);
						System.out.println("Subcontractor (FindingRecordsThread): " + room[0]);
						System.out.println("Has next? " + (i < results.length - 1));
					} catch (final Exception e) {
						/*
						 * In case a record was found during the execution of
						 * the find method, but deleted before the execution of
						 * the read instruction, a RecordNotFoundException will
						 * occur, which would be normal then
						 */
						System.out.println("Exception in " + "FindingRecordsThread - " + e);
					}
				}
				System.out.println("Exiting for loop");
			} catch (final Exception e) {
				System.out.println(e);
			}
		}
	}
}
