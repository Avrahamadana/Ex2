package api;



public class EdgeData implements edge_data{
	private int src; // the source of Edge.
	private double w; // the weight of Edge.
	private int dest; // the target of Edge.
	private String info;
	private int tag;
	
	public EdgeData(int s , int d ,double w ) 
	{
		this.src = s;
		this.dest = d;
		this.w = w;
	}
	
	public EdgeData(EdgeData n) 
	{
		this.src = n.src;
		this.dest = n.dest;
		this.w = n.w;
		this.info = n.info;
		this.tag = n.tag;
	}
	
	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.dest;
	}

	public void setWeight(double weight) {
		this.w = weight;
	}

	@Override
	public double getWeight() {
		
		return this.w;
	}

	@Override
	public String getInfo() {
		
		return this.info;
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
       this.tag = t;		
	}

	@Override
	public String toString() {
		return "[src: " + src + ", w: " + w + ", dest: " + dest + "]";
	}
}
