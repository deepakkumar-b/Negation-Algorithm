package negation_initial;

import java.util.List;

import gov.nih.nlm.nls.metamap.ConceptPair;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Result;

public class Sample2 {
	public static void main(String args[]) throws Exception {

		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");

		List<Result> resultList = api
				.processCitationsFromString("Edema is absent.");
		Result result = resultList.get(0);
		List<Negation> negList = result.getNegations();
		if (negList.size() > 0) {
			System.out.println("Negations:");
			for (Negation e : negList) {
				System.out.println("type: " + e.getType());
				System.out.print("Trigger: " + e.getTrigger() + ": [");
				for (Position pos : e.getTriggerPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
				System.out.print("ConceptPairs: [");
				for (ConceptPair pair : e.getConceptPairList()) {
					System.out.print(pair + ",");
				}
				System.out.println("]");
				System.out.print("ConceptPositionList: [");
				for (Position pos : e.getConceptPositionList()) {
					System.out.print(pos + ",");
				}
				System.out.println("]");
			}
		} else {
			System.out.println(" None.");
		}

	}

}
