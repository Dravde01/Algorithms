package uppgift_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Hugo Söderholm & Malte Enlund

public class Dijkstra_GraphWord {
	public static void main(String[] args) throws IOException {
		Scanner nodeInput = new Scanner(System.in);
		System.out.println("Enter the first word:");
		String start = nodeInput.nextLine();
		System.out.println("Enter the last word:");
		String end = nodeInput.nextLine();
		
		DijkstraKone dijkstraKone = new DijkstraKone();
		dijkstraKone.readFile();
		dijkstraKone.buildEdges();
		dijkstraKone.dijkstra(start);
		dijkstraKone.printPath(end);
	}
}

class DijkstraKone {
	private HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> relatives;
	private HashMap<String, Boolean> known = new HashMap<String, Boolean>();
	private HashMap<String, Integer> distance = new HashMap<String, Integer>();
	private HashMap<String, String> path = new HashMap<String, String>();
	
	private void setKnown(String s, boolean b) {
		known.put(s, b);
	}
	private void setDist(String s, int i) {
		distance.put(s, i);
	}
	private void setPath(String s, String g) {
		path.put(s, g);
	}
	
	private void addNode(String label) {
		relatives = new ArrayList<String>();
		graph.put(label, relatives);
		known.put(label, false);
		distance.put(label, Integer.MAX_VALUE);
		path.put(label, null);
	}
	
	private void addEdge(String start, String end) {
		graph.get(start).add(end);
	}
	
	protected void dijkstra(String start) {
		setDist(start, 0);
		while (true) {
			int max_path = Integer.MAX_VALUE;
			String check = null;
			for (String s : distance.keySet()) {
				if (distance.get(s) < max_path && !known.get(s)) {
					check = s;
					max_path = distance.get(s);
				}
			}
			if (check == null) {
				break;
			}
			setKnown(check, true);
			for (String neighbor : graph.get(check)){
				if (!known.get(neighbor)) {
					int c_dist = distance.get(check);
					int n_dist = distance.get(neighbor);
					if (c_dist + 1 < n_dist){
						setDist(neighbor, c_dist + 1);
						setPath(neighbor, check);
					}
				}
			}
		}
	}
	
	protected void printPath(String end) {
		if (path.get(end) != null) {
			printPath(path.get(end));
			System.out.println(" to ");
		}
		if (distance.get(end) == Integer.MAX_VALUE) {
			System.out.println("No path exists! D:");
		}
		else System.out.println(end);
	}
	
	protected void readFile() throws IOException {
		// OBS! Ersätt pathen till textfilen med egna pathen!
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\hugos\\eclipse-workspace\\Algoritmer\\src\\uppgift_3\\Words.txt"));
		String line = null;
		while ((line = reader.readLine()) != null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(line);
			String word = stringBuilder.toString();
			addNode(word);
		}
		reader.close();
	}

	protected void buildEdges() {
		for (String s : graph.keySet()) {
			char[] charArray = s.toCharArray();
			for (String check : graph.keySet()) {
				int indexCounter = 0;
				int matchCounter = 0;
				for (char c : check.toCharArray()) {
					if (charArray[indexCounter] == c) {
						matchCounter++;
					}
					indexCounter++;
				}
				if (matchCounter == 3) {
					addEdge(s, check);
				}
			}
		}
	}
}
