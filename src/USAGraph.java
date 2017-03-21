
/** A class representing a graph. Stores the array of nodes and the adjacency list.
 * 
 */
import java.awt.Point;
import java.util.Iterator;

public class USAGraph {

	//use for image, store name and point
	private CityNode[] nodes;

	// for each vertex store a linked list of edges
	private Edge[] adjacencyList;

	private int maxNumNodes; // the maximum number of nodes in the graph
	private int numNodes = 0; // how many nodes have been added to the graph so
								// far
	private int numEdges = 0; // how many edges have been added to the graph so
								// far

	public final int EPS_DIST = 5; // how close to the actual location of the
									// city the user has to click to select it
									// on the image

	public USAGraph(int maxNumNodes) {
		this.maxNumNodes = maxNumNodes;
		// Allocate memory for the nodes array and the adjacencyList array
		nodes = new CityNode[maxNumNodes];
		adjacencyList = new Edge[maxNumNodes];
		// Note: do not assign a value to numNodes. It should be incremented in
		// the addNode() method.
	}

	/**
	 * Returns a node with index i
	 */
	public CityNode getNode(int i) {
		return nodes[i];
	}

	/**
	 * Returns the head of the linked list of edges for the vertex with id = i
	 */
	public Edge getEdge(int i) {
		return adjacencyList[i];
	}

	/**
	 * Adds a node to the graph
	 * 
	 * @param node
	 */
	public void addNode(CityNode node) {
		nodes[numNodes] = node;
		numNodes++;
	}

	public int numNodes() {
		return numNodes;
	}

	/**
	 * Adds the edge to the linked list for this vertexId
	 * 
	 * @param vertexId
	 * @param edge
	 */
	public void addEdge(int nodeId, Edge edge) {
		if (adjacencyList[nodeId] == null){
			adjacencyList[nodeId] = edge;
		} else {
			edge.setNext(adjacencyList[nodeId]);
			adjacencyList[nodeId] = edge;
		}
		numEdges++;
		// add 2nd edge 
		int destCityID = edge.getDestCityID();
		Edge reversed = new Edge(null, edge.getDistance(), nodeId);
		if (adjacencyList[destCityID] == null){
			adjacencyList[destCityID] = reversed;
		} else {
			reversed.setNext(adjacencyList[destCityID]);
			adjacencyList[destCityID] = reversed;
		}
		numEdges++;
	}

	/**
	 * Given the location of the click, returns the node of the graph at this
	 * location.
	 */
	public CityNode getVertex(Point loc) {
		for (CityNode v : nodes) {
			Point p = v.getLocation();
			if ((Math.abs(loc.x - p.x) < EPS_DIST) && (Math.abs(loc.y - p.y) < EPS_DIST))
				return v;
		}
		return null;
	}

	/**
	 * Returns the iterator over the node locations. Used by the GUIApp class to
	 * draw little circles at the locations of the nodes, as well as for drawing
	 * the path.
	 */
	public Iterator<Point> getNodeLocations() {
		return new NodeIterator();

	}

	/** The inner class, the iterator over node locations */
	private class NodeIterator implements Iterator<Point> {
		private int nodeId;

		public NodeIterator() {
			nodeId = 0;
		}

		@Override
		public boolean hasNext() {
			if (nodeId < numNodes)
				return true;
			else
				return false;
		}

		@Override
		public Point next() {
			// if hasNext returns false, print an error message and return null
			// Otherwise return the location of the "current" node ; increment nodeId
			if (hasNext() == false) {
				System.err.println("No next elem for nodes");
				return null;
			}
			nodeId++;
			return nodes[nodeId-1].getLocation();

		}

	} // end of the private class NodeIterator

	/**
	 * Returns the array of all edges for drawing: each element in the array
	 * corresponds to one edge and is the array of two Point objects
	 * (corresponding to the locations of the two nodes connected by this edge).
	 */
	public Point[][] getEdges() {
		Point[][] edges = new Point[numEdges][2];
		int i = 0 , k = 0;
		while (i < numNodes){
			Edge e = adjacencyList[i];
			while (e != null){
				edges[k][0] = nodes[i].getLocation();
				edges[k][1] = nodes[e.getDestCityID()].getLocation();
				k++;
				e = e.getNext();
			}
			i++;
		}
		return edges;

	}

	/**
	 * Returns the array of cities corresponding to the vertices of this graph
	 * in the array
	 * 
	 * @return
	 */
	public String[] getCities() {
		String[] labels = new String[numNodes];
		for (int i = 0; i < labels.length; i++) {
			labels[i] = nodes[i].getCity();
		}
		return labels;
	}

} // class MapGraph