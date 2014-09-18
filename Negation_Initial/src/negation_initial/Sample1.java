package negation_initial;

import java.util.List;

import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;


public class Sample1 {
	public static void main(String args[]){
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		//api.
		List<Result> resultList = api.processCitationsFromString
				//("Cerebral blood flow [CBF] in newborn infants is.");
				("Edema is absent.");
		Result result = resultList.get(0);
		List<AcronymsAbbrevs> aaList = null;
		try {
			aaList = result.getAcronymsAbbrevs();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (aaList.size() > 0) {
		System.out.println("Acronyms and Abbreviations:");
		for (AcronymsAbbrevs e: aaList) {
		System.out.println("Acronym: " + e.getAcronym());
		System.out.println("Expansion: " + e.getExpansion());
		System.out.println("Count list: " + e.getCountList());
		System.out.println("CUI list: " + e.getCUIList());
		}
		} else {
		System.out.println(" None.");
		}
	}

}
