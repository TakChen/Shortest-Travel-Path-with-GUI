import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dijkstra {

	DijkstraTablet[] dtable;
	HashTable htable;
	PriorityQueue pq;

	private int sourceVertex = -1; // store the id of the source vertex
	private USAGraph graph;

	public Dijkstra(String filename) {
		loadGraph(filename);
	}

	public USAGraph getGraph() {
		return graph;
	}

	/**
	 * Compute all the shortest paths from the source vertex to all the other
	 * vertices in the graph; This function is called from GUIApp, when the user
	 * clicks on the city.
	 */
	public void computePaths(CityNode vSource) {
		// build the Dijkstra table
		dtable = new DijkstraTablet[htable.size()];

		for (int i = 0; i < dtable.length; i++) {
			dtable[i] = new DijkstraTablet(false, Integer.MAX_VALUE, sourceVertex);
		}
		dtable[htable.get(vSource.getCity())].distance = 0;

		pq = new PriorityQueue(htable.size());
		for (int i = 0; i < dtable.length; i++) {
			pq.insert(i, dtable[i].distance);
		}

		while (!pq.isempty()) {
			int cityID = pq.removeMin();
			dtable[cityID].shortest = true;
			Edge vertex = graph.getEdge(cityID);
			while (vertex != null) {
				if (dtable[vertex.getDestCityID()].distance > dtable[cityID].distance + vertex.getDistance()) {
					dtable[vertex.getDestCityID()].distance = dtable[cityID].distance + vertex.getDistance();
					pq.reduceKey(vertex.getDestCityID(), dtable[vertex.getDestCityID()].distance);
					dtable[vertex.getDestCityID()].from = cityID;
				}
				vertex = vertex.getNext();
			}
		}

	}

	/**
	 * Returns the shortest path between the source vertex and this vertex.
	 * Returns the array of node id-s on the path
	 */
	public ArrayList<Integer> shortestPath(CityNode vTarget) {
		// called from the GUIApp class
		// return the paths from Dijkstra table
		ArrayList<Integer> arr = new ArrayList<>();
		int cityID = htable.get(vTarget.getCity());

		while (cityID != -1) {
			arr.add(0, cityID); // may no need add to 0
			cityID = dtable[cityID].from;
		}

		return arr;
	}

	/**
	 * Loads graph info from the text file into USAGraph
	 * 
	 * @param filename
	 */
	public void loadGraph(String filename) {
		try (BufferedReader bf = new BufferedReader(new FileReader(filename))) {
			bf.readLine();
			String line = bf.readLine();
			int numCity = Integer.parseInt(line.trim());
			graph = new USAGraph(numCity);
			htable = new HashTable(numCity);
			int i = 0;
			while (i < numCity) {
				// create CityNode
				// add item into HashTable, size is known in the 2nd line
				String[] words = bf.readLine().split(" ");
				graph.addNode(new CityNode(words[0], Double.parseDouble(words[1]), Double.parseDouble(words[2])));
				htable.insert(words[0], i);
				i++;
			}
			// add edges
			bf.readLine();
			while ((line = bf.readLine()) != null) {
				String words[] = line.split(" ");
				graph.addEdge(htable.get(words[0]), new Edge(null, Integer.parseInt(words[2]), htable.get(words[1])));
			}

		} catch (IOException e) {
			System.err.println("Dijkstra.loadGraph()");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// Create an instance of the Dijkstra class
		// The parameter is the name of the file
		Dijkstra dijkstra = new Dijkstra(args[0]);

		// create the GUI window with the panel
		GUIApp app = new GUIApp(dijkstra);
	}

	private class DijkstraTablet {
		private int distance;
		private int from; // from which vertex
		private boolean shortest; // is the shortest one?

		public DijkstraTablet(boolean shortest, int distance, int from) {
			this.shortest = shortest;
			this.distance = distance;
			this.from = from;
		}

	}
}
