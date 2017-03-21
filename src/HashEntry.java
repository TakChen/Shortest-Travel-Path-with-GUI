/** Represents one node in the HashTable. Each HashEntry contains the name of the city and the id.
 * If open hashing is used for resolving collisions, each HashEntry should also contain a 
 * pointer to the next HashEntry. 
 *
 */
public class HashEntry {
	private String cityName;
	private int id;
	
	public HashEntry(String cityName, int id) {
		this.cityName = cityName;
		this.id = id;
	}
	
	public String getcity(){
		return this.cityName;
	}
	
	public int getid(){
		return this.id;
	}

}
