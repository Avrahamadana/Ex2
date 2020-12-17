package api;



public class GeoLocation implements geo_location {
    private double x;
	private double y;
	private double z;

    public GeoLocation(double d , double e , double f ) 
    {
       this.x = d;
       this.y = e;
       this.z = f;
    }
  
	@Override
	public double x() {
		return this.x;
	}

	@Override
	public double y() {
		return this.y;
	}

	@Override
	public double z() {
		return this.z;
	}
    
	@Override
	public double distance(geo_location g) {
		double x1 = Math.pow(g.x()-this.x, 2);
		double y1 = Math.pow(g.y()-this.y, 2);
		double z1 = Math.pow(g.z()-this.z, 2);
		return Math.sqrt(x1+y1+z1);
	}
  
	public String toString() 
	{
		return ""+this.x +","+this.y+","+this.z+" ";
	}
}
