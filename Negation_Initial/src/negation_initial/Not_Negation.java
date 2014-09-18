package negation_initial;

import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Map;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Deepakkumar
 */


public class Not_Negation {

    /*
    1. read file into string S
    2. split S into sentences {s1, s2, .... , s_n}
    3. for each sentence s : {s1, ... s_n}
            check for "no", "negative for" and "no evidence of"
            switch (based on which negation is found) {
                case "no":
                    phrases = list of phrases of s (as outputted by metamap api)
                    for each phrase p : phrases {
                        if (p.contains("no") get highest scored concept of p;
                    }
                case "negative for": ....
                case "no evidence of": ....
                default: throw some error
            }
    */
static final Pattern p = Pattern.compile("(no )(.*)");
static final Pattern p1 = Pattern.compile("(negative for)(.*)");
static final Pattern p2 = Pattern.compile("(negative)(.*)");
static final Pattern p_noevi = Pattern.compile("(no evidence)(.*)");

public static void negation_detection(String filelocation) throws FileNotFoundException, IOException, Exception{
	
        String finalLine = null;
        String fileName=null;
        String line = null;
        int no_p = 0;
        int neg_p = 0;
        int noevi_p = 0;
        int next_phrase = 0; // for "negative"
        int no_evi_phrase = 0; //for "no evidence"
        TreeMap<Integer, String> sort_scores = new TreeMap<Integer, String>();
        TreeMap<Integer, String> sort_scores1 = new TreeMap<Integer, String>();
        TreeMap<Integer, String> sort_scores2 = new TreeMap<Integer, String>();

        MetaMapApi api = new MetaMapApiImpl();
	api.setOptions("-y"); //-y => word_sense_disambiguation : use WSD
        
        final File folder = new File(filelocation);
	
        boolean contains = true;
        for (final File fileEntry : folder.listFiles()) {
            //count++;

            fileName=fileEntry.getName();
            final File outFileName=new File("C:/temp/output/"+"op_" +fileName);
            FileWriter fw = new FileWriter(outFileName.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
           
            BufferedReader reader = new BufferedReader(new FileReader(
                            "C:/temp/meta/"+fileName));
            List<String> neg_terms = new ArrayList<String>();
        
            while ((line = reader.readLine()) != null) {
                    finalLine += line;
            }
            finalLine = finalLine.toLowerCase();
            String[] sentences = finalLine.split("\\."); // call them "sentences" to avoid confusion



            for (String sentence : sentences) { // similarly, String sentence : sentences

                List<Result> resultList = api
                                .processCitationsFromString(sentence.trim());
                Result result = resultList.get(0);
                for (Utterance utterance : result.getUtteranceList()) {
                    System.out.println(" Id: " + utterance.getId());
                    
                    String utterances = utterance.getString();
                    System.out.println(" Utterance text: " + utterances);


                    Matcher m =  p.matcher(utterances);
                    Matcher m1 = p1.matcher(utterances);
                    Matcher m2 = p_noevi.matcher(utterances);
                    
                if (m2.find() || m1.find() || m.find()) { // simplify code instead of using many variables

                                                 // basically, here you have found a negation
                    /*for (PCM pcm : utterance.getPCMList()) {

                        //System.out.println("Phrase:");

                        String phraseText = pcm.getPhrase().getPhraseText();
                        //System.out.println(" text: " + phraseText);
                        
                        Matcher mp =  p.matcher(phraseText);
                        Matcher mp2 = p2.matcher(phraseText);
                        Matcher mp_noevi = p_noevi.matcher(phraseText);
                        
                        if( next_phrase == 1){
                            for (Ev ev : pcm.getCandidateList()){
                                 neg_p = 1;    
                                 int i = ev.getScore();
                                 ev.getConceptName();
                                 
                                 //System.out.println("Neg status " + ev.getNegationStatus());
                                 String local = ev.getConceptName();
                                 sort_scores1.put(i, local);
                                 System.out.println("negative for + concept : " + local);
                                }
                                next_phrase = 0;
                        }
                        if( no_evi_phrase == 1){
                            for (Ev ev : pcm.getCandidateList()){
                                 noevi_p = 1;    
                                 int i = ev.getScore();
                                 ev.getConceptName();
                                 //System.out.println("No evidence status " + ev.getNegationStatus());
                                 String local = ev.getConceptName();
                                 sort_scores2.put(i, local);
                                 System.out.println("no evidence of : " + local);
                                }
                                no_evi_phrase = 0;
                        }
                        if(mp_noevi.find()){
                                no_evi_phrase = 1;
                                
                        }
                        if(mp.find() && no_evi_phrase == 0){
                            for (Ev ev : pcm.getCandidateList()){
                               // System.out.println("Neg status " + ev.getNegationStatus());
                                no_p = 1;
                                int i = ev.getScore();       
                                String local = ev.getConceptName();
                                sort_scores.put(i, local);
                                System.out.println("no + concept : " + local);
                            }
                                
                           }
                            if(mp2.find()){
                               next_phrase = 1;
                            }
                            
                            
                            
                        
                        if(no_p == 1){
                            neg_terms.add("no " + sort_scores.lastEntry());
                            System.out.println("no(Scores) " + sort_scores.lastEntry());
                            no_p = 0;
                            sort_scores.clear();
                        }
                        if(neg_p == 1){
                            neg_terms.add("neg for " + sort_scores1.lastEntry());
                            System.out.println("negative for(Scores) " + sort_scores1.lastEntry());
                            neg_p = 0;
                            sort_scores1.clear();
                        }
                        if(noevi_p == 1){
                            neg_terms.add("no evidence of " + sort_scores2.lastEntry());
                            System.out.println("no evidence of (Scores) " + sort_scores2.lastEntry());
                            noevi_p = 0;
                            sort_scores2.clear();
                        }
                                    
                    }*/
                   }
                else{
                    for (PCM pcm : utterance.getPCMList()) {

                        //System.out.println("Phrase:");

                        String phraseText = pcm.getPhrase().getPhraseText();
                        System.out.println(" text: " + phraseText);
                    }   
                }
        }
       }
 }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{
        negation_detection("C:/temp/meta/");
    }
}

        
          









