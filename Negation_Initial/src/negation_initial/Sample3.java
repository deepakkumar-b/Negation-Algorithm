package negation_initial;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

import java.util.List;

public class Sample3 {
	public static void main(String args[]) throws Exception {
		// scorpion sting

		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		//Edema
		//cancer of the bile duct is absent.
		List<Result> resultList = api
				.processCitationsFromString("agenesis of the gallbladder a dangerously misdiagnosed malformation");
		Result result = resultList.get(0);
		for (Utterance utterance : result.getUtteranceList()) {
			System.out.println("Utterance:");
			System.out.println(" Id: " + utterance.getId());
			System.out.println(" Utterance text: " + utterance.getString());
			System.out.println(" Position: " + utterance.getPosition());
			for (PCM pcm : utterance.getPCMList()) {

				System.out.println("Phrase:");
				System.out.println(" text: " + pcm.getPhrase().getPhraseText());

				System.out.println("Candidates:");
				for (Ev ev : pcm.getCandidateList()) {
					//System.out.println(" Candidate:");
					//System.out.println(" Score: " + ev.getScore());
					//System.out.println(" Concept Id: " + ev.getConceptId());
					System.out.println(" Concept Name: " + ev.getConceptName());
					/*System.out.println(" Preferred Name: " + ev.getPreferredName());
					System.out.println(" Matched Words: " + ev.getMatchedWords());
					System.out.println(" Semantic Types: " + ev.getSemanticTypes());
					System.out.println(" MatchMap: " + ev.getMatchMap());
					System.out.println(" MatchMap alt. repr.: " + ev.getMatchMapList());
					System.out.println(" is Head?: " + ev.isHead());
					System.out.println(" is Overmatch?: " + ev.isOvermatch());
					System.out.println(" Sources: " + ev.getSources());
					System.out.println(" Positional Info: " + ev.getPositionalInfo());*/
					//System.out.println("Mappings:");
					for (Mapping map: pcm.getMappingList()) {
					//System.out.println(" Map Score: " + map.getScore());
					for (Ev mapEv: map.getEvList()) {
					//System.out.println(" Score: " + mapEv.getScore());
					//System.out.println(" Concept Id: " + mapEv.getConceptId());
					//System.out.println(" Concept Name: " + mapEv.getConceptName());
					/*System.out.println(" Preferred Name: " + mapEv.getPreferredName());
					System.out.println(" Matched Words: " + mapEv.getMatchedWords());
					System.out.println(" Semantic Types: " + mapEv.getSemanticTypes());
					System.out.println(" MatchMap: " + mapEv.getMatchMap());
					System.out.println(" MatchMap alt. repr.: " + mapEv.getMatchMapList());
					System.out.println(" is Head?: " + mapEv.isHead());
					System.out.println(" is Overmatch?: " + mapEv.isOvermatch());
					System.out.println(" Sources: " + mapEv.getSources());
					System.out.println(" Positional Info: " + mapEv.getPositionalInfo());*/
					}
					}

				}

			}

		}
	}
}
