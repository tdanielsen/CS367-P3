/**
 * An ordered collection of trains.
 */
public interface PlatformADT {	
	/**
	 * Adds the given train to the platform.
	 *
	 * @param train The train to be added to the platform
	 * @throws FullPlatformException If the platform is full
	 */
	void put(Train item) throws FullPlatformException;
	
	/**
	 * Removes a train from the platform.
	 *
	 * @return The train removed from the platform
	 * @throws EmptyPlatformException If the platform is empty
	 */
	Train get() throws EmptyPlatformException;
	
	/**
	 * Returns the train which may exit first without removing it from the
	 * platform.
	 *
	 * @return The train removed from the platform
	 * @throws EmptyPlatformException If the platform is empty
	 */
	Train check() throws EmptyPlatformException;
	
	/**
	 * Checks if the platform is empty.
	 * 
	 * @return True if the platform is empty; else false
	 */
	boolean isEmpty();
	
	/**
	 * Checks if the platform is full.
	 * 
	 * @return True if the platform is full; else false
	 */
	boolean isFull();
}