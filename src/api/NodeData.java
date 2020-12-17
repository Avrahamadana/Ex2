package api;

import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NodeData implements node_data{
	
	private int key; // the key of node.
	private int tag; //the tag of node.
	private String info = "F"; //the data of node.
    private double Weight; //the Weight of node.
    private geo_location nodeLocation;
    
    public NodeData(int k ,double w) 
    {
    	this.key = k;
    	this.Weight = w;
    }
    public NodeData(int k) 
    {
    	this.key = k;
    }
	@Override
	public int getKey() {
		
		return this.key;
	}

	@Override
	public geo_location getLocation() {
		
		return this.nodeLocation;
	}

	@Override
	public void setLocation(geo_location p) {
		this.nodeLocation = new GeoLocation(p.x(), p.y(), p.z());
	    this.nodeLocation = p;
	}

	@Override
	public double getWeight() {
		return this.Weight;
	}

	@Override
	public void setWeight(double w) {
		this.Weight = w;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag =t;
	}
	
	@Override
	
	public String toString() 
	{
		return "" + this.nodeLocation;
	}
}
