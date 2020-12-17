package api;

import java.util.Arrays;
import java.util.Collection;



public class Test {

	public static void main(String[] args) {
		directed_weighted_graph g = new DWGraph_DS();

		g.addNode(new NodeData(0,0));
		g.addNode(new NodeData(1,0));
		g.addNode(new NodeData(2,0));
		//		g.addNode(new NodeData(3,0));

		g.connect(0, 1, 1);
//		g.connect(1, 0, 0.5);
		g.connect(1, 2, 2);
//		g.connect(2,0 , 1);
//		g.connect(2, 1, 0.5);
		//		g.connect(4, 3, 0);
		//		g.connect(2, 1, 0);
		//		g.connect(3, 1, 0);
		geo_location geo = new GeoLocation(31.888, 22.777,0.0);
		geo_location geo1 = new GeoLocation(35.79588, 18.123,0.0);
		geo_location geo2 = new GeoLocation(40.987, 25.11117,0.0);
		g.getNode(0).setLocation(geo);
		g.getNode(1).setLocation(geo1);
		g.getNode(2).setLocation(geo2);
		
//		System.out.println(geo.toString());
		directed_weighted_graph graphR = returnNodes(g);
		dw_graph_algorithms g1 = new DWGraph_Algo();
		g1.init(g);
//		System.out.println(g1);
		g1.save("graph.json");
//		System.out.println(g);
		dw_graph_algorithms copy = new DWGraph_Algo();
		copy.load("graph.json");
		System.out.println(g);
		System.out.println(copy);
//		System.out.println(g1);
//		System.out.println(copy.getGraph());
//		System.out.println(copy);
//		        System.out.println(g1.isConnected());
		System.out.println(g.getE(0));
		System.out.println(g.getE(2));
		        System.out.println(g1.shortestPathDist(0, 2));
		        System.out.println(g1.shortestPath(0, 2));
		//		System.out.println(g1.isConnected());
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
}
