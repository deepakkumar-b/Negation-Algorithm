package negation_initial;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;

import java.util.List;


public class Sample4 {
	public static void main(String args[]){
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		//api.
		List<Result> resultList = api.processCitationsFromString
				("Scorpio String");
		
		Result result = resultList.get(0);
		String machineOutput = result.getMachineOutput();
		
		System.out.println(machineOutput);
	}

}
