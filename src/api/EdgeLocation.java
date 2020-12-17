package api;

public class EdgeLocation implements edge_location{
	private EdgeData e;
	private double location;
	
	public EdgeLocation(double l) 
	{
		this.location =l;
	}
    
	@Override
	public edge_data getEdge() {
		
		return e;
	}

	@Override
	public double getRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

}
