package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class DWGraph_DS implements directed_weighted_graph{
	@SerializedName("Edges ")
	HashMap<Integer,HashMap<Integer, EdgeData>> Ni;
	@SerializedName("Nodes ")
	HashMap<Integer, node_data> nodes;
	private int MC;
	private int nodeSize;
	private int edgeSize;

	public DWGraph_DS() 
	{
		nodes = new HashMap<>();
		Ni = new HashMap<>();
	}
	@Override
	public node_data getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		if(Ni.get(src) != null && Ni.get(src).get(dest) != null) 
		{
			return Ni.get(src).get(dest);
		}
		return null;
	}

	@Override
	public void addNode(node_data n) {
		if(nodes.size() != 0) 
		{
			if(nodes.containsValue(n)) 
			{
				return;
			}
		}
		nodes.put(n.getKey(), n);
		Ni.put(n.getKey(),new HashMap<>());
		nodeSize++;
	}

	/**
	 * This function checks if node1 and node2 are neighbors
	 * @param node1
	 * @param node2
	 * Complications O(1).
	 */

	public boolean hasEdge(int node1, int node2) {

		if (nodes.get(node1) == null || nodes.get(node2) == null || Ni.get(node1) == null || Ni.get(node1).get(node2) == null) {
			return false;
		}
		return true;
	}


	@Override
	public void connect(int src, int dest, double w) {
		if(nodes.get(src) == null || nodes.get(dest) == null) 
		{
			return;
		}
		if(w>=0) {
			if(!hasEdge(src, dest)) {
				EdgeData e = new EdgeData(src, dest, w);
				Ni.get(src).put(dest, e);
				MC++;
				edgeSize++;
			}
			else 
			{
				return;
			}
		}
		this.MC++;
	}

	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id){
		List<edge_data> list = new LinkedList<edge_data>(); 
		if (Ni.get(node_id) != null) {
			Collection<EdgeData> ed = Ni.get(node_id).values();
			for (EdgeData edge : ed) {
				list.add(edge);
			}
		}
		return list;
	}

	@Override
	public node_data removeNode(int key) {
		node_data node = getNode(key);

		if (node == null) {
			return null;
		}

		Collection<node_data> nodeNi =  nodes.values();
		for (node_data node_info : nodeNi) {
			removeEdge(key,node_info.getKey());
		}

		if (Ni.get(key) != null) {
			Ni.remove(key);
			edgeSize--;
		}

		nodes.remove(key);

		MC++;
		nodeSize--;

		return node;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		if (!hasEdge(src, dest)) {
			return null;
		}
		edge_data e = getEdge(src,dest);
		Ni.get(src).remove(dest);
		this.edgeSize--;
		MC++;
		return e;
	}

	@Override
	public int nodeSize() {
		return this.nodeSize;
	}

	@Override
	public int edgeSize() {
		return this.edgeSize;
	}

	@Override
	public int getMC() {
		return this.MC;
	}
	
	@Override
	public String toString() 
	{
	
		String str = "Nodes ";
		Collection<node_data> node = nodes.values();
		str+="[";
		for (node_data node_data : node) {
			str +="{"+node_data.getLocation()+",id:"+node_data.getKey()+"}";
		}
		str+="]";
		String str1 = "{Edges ";
		for (node_data node_data : node) 
		{
			for(edge_data edge : Ni.get(node_data.getKey()).values()) 
			{
				str1 += edge;
			}
		}
		String str2 = str1+"," +str+"}";
		
		return str2;
	}
}
