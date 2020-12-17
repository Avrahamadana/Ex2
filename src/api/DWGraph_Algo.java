package api;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class DWGraph_Algo implements dw_graph_algorithms{
	directed_weighted_graph graph = new DWGraph_DS();
	HashMap<Integer, Double> weightOfEdge = new HashMap<>();
	HashMap<Integer, Integer> prev = new HashMap<>();

	@Override
	public void init(directed_weighted_graph g) {
		this.graph = g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return graph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copy = new DWGraph_DS();

		for (node_data node: graph.getV()) {

			copy.addNode(node);
		}

		for (node_data node_src : graph.getV()) {

			for(edge_data node_dest : graph.getE(node_src.getKey())) {

				copy.connect(node_src.getKey(), node_dest.getDest(), node_dest.getWeight());
			}
		}
		return copy;
	}

	@Override
	public boolean isConnected(){
		boolean b = bfs(graph);
		directed_weighted_graph reversGraph = returnNodes(graph);
		b = bfs(reversGraph);
		return b;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		return Dijkstra(src,dest);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		LinkedList<node_data> list = new LinkedList<node_data>();
		double distance = this.shortestPathDist(src, dest);
		if (distance == Double.MAX_VALUE)
			return null;
		node_data nDest = this.graph.getNode(dest);
		if (nDest == null)
			return null;
		while (prev.get(nDest.getKey()) != null && nDest.getKey() != src) {
			list.add(nDest);
			System.out.println(prev.get(nDest.getKey()));
			node_data prev1 = new NodeData(prev.get(nDest.getKey()),0);
			nDest = prev1;
		}
		list.add(this.graph.getNode(src));

		return reverseList(list);
	}

	private static LinkedList<node_data> reverseList(LinkedList<node_data> list) {
		LinkedList<node_data> rList = new LinkedList<node_data>();
		while (!list.isEmpty()) {
			rList.add(list.getLast());
			list.removeLast();
		}
		return rList;
	}

	@Override
	public boolean save(String file) {
		try {
			JSONObject edgesObj = new JSONObject();
			JSONArray Edges = new JSONArray();
			JSONObject nodeObj = new JSONObject();
			JSONArray Nodes = new JSONArray();
			JSONObject AllObj = new JSONObject();

			Collection<node_data> node = graph.getV();
			for (node_data node_data : node) {
				edgesObj = new JSONObject();
				Collection<edge_data> edges = graph.getE(node_data.getKey());
				for (edge_data edge : edges) {
					edgesObj.put("src", edge.getSrc());
					edgesObj.put("w", edge.getWeight());
					edgesObj.put("dest", edge.getDest());
					Edges.put(edgesObj);
				}
			}

			AllObj.put("Edges", Edges);

			for (node_data node_data : node) {
				nodeObj = new JSONObject();
				String str = ""+node_data.getLocation();

				nodeObj.put("pos", str);

				nodeObj.put("id", node_data.getKey());
				Nodes.put(nodeObj);
				str ="";
			}

			AllObj.put("Nodes", Nodes);
			try 
			{
				PrintWriter pw = new PrintWriter("graph.json");
				pw.write(AllObj.toString());
				pw.close();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean load(String file) {
		try 
		{
			Scanner scanner = new Scanner(new File(file));
			String jsonString = scanner.useDelimiter("\\A").next();
			scanner.close();

			JSONObject vertex = new JSONObject();
			JSONObject edges = new JSONObject();
			JSONObject jsonObject = new JSONObject(jsonString);

			JSONArray vertexArray = jsonObject.getJSONArray("Nodes");

			for(int i=0; i<vertexArray.length(); i++) 
			{
				vertex = vertexArray.getJSONObject(i);
				int n = vertex.getInt("id");
				String pos = (String)vertex.get("pos");
				node_data node = new NodeData(n);
				String [] s = pos.split(",");
				node.setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
				graph.addNode(node);
			}

			JSONArray edgesArray = jsonObject.getJSONArray("Edges");
			for(int i=0; i<edgesArray.length(); i++) 
			{
				edges = edgesArray.getJSONObject(i);
				int src = edges.getInt("src");
				int dest = edges.getInt("dest");
				double w = edges.getDouble("w");
				graph.connect(src, dest, w);
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public boolean bfs(directed_weighted_graph g) 
	{
		if (g == null) return true;
		if(g.nodeSize() == 0) return true;
		Queue<node_data> q = new LinkedList<node_data>();
		Iterator <node_data> it =  g.getV().iterator();
		boolean flag = true;
		//if(g.nodeSize() == 0) return true;
		q.add(it.next());
		while(!q.isEmpty()) {
			node_data tmp = q.poll();
			if(tmp.getInfo() != "b") tmp.setInfo("b");
			for(edge_data t : g.getE(tmp.getKey())) {
				if(t.getInfo()!="b" && t.getInfo()!="g") {
					q.add(graph.getNode(t.getDest()));
					graph.getNode(t.getDest()).setInfo("g");
					t.setInfo("g");
				}
			}
		}
		for(node_data n : g.getV()) { //O(|v|)
			if (n.getInfo() != "b") flag = false;
			n.setInfo("");
		}
		return flag;
	}

	public static directed_weighted_graph returnNodes(directed_weighted_graph g) 
	{
		directed_weighted_graph revers = new DWGraph_DS();
		Collection<node_data> nodes = g.getV();
		for (node_data node : nodes) {
			revers.addNode(node);
		}

		for (node_data node : nodes) {
			Collection<edge_data> edges = g.getE(node.getKey());
			for (edge_data edge : edges) {
				revers.connect(edge.getDest(), node.getKey(), 0);
			}
		}
		return revers;
	}

	public  double Dijkstra(int src , int dest) 
	{
		if(graph.getE(src).isEmpty()) return -1;
		HashMap<Integer, HashMap<Integer,Double>> copyNi = new HashMap<>();

		for(node_data n : graph.getV()) {  //------------>sets all the node TAGS to an infinit value;
			Collection<edge_data> Ni = graph.getE(n.getKey());
			for (edge_data edge : Ni) {
				copyNi.put(n.getKey(), new HashMap<>());
				copyNi.get(n.getKey()).put(edge.getDest(), edge.getWeight());
			}
			n.setWeight(Double.POSITIVE_INFINITY);
		}
		graph.getNode(src).setWeight(0);
		Node_Comparator comp = new Node_Comparator();

		PriorityQueue<node_data> pq = new PriorityQueue<node_data>(comp);
		pq.add(graph.getNode(src));
		while(!pq.isEmpty()) {
			node_data tmp = pq.poll(); //first element got polled out of the Queue
			if(tmp.getInfo() != "visited") tmp.setInfo("visited");
			for(edge_data n : graph.getE(tmp.getKey())) {
				if(n.getInfo() != "visited") {
					if((tmp.getWeight() + n.getWeight())< graph.getNode(n.getDest()).getWeight()) {
						graph.getNode(n.getDest()).setWeight(tmp.getWeight() + n.getWeight());
						pq.add(new NodeData(n.getDest(),n.getWeight()));
						prev.put(n.getDest(), tmp.getKey());
					}
				}
			}
		}
		double dist = graph.getNode(dest).getWeight();
		for(node_data n : graph.getV()) {
			n.setInfo(null);
			n.setTag(0);
		}
		return dist;
	}
	public String toString() 
	{
		return ""+this.graph;
	}
}
