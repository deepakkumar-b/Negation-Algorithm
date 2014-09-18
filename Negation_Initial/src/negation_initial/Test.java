/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negation_initial;

/**
 *
 * @author Deepakkumar
 */

import gov.nih.nlm.nls.metamap.Ev;
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
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String args[]) throws Exception {
		MetaMapApi api = new MetaMapApiImpl();
		api.setOptions("-y");
		final File folder = new File("C:/temp/meta/");
		String fileName=null;
		int count=0;
                
                //boolean contains = true;
		for (final File fileEntry : folder.listFiles()) {
			count++;
                        
			fileName=fileEntry.getName();
			final File outFileName=new File("C:/temp/output/"+"op_" +fileName);
			FileWriter fw = new FileWriter(outFileName.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//bw.write(content);
			//bw.close();
			BufferedReader reader = new BufferedReader(new FileReader(
					"C:/temp/meta/"+fileName));
			
			String line = null;
			String finalLine = "";
			while ((line = reader.readLine()) != null) {
				finalLine += line;
			}
			//System.out.println(finalLine);
			String[] lines = finalLine.split("\\.");                        
			for (String linee : lines) {
                                
				System.out.println(linee.trim());
				bw.write(linee.trim());
				bw.newLine();
				List<Result> resultList = api
						.processCitationsFromString(linee.trim());
				Result result = resultList.get(0);
				for (Utterance utterance : result.getUtteranceList()) {
					System.out.println("Utterance:");
					bw.write("Utterance");
					bw.newLine();
					System.out.println(" Id: " + utterance.getId());
					bw.write(" Id: " + utterance.getId());
					bw.newLine();
					System.out.println(" Utterance text: " + utterance.getString());
					bw.write(" Utterance text: " + utterance.getString());
					bw.newLine();
                                        
                                        //negation goes here
                                        
                                        bw.write(" Utterance text: " + utterance.getString());
					bw.newLine();
                                         
                                         Pattern p = Pattern.compile
       ("(negative for |no evidence of |No |no |No evidence of |Negative for |NO |NO EVIDENCE OF |NEGATIVE FOR )(.*)");
                                        
                                        
                                            //initial patterns that are found in a number of places 
                                            //extract the words after the patterns
                                            Matcher m =  p.matcher(utterance.getString());
                                            
                                           try{
                                             while (m.find()) {
                                                
                                                
                                                //print the pattern followed by the two words
                                                bw.write("negation detected =>  ");
                                                bw.write(m.group(1).trim() + "\t" + m.group(2).trim() + "\n");
                                                
                                                //System.out.println(m.group(1) + "\t" + m.group(2));
                                                bw.newLine();
                                                }
                                            }
                                            catch(Exception e){
                                                //e.printStackTrace();
                                            }
                                            finally{
                                                //bw.close();
                                            }
                                        
					System.out.println(" Position: " + utterance.getPosition());
					bw.write(" Position: " + utterance.getPosition());
					bw.newLine();
                                        
					for (PCM pcm : utterance.getPCMList()) {

						System.out.println("Phrase:");
						bw.write("Phrase:");
						bw.newLine();
						System.out.println(" text: "
								+ pcm.getPhrase().getPhraseText());
						bw.write(" text: "
								+ pcm.getPhrase().getPhraseText());
						bw.newLine();

						System.out.println("Candidates:");
						bw.write("Candidates:");
						bw.newLine();
						for (Ev ev : pcm.getCandidateList()) {

							System.out.println(" Concept Name: "
									+ ev.getConceptName());
							bw.write(" Concept Name: "
									+ ev.getConceptName());
							bw.newLine();
                                                        
						}
					
				}
                                       
			}  
                       
			
                     bw.flush();
	        
	    }
		
		bw.close();
	}
        }
    
}


