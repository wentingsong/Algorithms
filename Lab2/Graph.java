import java.util.ArrayList;
//import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
/*
 * Name: <Wenting Song>
 * EID: <ws8496>
 */

public class Graph implements Program2{
	// n is the number of ports
	private int n;
    
	// Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	// Use of this class is optional. You may make your own, and comment
	// this one out.
	private class Edge{
		public int node;
		public int time;
		public int capacity;
		public Edge(int n, int t, int c){
			node = n;
			time = t;
			capacity = c;
		}

		// prints out an Edge.
		public String toString() {
			return "" + node;
		}
	}

	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....
	ArrayList<Edge> adjList[]; // array of arraylist, right?
	// each vertex has an arrayList of Edge, n vertex already known

	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	//Hint: Do you need other functions here?
	public Graph(int x) { //!constructor of the Graph, new new new
		n = x;
		
		adjList = new ArrayList[n]; // int[], like this
		// define the size of array as number of vertices
		for (int i = 0; i < n; i++) { 
		//Create a new list for each vertex s.t. adjacent nodes can be stored
			adjList[i] = new ArrayList<Edge>();
		}
	}
	
	

    
	
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		//undirected graph
		Edge e1 = new Edge(port2, time, capacity);
		ArrayList<Edge> tmp1 = adjList[port1];
		tmp1.add(e1);
		
		Edge e2 = new Edge(port1, time, capacity);
		ArrayList<Edge> tmp2 = adjList[port2];
		tmp2.add(e2);
		return;
	}
	
	
	
	
	//===============================================================================================	
	//===============================================================================================	

	
//	public Set<Vertex> create(Graph graph) {
//		LinkedHashSet<Vertex> vertexs = new LinkedHashSet<Vertex>();
//		return vertexs;
//	}
	
	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		//int maxConstant = Integer.MAX_VALUE;  //Integer.MAX_VALUE is the constant
		Set<Vertex> queue = new LinkedHashSet<Vertex>();  
		for (int i = 0; i < n; i++) {
			if(i == sourcePort)
				queue.add(new Vertex(i, 0, null));
			else
				queue.add(new Vertex(i, Integer.MAX_VALUE, null));
		}
		
		//Set<Vertex> queue = new HashSet<>(dist);
		//Set<Vertex> queue = new LinkedHashSet<>(dist);  //copy the set
		
		int[] dist1 = new int[queue.size()];
		int[] previous = new int[queue.size()];
		for(int i = 0; i < queue.size(); i++) {
			dist1[i] = Integer.MAX_VALUE;
			previous[i] = -1;
		}
		dist1[sourcePort] = 0;
		
		while(!queue.isEmpty()) {
			
			//System.out.println("haha");
			int minVertex = -1;
			int minValue = Integer.MAX_VALUE;
			
			Vertex temp = null;
			for (Vertex v: queue) {
				if (dist1[v.getNode()] < minValue) {
					minValue = dist1[v.getNode()];
					minVertex = v.getNode();
					temp = v;
				}
			}
			
			if (minVertex == -1) {
				break;
			}
			queue.remove(temp);			
			
			if (minVertex == destPort) break;
					
			for (Edge e: adjList[minVertex]) {
				int alt = dist1[minVertex] + e.time;
				if(alt < dist1[e.node]) {
					dist1[e.node] = alt;
					previous[e.node] = minVertex;
				}
			}
		}
		
		return dist1[destPort];
		
		//return -1;
	}
	
// get the destination vertex, output its min time
//	Vertex output = new Vertex(0,0,null);
//	for (Vertex v: dist) {
//		if (v.getNode() == destPort) output = v;
//	}
	
	
	//===============================================================================================	
	//===============================================================================================	

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		Set<Vertex> queue2 = new LinkedHashSet<Vertex>();  
		for (int i = 0; i < n; i++) {
			if(i == sourcePort)
				queue2.add(new Vertex(i, Integer.MAX_VALUE, null));
			else
				queue2.add(new Vertex(i, 0, null));
		}
		
		//Set<Vertex> queue = new HashSet<>(dist);
		//Set<Vertex> queue = new LinkedHashSet<>(dist);  //copy the set
		
		int[] dist2 = new int[queue2.size()];
		int[] previous = new int[queue2.size()];
		for(int i = 0; i < queue2.size(); i++) {
			dist2[i] = 0;
			previous[i] = -1;
		}
		dist2[sourcePort] = Integer.MAX_VALUE;
		
		while(!queue2.isEmpty()) {
			
			//System.out.println("haha");
			int maxVertex = -1;
			int maxValue = 0;
			
			Vertex temp = null;
			for (Vertex v: queue2) { // check each path not yet removed
				if (dist2[v.getNode()] > maxValue) { // find the max
					maxValue = dist2[v.getNode()];
					maxVertex = v.getNode();
					temp = v;
				}
			}
			
			if (maxVertex == -1) {
				break;
			}
			queue2.remove(temp);  // already updated, remove it
			
			if (maxVertex == destPort) break; //finish the while loop
					
			for (Edge e: adjList[maxVertex]) {  // for each Edge connected to the maxVertex, update them
				int alt = dist2[maxVertex]; //+ e.capacity;
				if (dist2[maxVertex] > e.capacity) {   // if(alt > dist2[e.node])
					alt = e.capacity;
				}
				if (alt > dist2[e.node]) {
					dist2[e.node] = alt;
					previous[e.node] = maxVertex;
				}
				
			}
		}
		
		return dist2[destPort];
		
		//return -1;
	}

	//===============================================================================================	
	//===============================================================================================	
	
	
	
	// This function returns the neighboring ports of node.
	// This function is used to test if you have contructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		ArrayList<Edge> tmp = adjList[node];
		for (Edge e: tmp) {
			edges.add(e.node);
		}
		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}





/*
static void addEdge(Graph graph, int src, int dest) {  
    // Add an edge from src to dest. 
    graph.adjList[src].addFirst(dest);
     
    // Since graph is undirected, add an edge from dest
    // to src also
    graph.adjList[dest].addFirst(src);
}
*/



/*
 * //distance function from source to v
		//HashMap<Edge, Integer> dist = new HashMap<Edge, Integer>(); //?
		HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
		
		//Previous node in optimal path from source
		//HashMap<Edge, Edge> previous = new HashMap<Edge, Edge>(); //?
		HashMap<Integer, Edge> previous = new HashMap<Integer, Edge>();
		
		for (int i = 0; i < n; i++) { //? how get n
			dist.put(i, Integer.MAX_VALUE);
			previous.put(i, null); //?
		}
		dist.put(sourcePort, 0);
 * 
 */



/*
public int findCapOptimalPath(int sourcePort, int destPort) {
int minConstant = Integer.MIN_VALUE;
Set<Vertex> dist = new LinkedHashSet<Vertex>();  //this?
for (int i = 0; i < n; i++) {
	Vertex v2 = new Vertex(i, minConstant, null);
	dist.add(v2);
}
for (Vertex v: dist) {
	if (v.getNode() == sourcePort) {
		v.setDistance(Integer.MAX_VALUE);//0
		break;
	}
}
Set<Vertex> queue = new LinkedHashSet<>(dist);  //

Vertex maxVertex = new Vertex(0,0,null);
int maxValue = minConstant;

while(!queue.isEmpty()) {

	for (Vertex v: queue) {
		if (v.getDistance() > maxValue) {
			maxValue = v.getDistance();
			maxVertex = v;
		}
		if (maxVertex.getDistance() == minConstant) break;
		// Integer.MAX_VALUE the same value? need store the value?
		
	queue.remove(maxVertex);
	
	if (maxVertex.getNode() == destPort) break;
	}
	
	
	int s = maxVertex.getNode();
	for (Edge e: adjList[s]) {
		int alt2 = maxVertex.getDistance();
		if (e.capacity < maxVertex.getDistance())
			alt2 = e.capacity;
		//int alt2 = maxVertex.getDistance() + e.capacity; 
		
		Vertex vNew2 = new Vertex(0,0,null);
		for (Vertex v: dist) {
			if (v.getNode() == e.node) vNew2 = v;
		}
		
		if (alt2 > vNew2.getDistance()) { // <
			vNew2.setDistance(alt2);
			vNew2.setPrevVertex(maxVertex);
		}
	}
}

// get the destination vertex, output its max capacity
Vertex output2 = new Vertex(0,0,null);
for (Vertex v: dist) {
	if (v.getNode() == destPort) output2 = v;
}
return output2.getDistance();
//return -1;
}

*/
