

/** The Priority Queue class to be used in Dijkstra's algorithm */
public class PriorityQueue {
	
	private Vertex[] Heap;
	private int maxsize;
	private int size;
	private int[] location;

	public PriorityQueue(int max) {
		maxsize = max;
		Heap = new Vertex[maxsize+1];
		size = 0;
		Heap[0] = new Vertex(-1, Integer.MIN_VALUE);
		location = new int[max];
	}

	private int leftchild(int pos) {
		return 2 * pos;
	}
	
	private int rightchild(int pos) {
		return 2 * pos + 1;
	}

	private int parent(int pos) {
		return pos / 2;
	}

	private boolean isLeaf(int pos) {
		return ((pos > size / 2) && (pos <= size));
	}

	private void swap(int pos1, int pos2) {
		Vertex tmp;
		
		int temp = location[Heap[pos1].vertexID];
		location[Heap[pos1].vertexID] = location[Heap[pos2].vertexID];
		location[Heap[pos2].vertexID] = temp;
		
		tmp = Heap[pos1];
		Heap[pos1] = Heap[pos2];
		Heap[pos2] = tmp;
	}
	
	
	
	/**
	 * Method that takes vertex id and priority and adds it to the min heap
	 * @param element to be added (vertexId)
	 * @param priority of the element
	 */
	
	// priority small decide 
	public void insert(int vertedId, int priority){
		size++;
		Heap[size] = new Vertex(vertedId, priority);
		location[vertedId] = size;
		int current = size;
		
		while (Heap[current].priority < Heap[parent(current)].priority){
			swap(current,parent(current));
			current = parent(current);
		}
	}
	
	public void print() {
		int i;
		for (i = 1; i <= size; i++)
			System.out.print(Heap[i].vertexID + "-" + Heap[i].priority + " ");
		System.out.println();
	}
	
	/**
	 * Method that removes the min element from the heap.
	 * 
	 * @return the min element from the heap (vertexID with the smallest priority)
	 */
	public int removeMin(){
		swap(1, size);
		size--;
		if (size != 0)
			pushdown(1);
		return Heap[size+1].vertexID;
	}

	
	/**
	 * Method that takes vertex id and newPriority. It sets the priority to the new priority.
	 * Needs to update the min heap accordingly         
	 */
	
	public void reduceKey(int vertexId, int newPriority) {
		int index = location[vertexId];
		Heap[index].priority = newPriority;
		if(Heap[index].priority < Heap[parent(index)].priority){
			while (Heap[index].priority < Heap[parent(index)].priority){
				swap(index,parent(index));
				index = parent(index);
			}
		} else
			pushdown(index);
	}

	private void pushdown(int position) {
		int smallestchild;
		while (!isLeaf(position)) {
			smallestchild = leftchild(position);
			
			if ((smallestchild < size) && (Heap[smallestchild].priority > Heap[smallestchild + 1].priority))
				smallestchild = smallestchild + 1;
			
			if (Heap[position].priority <= Heap[smallestchild].priority)
				return;
			
			swap(position, smallestchild);
			position = smallestchild;
		}
	}
	
	public boolean isempty(){
		if (size == 0)
			return true;
		else 
			return false;
	}
	
	private class Vertex{
		private int vertexID, priority;
		
		public Vertex(int vertexID, int priority){
			this.vertexID = vertexID;
			this.priority = priority;
		}

	}
	
}
