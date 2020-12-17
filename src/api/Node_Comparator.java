package api;

import java.util.Comparator;



public class Node_Comparator implements Comparator<node_data>{
	@Override
	public int compare(node_data node1,node_data node2) {
		double ans = node1.getTag() - node2.getTag();
		if(ans>0) 
		{
			return 1;
		}
		else if(ans == 0) 
		{
			return 0;
		}
		else 
		{
			return -1;
		}
	}
}
