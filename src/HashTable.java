
/**
 * A HashTable that stores an array of HashEntry objects. You may use open or
 * closed hashing to resolve collisions.
 */

// linear probing
public class HashTable {

	// size of the array, should equal to # of city
	// size will not overflow since we know size in second line
	private int size;
	private HashEntry[] arr;

	public HashTable(int size) {
		this.size = size;
		arr = new HashEntry[size];
	}

	/** Returns the hash value for a given key */
	public int hash(String key) {
		return Math.abs(key.hashCode()) % size;

	}

	/**
	 * Inserts a HashNode with the given city name and Node id into the hash
	 * table
	 */
	public void insert(String cityName, int id) {
		int index = hash(cityName);
		if (arr[index] == null) {
			arr[index] = new HashEntry(cityName, id);
		} else {
			int i = 1;
			while (true) {
				if (arr[(index + i) % size] == null) {
					arr[(index + i) % size] = new HashEntry(cityName, id);
					return;
				}
				i++;
			}
		}

	}

	/**
	 * return the CityID for given city name
	 */
	public int get(String cityName) {
		int index = hash(cityName);
		if (arr[index].getcity().compareTo(cityName) == 0)
			return arr[index].getid();
		else {
			int i = 1;
			while (true) {
				if (arr[(index + i) % size] != null && arr[(index + i) % size].getcity().compareTo(cityName) == 0) {
					return arr[(index + i) % size].getid();
				}
				i++;
			}
		}
	}

	public int size() {
		return size;
	}

}