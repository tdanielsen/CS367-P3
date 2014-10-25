import java.util.List;
import java.util.ArrayList;

public class Train {
	private int id;
	private List<Integer> etd;
	private List<Integer> atd;
	private List<Integer> ata;
	
	/**
	 * Instantiates and initializes a new train.
	 * 
	 * @param id The ID to be assigned to this train
	 */
	public Train(int id) {
		this.id = id;
		this.etd = new ArrayList<Integer>();
		this.atd = new ArrayList<Integer>();
		this.ata = new ArrayList<Integer>();
	}
	
	/**
	 * @return The id of the train
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * @return The ETD of the train
	 */
	public List<Integer> getETD() {
		return this.etd;
	}
	
	/**
	 * @return The ATD of the train
	 */
	public List<Integer> getATD() {
		return this.atd;
	}
	
	/**
	 * @return The ATA of the train
	 */
	public List<Integer> getATA() {
		return this.ata;
	}
}