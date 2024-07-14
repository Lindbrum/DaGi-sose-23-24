package ${package};

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Sum {

	@WebMethod
	public int sum(int a, int b);

	@WebMethod
	public int safeSum(int a, int b) throws SumException;
	
}
