package suncertify.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import suncertify.db.DB;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class TestJarSubmission {

	// path to the directory where you unjarred the submission jar
	// private static final String DIR_SUBMISSION_JAR =
	// "/Users/damienotoole/Dropbox/WorkSpaces/workspace_ECLIPSE/com.tooler.scjd/output/submission";
	private static final String DIR_SUBMISSION_JAR = "C:\\Users\\eeidote\\Dropbox\\WorkSpaces\\workspace_ECLIPSE\\com.tooler.scjd\\output\\submission";

	/**
	 * A directory called code, containing all the source code and related parts
	 * of your project. You must create subdirectories within this to reflect
	 * your package structure and distribute your source files within those
	 * directories.
	 */
	private static final String DIR_CODE = "code";

	/**
	 * A directory called docs, containing the following items at the top level.
	 */
	private static final String DIR_DOCS = "docs";

	/**
	 * A subdirectory called javadoc, containing HTML/Javadoc documentation for
	 * all classes and interfaces you are submitting.
	 */
	private static final String DIR_JAVADOC = TestJarSubmission.DIR_DOCS + "/javadoc";

	/**
	 * The original, unchanged database file that was supplied to you. Note that
	 * you must keep a copy of the original database file supplied to you, and
	 * this must be the file you submit. The marking process will expect the
	 * exact same data without any changes.
	 */
	private static final String FILE_DB = "db-2x3.db";

	/**
	 * A file called choices.txt that containing pure ASCII (not a word
	 * processor format) text describing the significant design choices you
	 * made. Detail the problems you perceived, the issues surrounding them,
	 * your value judgments, and the decisions that you made. This document
	 * should also describe any uncertainties you had regarding the project, and
	 * the decisions you made when resolving them.
	 */
	private static final String FILE_CHOICES = TestJarSubmission.DIR_DOCS + "/choices.txt";

	/**
	 * The executable JAR containing the programs. This must be called
	 * runme.jar.
	 */
	private static final String FILE_EXECUTABLE_JAR = "runme.jar";

	/**
	 * This html file.
	 */
	private static final String FILE_INSTRUCTIONS = TestJarSubmission.DIR_DOCS
			+ "/instructions.html";

	/**
	 * The index.html file of the HTML/Javadoc documentation.
	 */
	private static final String FILE_JAVADOC = TestJarSubmission.DIR_JAVADOC + "/index.html";

	/**
	 * User documentation for the database server and the gui client. If your
	 * user documentation is online then you may omit this file. However, if the
	 * documentation is not online, you must provide either a single plain ASCII
	 * (not word processor format) text document, which must be called
	 * userguide.txt, or multiple HTML files which must all be accessible from a
	 * starting point document that must be called userguide.html.
	 */
	private static final String FILE_USERGUIDE = TestJarSubmission.DIR_DOCS + "/userguide.txt";

	/**
	 * A file called version.txt. This must contain pure ASCII (not a word
	 * processor format) indicating the exact version of JDK you used, and the
	 * host platform you worked on.
	 */
	private static final String FILE_VERSION = "version.txt";

	/**
	 * The required data access class (Data).
	 */
	private static final String SOURCE_DATA = TestJarSubmission.DIR_CODE
			+ "/suncertify/db/Data.java";

	/**
	 * The required interface (DB).
	 */
	private static final String SOURCE_DBMAIN = TestJarSubmission.DIR_CODE
			+ "/suncertify/db/DB.java";

	/**
	 * The required exception DuplicateKeyException.
	 */
	private static final String SOURCE_EXCEPTION_DK = TestJarSubmission.DIR_CODE
			+ "/suncertify/db/DuplicateKeyException.java";

	/**
	 * The required exception RecordNotFoundException.
	 */
	private static final String SOURCE_EXCEPTION_RNF = TestJarSubmission.DIR_CODE
			+ "/suncertify/db/RecordNotFoundException.java";

	/**
	 * The required directories in the submission jar.
	 */
	private static final String[] REQUIRED_DIRECTORIES = new String[] { TestJarSubmission.DIR_CODE,
			TestJarSubmission.DIR_DOCS, TestJarSubmission.DIR_JAVADOC };

	/**
	 * The expected count of required directories in the submission jar.
	 */
	private static final int REQUIRED_DIRECTORIES_COUNT = 3;

	/**
	 * The required files in the submission jar.
	 */
	private static final String[] REQUIRED_FILES = new String[] { TestJarSubmission.FILE_DB,
			TestJarSubmission.FILE_CHOICES, TestJarSubmission.FILE_EXECUTABLE_JAR,
			TestJarSubmission.FILE_INSTRUCTIONS, TestJarSubmission.FILE_JAVADOC,
			TestJarSubmission.FILE_USERGUIDE, TestJarSubmission.FILE_VERSION };

	/**
	 * The expected count of required files in the submission jar.
	 */
	private static final int REQUIRED_FILES_COUNT = 7;

	/**
	 * The required sources in the submission jar.
	 */
	private static final String[] REQUIRED_SOURCES = new String[] { TestJarSubmission.SOURCE_DATA,
			TestJarSubmission.SOURCE_DBMAIN, TestJarSubmission.SOURCE_EXCEPTION_DK,
			TestJarSubmission.SOURCE_EXCEPTION_RNF };

	/**
	 * The expected count of required sources in the submission jar.
	 */
	private static final int REQUIRED_SOURCES_COUNT = 4;

	/**
	 * Test for the required directories in the submission jar.
	 */
	@Test
	public void testRequiredDirectories() {
		Assert.assertEquals(TestJarSubmission.REQUIRED_DIRECTORIES_COUNT,
				TestJarSubmission.REQUIRED_DIRECTORIES.length);
		for (final String directory : TestJarSubmission.REQUIRED_DIRECTORIES) {
			final File f = new File(TestJarSubmission.DIR_SUBMISSION_JAR, directory);
			Assert.assertTrue("directory '" + directory + "' not exists", f.exists());
		}
	}

	/**
	 * Test for the required files in the submission jar.
	 */
	@Test
	public void testRequiredFiles() {
		Assert.assertEquals(TestJarSubmission.REQUIRED_FILES_COUNT,
				TestJarSubmission.REQUIRED_FILES.length);
		for (final String file : TestJarSubmission.REQUIRED_FILES) {
			final File f = new File(TestJarSubmission.DIR_SUBMISSION_JAR, file);
			Assert.assertTrue("file '" + file + "' not exists", f.exists());
		}
	}

	/**
	 * Test for the required source files in the submission jar.
	 */
	@Test
	public void testRequiredSources() {
		Assert.assertEquals(TestJarSubmission.REQUIRED_SOURCES_COUNT,
				TestJarSubmission.REQUIRED_SOURCES.length);
		for (final String source : TestJarSubmission.REQUIRED_SOURCES) {
			final File f = new File(TestJarSubmission.DIR_SUBMISSION_JAR, source);
			Assert.assertTrue("source '" + source + "' not exists", f.exists());
		}
	}

	/**
	 * Test for the following source requirements:
	 * <ul>
	 * <li>Your data access class must implement the interface DB</li>
	 * <li>Any unimplemented exceptions in the interface DB must have a zero
	 * argument constructor and a second constructor that takes a String that
	 * serves as the exception's description.
	 * </ul>
	 */
	@Test
	public void testSourceRequirements() {
		// check data access class
		try {
			final Class<?> clazz = Class.forName("suncertify.db.Data");
			Assert.assertTrue("DB is not assignable from " + clazz,
					DB.class.isAssignableFrom(clazz));
		} catch (final ClassNotFoundException e1) {
			Assert.fail("data access class not found");
		}
		// check the unimplemented exceptions
		final Class<?>[] exceptionClasses = { RecordNotFoundException.class,
				DuplicateKeyException.class };
		for (final Class<?> exceptionClass : exceptionClasses) {
			// a zero argument constructor
			try {
				exceptionClass.getConstructor((Class[]) null);
			} catch (final SecurityException e) {
				Assert.fail("security violation");
			} catch (final NoSuchMethodException e) {
				Assert.fail("the zero argument constructor not found");
			}
			// a constructor with String argument
			try {
				exceptionClass.getConstructor(String.class);
			} catch (final SecurityException e) {
				Assert.fail("security violation");
			} catch (final NoSuchMethodException e) {
				Assert.fail("constructor not found");
			}
		}
	}

}