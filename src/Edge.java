
/**
 * Edge class represents an Edge in the linked list of edges for a vertex.
 * 
 */

class Edge {
	private Edge next;
	private int distance;
	private int destCityID;

	public Edge(Edge next, int distance, int destCityID) {
		this.next = next;
		this.distance = distance;
		this.destCityID = destCityID;
	}

	public Edge getNext() {
		return next;
	}

	public void setNext(Edge next) {
		this.next = next;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDestCityID() {
		return destCityID;
	}

	public void setDestCityID(int destCityID) {
		this.destCityID = destCityID;
	}

}