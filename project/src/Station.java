/**
 * An aggregation of platforms.
 */
public class Station {
	private int id;
	private Platform platform;
	
	/**
	 * Instantiates and initializes a new station.
	 * 
	 * @param id The ID to be assigned to the station.
	 * @param capacity The maximum number of trains that may be held by
	 *        each platform.
	 */
	public Station(int id, int capacity) {
		this.id = id;
		platform = new Platform(capacity);
	}
	
	/**
	 * @return The ID of the station
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * @return The platform(s) associated with this station
	 */
	public Platform getPlatform() {
		return this.platform;
	}
}