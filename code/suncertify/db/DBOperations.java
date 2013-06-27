package suncertify.db;

/**
 * The {@link DB} interface is responsible for the <i>CRUD
 * (Create-Read-Update-Delete)</i> use-cases along with locking/unlocking of
 * records where required by the <i>CRUD</i> operations. The
 * {@code DBOperations} interface extends the {@code DB} interface and adds
 * extra functionality that may be required outside of <i>CRUD</i> use-cases,
 * for example any initial setup required for data access.
 * <p>
 * Note: {@link DBOperations#initialize(String)} must be called before any other
 * method is invoked on implementation classes of the {@code DB} interface.
 * 
 * @version 1.0
 * @author Damien O'Toole
 */
interface DBOperations extends DB {

	/**
	 * Initializing the data access component so that it is in the correct state
	 * before any request of <i>CRUD</i> operations is allowed.
	 * 
	 * @param databaseLocation
	 *            the database location
	 * @throws DatabaseException
	 *             the invalid database exception
	 */
	public void initialize(final String databaseLocation)
			throws DatabaseException;

	/**
	 * Destroy the data access component so that any resources are released or
	 * operations required are completed by the implementation class of this
	 * interface.
	 * <p>
	 * On invocation of this method, the implementation class will require it's
	 * {@link DBOperations#initialize(String)} method to be called before and request
	 * of <i>CRUD</i> operations is allowed
	 * 
	 * @throws DatabaseException
	 *             the invalid database exception
	 */
	public void destroy() throws DatabaseException;

}
