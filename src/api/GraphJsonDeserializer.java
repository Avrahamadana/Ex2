package api;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map.Entry;
import javax.swing.plaf.synth.SynthScrollBarUI;

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;


public class GraphJsonDeserializer implements JsonDeserializer<directed_weighted_graph>{

	@Override
	public directed_weighted_graph deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
	{
		directed_weighted_graph graph = new DWGraph_DS();
//		JSONObject jsonObject = json.getAsJsonObject();
//		JSONArray vertxObject = jso
		//		JsonArray vertexObject = jsonObject.getAsJsonArray("Nodes");
		//		JsonArray edgesObject = jsonObject.get("Edges").getAsJsonArray();
		//		System.out.println(vertexObject);

		//	    String s = edgesObject.getJsonArray(0);
//		for(int i=0; i<edgesObject.size(); i++) 
//		{
//			jsonObject = edgesObject.get
//					JsonElement j = jsonObject.get("pos");
//			String s = (String)j.getAsString();
//			System.out.println(s);
//		}

		//		JsonObject vertexObject = jsonObject.get("Nodes").getAsJsonObject();

		//		for(Entry<String ,JsonElement> set : vertexObject.entrySet()) 
		//		{
		//			JsonElement jsonValue = set.getValue();
		//			int key = jsonValue.getAsJsonObject().get("key").getAsInt();
		//			node_data node = new NodeData(key);
		//			graph.addNode(node);
		//		}
		//
		//		for(Entry<String ,JsonElement> set : vertexObject.entrySet()) 
		//		{
		//			JsonElement jsonValue = set.getValue();
		//			int key = jsonValue.getAsJsonObject().get("key").getAsInt();
		//			int dest = jsonValue.getAsJsonObject().get("dest").getAsInt();
		//			double w = jsonValue.getAsJsonObject().get("w").getAsInt();
		//			graph.connect(key, dest, w);
		//		}
		//
		//		vertexObject = jsonObject.get("Edges").getAsJsonObject();
		//		for(Entry<String ,JsonElement> set : vertexObject.entrySet()) 
		//		{
		//			JsonElement jsonValue = set.getValue();
		//			int key = jsonValue.getAsJsonObject().get("key").getAsInt();
		//			String sLocation = jsonValue.getAsJsonObject().get("pos").getAsString();
		//			String [] s = sLocation.split(",");
		//			graph.getNode(key).setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
		//
		//		}
		//		System.out.println(graph);
		return graph;
	}

	
}
