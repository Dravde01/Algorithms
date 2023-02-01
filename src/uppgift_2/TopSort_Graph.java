package uppgift_2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// Hugo Söderholm & Malte Enlund

public class TopSort_Graph {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphBuilder graph = new GraphBuilder();
		graph.Graph1_test();
		graph.Graph2_test();
		graph.Cities_test();
		graph.Names_test();
	}
}

class GraphComponent {
	HashMap<String, LinkedList<String>> graph = new HashMap<String, LinkedList<String>>();
	LinkedList<String> node;
	HashMap<String, Integer> indegree = new HashMap<String, Integer>();
	HashMap<String, Integer> topNum = new HashMap<String, Integer>();
	
	GraphComponent(){}
	
	void addNode(String label) {
		node = new LinkedList<String>();
		graph.put(label, node);
		indegree.put(label, 0);
	}
	
	void addEdge(String start, String end) {
		graph.get(start).add(end);
		indegree.put(end, indegree.get(end)+1);
	}
	
	void topSort(int testnr) {
		Queue<String> q = new PriorityQueue<String>();	// Skapa en tom kö
		int counter = 0;
		for (String n : graph.keySet()) {	// Sätt in noder med ingrad 0
			if (indegree.get(n) == 0) {
				q.add(n);
			}
		}
		while(!q.isEmpty()) {					// Tills kön är tom
			String nod = q.remove();			// Ta ut en nod
			topNum.put(nod, ++counter);			// Ge den ett ordningsnummer
			for (String w : graph.get(nod)) { 	// Uppdatera närliggande
				int check = indegree.get(w);
				check--;
				indegree.put(w, check);
				if (check == 0) {
					q.add(w);					// Sätt in i kön om ingrad = 0
				}
			}
		}
		if (counter != graph.keySet().size()) {
			System.out.println("Test #" + testnr + ": Cycle found.");
		}
		else System.out.println("Test #" + testnr + ": " + topNum.toString());
	}
}

class GraphBuilder {
	protected void Graph1_test() {
		GraphComponent Graph1 = new GraphComponent();
		
		Graph1.addNode("A");
		Graph1.addNode("B");
		Graph1.addNode("C");
		Graph1.addNode("D");
		Graph1.addNode("E");
		Graph1.addNode("F");
		Graph1.addNode("G");

		Graph1.addEdge("A","B");
		Graph1.addEdge("A","C");
		Graph1.addEdge("A","D");
		Graph1.addEdge("B","C");
		Graph1.addEdge("B","E");
		Graph1.addEdge("B","G");
		Graph1.addEdge("C","E");
		Graph1.addEdge("C","D");
		Graph1.addEdge("G","E");
		Graph1.addEdge("E","D");
		Graph1.addEdge("E","F");
		Graph1.addEdge("D","F");
		
		Graph1.topSort(1);
	}
	
	protected void Graph2_test() {
		GraphComponent Graph2 = new GraphComponent();
		
		Graph2.addNode("A");
		Graph2.addNode("B");
		Graph2.addNode("C");
		Graph2.addNode("D");
		Graph2.addNode("E");
		Graph2.addNode("F");
		
		Graph2.addEdge("A","B");
		Graph2.addEdge("A","C");
		Graph2.addEdge("A","D");
		Graph2.addEdge("B","C");
		Graph2.addEdge("B","E");
		Graph2.addEdge("C","E"); 
		Graph2.addEdge("D","C");
		Graph2.addEdge("D","F");
		Graph2.addEdge("E","D");
		Graph2.addEdge("E","F");
		
		Graph2.topSort(2);
	}
	
	protected void Cities_test() {
		GraphComponent Cities = new GraphComponent();
		
		Cities.addNode("Turku");
		Cities.addNode("Salo");
		Cities.addNode("Helsingfors");
		Cities.addNode("Porvoo");
		Cities.addNode("Raumo");
		Cities.addNode("Pori");
		Cities.addNode("Mikkeli");
		Cities.addNode("Tammerfors");
		Cities.addNode("Forssa");
		Cities.addNode("Tavastehus");
		Cities.addNode("Lahtis");
		Cities.addNode("Kotka");
		Cities.addNode("Villmanstrand");
		Cities.addNode("Imatra");

		Cities.addEdge("Turku","Salo");
		Cities.addEdge("Turku","Raumo");
		Cities.addEdge("Raumo","Pori");
		Cities.addEdge("Raumo","Forssa");
		Cities.addEdge("Pori","Tammerfors");
		Cities.addEdge("Tammerfors","Tavastehus");
		Cities.addEdge("Tavastehus","Lahtis");
		Cities.addEdge("Lahtis","Villmanstrand");
		Cities.addEdge("Salo","Helsingfors");
		Cities.addEdge("Helsingfors","Porvoo");
		Cities.addEdge("Porvoo","Villmanstrand");
		Cities.addEdge("Salo","Forssa");
		Cities.addEdge("Forssa","Tammerfors");
		Cities.addEdge("Mikkeli","Imatra");
		Cities.addEdge("Helsingfors","Lahtis");
		Cities.addEdge("Tammerfors","Lahtis");
		Cities.addEdge("Helsingfors","Kotka");
		Cities.addEdge("Lahtis","Mikkeli");
		Cities.addEdge("Villmanstrand","Kotka");
		Cities.addEdge("Villmanstrand","Imatra");
		Cities.addEdge("Kotka","Imatra");
		
		Cities.topSort(3);
	}
	
	protected void Names_test() {
		GraphComponent Names = new GraphComponent();
		
		Names.addNode("Anders");
		Names.addNode("Berit");
		Names.addNode("Christel");
		Names.addNode("Dani");
		Names.addNode("Eva");
		Names.addNode("Fredrik");
		Names.addNode("Greta");
		
		Names.addEdge("Anders","Berit");
		Names.addEdge("Anders","Christel");
		Names.addEdge("Anders","Dani");
		Names.addEdge("Berit","Christel");
		Names.addEdge("Berit","Eva");
		Names.addEdge("Berit","Greta");
		Names.addEdge("Christel","Eva");
		Names.addEdge("Christel","Dani");
		Names.addEdge("Greta","Eva");
		Names.addEdge("Eva","Dani");
		Names.addEdge("Eva","Fredrik"); 
		Names.addEdge("Dani","Fredrik");
		
		Names.topSort(4);
	}
}
