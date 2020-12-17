package gameClient;

import java.util.Comparator;

public class Agent_Comparator implements Comparator<CL_Agent>{

	@Override
	public int compare(CL_Agent o1, CL_Agent o2) {
		double ans = o2.getValue()-o1.getValue();
		if(ans>0) 
		{
			return 1;
		}
		else if(ans ==0) 
		{
			return 0;
		}
		
		return -1;
	}

}
