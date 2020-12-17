package gameClient;
import java.awt.Point;
import java.io.IOException;
import java.io.LineNumberReader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.annotation.processing.SupportedSourceVersion;
import javax.management.Query;
import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;

public class Ex2 implements Runnable {
	private static MyFrame _win;
	private static Arena _ar;
	static List<CL_Pokemon> p;
	private static Hashtable<Integer, List<node_data>> agent_paths;
	private static Hashtable<Integer, CL_Pokemon> pokemons_status;
	private static double max_speed;
	private static dw_graph_algorithms graph;
	private static List<CL_Agent> listOfAgent;

	public static void main(String[] args)
	{
		agent_paths = new Hashtable<>();

		pokemons_status = new Hashtable<>();
		graph = new DWGraph_Algo();

		Thread client = new Thread(new Ex2());
		client.start();

	}

	@Override
	public void run() {
		int level = 0;
		game_service game = Game_Server_Ex2.getServer(level);

		directed_weighted_graph graph = game.getJava_Graph_Not_to_be_used();
		init(game);
		_ar.setAgents(listOfAgent);
		_ar.setPokemons(p);
		//		initRobotPath(graph);
		initRobotPath(graph);
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		int ind = 0;
		long dt = 100;

		try {
			JSONObject stringOfTheGame = new JSONObject(game.toString());
			JSONObject gameServer = stringOfTheGame.getJSONObject("GameServer");
			int numberOfAgents = gameServer.getInt("agents");
			while(game.isRunning()) 
			{
				System.out.println(_ar.getAgents());
				moveAgants(game, graph);
				System.out.println("bbbb    "+_ar.getAgents());
				try {
					if(ind%1==0) {_win.repaint();}
					Thread.sleep(dt);
					ind++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		String res = game.toString();
		System.out.println(res);
		System.exit(0);
	}

	private void init(game_service game) 
	{
		String pokemonsString = game.getPokemons();

		directed_weighted_graph graph = game.getJava_Graph_Not_to_be_used();

		_ar = new Arena();
		_ar.setGraph(graph);
		_ar.setPokemons(Arena.json2Pokemons(pokemonsString));

		_win = new MyFrame("test Ex2");
		_win.setSize(1000,700);
		_win.update(_ar);
		_win.setVisible(true);


		//		p = queuePokemons()
		try 
		{
			JSONObject node = new JSONObject(game.getGraph());
			JSONArray nodesArray = node.getJSONArray("Nodes");
			JSONArray edgesArray = node.getJSONArray("Edges");
			directed_weighted_graph g = buildGraph(nodesArray,node,edgesArray);
			
            System.out.println(g.edgeSize());
            
			System.out.println(game.getPokemons());
			JSONObject pok_s = new JSONObject(game.getPokemons());
			JSONArray pok_Array = pok_s.getJSONArray("Pokemons");
			p = Arena.json2Pokemons(game.getPokemons());
			//					queuePokemons(pok_Array,pok_s,g);

			JSONObject stringOfTheGame = new JSONObject(game.toString());
			JSONObject gameServer = stringOfTheGame.getJSONObject("GameServer");
			int numberOfAgents = gameServer.getInt("agents");

			ArrayList<CL_Pokemon> cl_po = Arena.json2Pokemons(game.getPokemons());
			for(int i=0; i<cl_po.size(); i++) 
			{
				Arena.updateEdge(p.get(i), g);
				System.out.println(p.get(i).get_edge());
			}

			System.out.println(p);
			for(int i=0; i<numberOfAgents; i++) 
			{
				CL_Pokemon pok = p.get(i);
				if(pok.getType()==-1)
				{
					game.addAgent(pok.get_edge().getSrc());
					listOfAgent(game);
					System.out.println("bbbb   "+listOfAgent);
					_ar.setAgents(listOfAgent);
				}
				else 
				{
					game.addAgent(pok.get_edge().getDest());
					listOfAgent(game);
					_ar.setAgents(listOfAgent);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<CL_Pokemon> queuePokemons(JSONArray PokemonsArray ,JSONObject Pokemons,directed_weighted_graph g) 
	{
		try {
			Pokemon_Comparator comp = new Pokemon_Comparator();
			PriorityQueue<CL_Pokemon> qPokemons =new PriorityQueue<CL_Pokemon>(comp);
			List<CL_Pokemon> pok = new LinkedList<CL_Pokemon>();
			for(int i=0;i<PokemonsArray.length();i++) {
				JSONObject pp;
				pp = PokemonsArray.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				//double s = 0;//pk.getDouble("speed");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				Arena.updateEdge(f,g);
				qPokemons.add(f);
				pok.add(f);
				pok.sort(comp);
			}
			return pok;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void moveAgants(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		System.out.println(log.get(0));
		CL_Agent L = log.remove(0);
		EdgeData d = new EdgeData(L.getSrcNode(),-1,0);
		L.set_curr_edge(d);
		log.add(0, L);
		//ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();
		String fs =  game.getPokemons();
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
		for(int i=0;i<log.size();i++) {
			CL_Agent ag = log.get(i);
			System.out.println("agetn ->"+ag);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(dest==-1) {
				dest = nextNode(game,src);
				game.chooseNextEdge(ag.getID(), dest);
				System.out.println("Edge"+ag.get_curr_edge());
				System.out.println("first ->"+lg);


				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
	}

	//	private void moveAgants(game_service game, directed_weighted_graph graph ,int numberOfAgents) 
	//	{
	//		String lg = game.move();
	//		List<CL_Agent> log = Arena.getAgents(lg, graph);
	////		System.out.println(log.get(0));
	////		CL_Agent L = log.remove(0);
	////		EdgeData d = new EdgeData(L.getSrcNode(),-1,0);
	////		L.set_curr_edge(d);
	////		log.add(0, L);
	//
	//		_ar.setAgents(log);
	//		System.out.println("aaaa     "+lg);
	//		System.out.println(p.get(0).get_edge());
	//
	//
	//		String fs =  game.getPokemons();
	//		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
	//		_ar.setPokemons(ffs);
	//		for(int i=0; i<numberOfAgents; i++) 
	//		{
	//			CL_Agent agent = _ar.getAgents().get(i);
	//			int dest = agent.get_curr_edge().getDest();
	//			if(agent.get_curr_edge().getDest()== -1) 
	//			{
	//				if(agent.getSpeed() > max_speed) 
	//				{
	//					dest = nextNodeByDist(i);
	//				}
	//				else 
	//				{
	//					dest = nextNodeByValue(agent.getID());
	//				}
	//			}
	//			max_speed = Math.max(max_speed, agent.getSpeed());
	//			CL_Pokemon pok = pokemons_status.get(agent.getID());
	//			if(agent.getSpeed()>=2) 
	//			{
	//				if(agent.get_curr_edge().getSrc() == pok.get_edge().getSrc() && dest == pok.get_edge().getDest()) 
	//				{
	//					node_data src = _ar.getGraph().getNode(pok.get_edge().getSrc());
	//					node_data dst = _ar.getGraph().getNode(pok.get_edge().getDest());
	//					double dist_src_fruit = src.getLocation().distance(pok.getLocation());
	//					double dist_src_dst = src.getLocation().distance(dst.getLocation());
	//					double n = dist_src_fruit / dist_src_dst;
	//					double w = pok.get_edge().getWeight();
	//					double s = agent.getSpeed();
	//					int dt = (int) (w * n / s * 100);
	//					//					client.dt = dt;
	//				}
	//			}
	//			_ar.setAgents(listOfAgent);
	//			game.chooseNextEdge(i, dest);
	//		}
	//	}

	private static  int nextNode(game_service game ,int src) {
		JSONObject stringOfTheGame;
		int ans = 0;
		try {
			stringOfTheGame = new JSONObject(game.toString());
			JSONObject gameServer = stringOfTheGame.getJSONObject("GameServer");
			int numberOfAgents = gameServer.getInt("agents");
			for(int i=0; i<numberOfAgents; i++) {
				CL_Agent agent = _ar.getAgents().get(i);
				int dest = agent.get_curr_edge().getDest();
				if(agent.getSpeed() > max_speed) 
				{
					dest = nextNodeByDist(i);
				}
				else 
				{
					dest = nextNodeByValue(agent.getID());
				}
				ans = dest;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ans;
		//		int ans = -1;
		//		Collection<edge_data> ee = g.getE(src);
		//		Iterator<edge_data> itr = ee.iterator();
		//		int s = ee.size();
		//		int r = (int)(Math.random()*s);
		//		int i=0;
		//		while(i<r) {itr.next();i++;}
		//		ans = itr.next().getDest();
		//		return ans;

	}

	private static  int nextNodeByValue(int rid) {
		CL_Agent agent = listOfAgent.get(rid);
		List<node_data> tmp = agent_paths.get(rid);

		if (tmp.isEmpty()) {
			pokemons_status.remove(rid);
			synchronized (_ar.getPokemons()) {
				if (_ar.getPokemons().size() > 0) {
					CL_Pokemon pok = p.remove(0);
					tmp = graph.shortestPath(agent.get_curr_edge().getDest(), pok.get_edge().getSrc());
					node_data dest = _ar.getGraph().getNode(pok.get_edge().getDest());
					tmp.add(dest);
					agent_paths.put(agent.getID(), tmp);
					pokemons_status.put(rid, pok);
				}
			}
		}
		node_data n = tmp.get(1);
		tmp.remove(1);
		return n.getKey();
	}

	private static  int nextNodeByDist(int rid) 
	{
		System.out.println(_ar.getAgents());
		CL_Agent agent = _ar.getAgents().get(rid);
		List<node_data> tmp = agent_paths.get(rid);
		System.out.println(agent_paths);
		System.out.println(tmp.get(0));
		System.out.println(tmp.get(1));
		if (tmp.get(0)==null) {
			pokemons_status.remove(rid);
			synchronized (_ar.getPokemons()) {
				if (_ar.getPokemons().size() > 0) {
					CL_Pokemon pok = findClosestPokemon(agent);
					tmp = graph.shortestPath(agent.get_curr_edge().getSrc(), pok.get_edge().getSrc());
					node_data dest = _ar.getGraph().getNode(pok.get_edge().getDest());
					tmp.add(dest);
					agent_paths.put(agent.getID(), tmp);
					pokemons_status.put(rid, pok);
				}
			}
		}

		node_data n = tmp.get(0);
		tmp.remove(0);
		return n.getKey();
	}

	private static CL_Pokemon findClosestPokemon(CL_Agent agent) {
		double min_dist = Double.MAX_VALUE;
		CL_Pokemon pokAns = null;
		for (int i = 0; i < _ar.getPokemons().size(); i++) {
			CL_Pokemon pok = _ar.getPokemons().get(i);
			if (pok.get_edge().getSrc() == agent.get_curr_edge().getSrc()) {
				return pok;
			}

			if (pokemons_status.values().contains(pok))
				continue;

			double dist = graph.shortestPathDist(agent.get_curr_edge().getSrc(), pok.get_edge().getSrc());
			if (dist < min_dist) {
				min_dist = dist;
				pokAns = pok;
			}
		}
		return pokAns;
	}

	public static directed_weighted_graph buildGraph(JSONArray VertexArray ,JSONObject Vertex,JSONArray EdgesArray) 
	{
		try {
			directed_weighted_graph graph = new DWGraph_DS();
			for(int i=0; i<VertexArray.length(); i++) 
			{
				Vertex = VertexArray.getJSONObject(i);
				int n = Vertex.getInt("id");
				String pos = (String)Vertex.get("pos");
				node_data node = new NodeData(n);
				String [] s = pos.split(",");
				node.setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
				graph.addNode(node);
			}
			JSONObject edges = new JSONObject();
			for(int i=0; i<EdgesArray.length(); i++) 
			{
				edges = EdgesArray.getJSONObject(i);
				int src = edges.getInt("src");
				int dest = edges.getInt("dest");
				double w = edges.getDouble("w");
				graph.connect(src, dest, w);
			}
			return graph;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private  void listOfAgent(game_service game)
	{
		List<CL_Agent> list1 = new LinkedList<>();
		try 
		{
			Agent_Comparator comp = new Agent_Comparator();
			JSONObject agents = new JSONObject(game.getAgents());
			JSONArray agentsArray = agents.getJSONArray("Agents");
			for(int i=0; i<agentsArray.length(); i++) 
			{
				JSONObject agentsObj = agentsArray.getJSONObject(i);

				JSONObject j = agentsObj.getJSONObject("Agent");
				//				agentsObj.get
				int id = j.getInt("id");
				double v = j.getDouble("value");
				int src = j.getInt("src");
				int dest = j.getInt("dest");
				double speed = j.getDouble("speed");
				String pos = j.getString("pos");
				String [] s = pos.split(",");
				geo_location pos1 = new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2]));
				CL_Agent agent = new CL_Agent(id, v,src,dest, speed, pos1);
				list1.add(agent);
				list1.sort(comp);
				listOfAgent = list1;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initRobotPath(directed_weighted_graph g)
	{
		graph.init(g);
		for (int i = 0; i < _ar.getAgents().size(); i++) {
			CL_Agent agent = _ar.getAgents().get(i);
			CL_Pokemon pok = _ar.getPokemons().get(i);
			System.out.println("The shortPath ->"+graph.shortestPath(agent.get_curr_edge().getSrc(), pok.get_edge().getDest()));
			List<node_data> tmp = graph.shortestPath(agent.get_curr_edge().getSrc(), pok.get_edge().getDest());
			agent_paths.put(agent.getID(), tmp);
			pokemons_status.put(agent.getID(), pok);
			agent.set_curr_fruit(pok);
			agent.setGraph(g);
			agent.setCurrNode(pok.get_edge().getSrc());
			agent.setNextNode(pok.get_edge().getDest());
		}
	}

}
