/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* Oct 22:
Step 1 complete
Start with step 2

*/
package negation_initial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Deepakkumar
 */
/* Pseudo
    
    1) Get the file: Implement the file browser
    2) Run all the terms
    3) For found term -> Associate either it is Head CT, Chest CT, etc
    4) Give the diagnosis
 */
public class Med_Term_Search {

    static final Pattern p = Pattern.compile("(no )(.*)");
    static final Pattern p1 = Pattern.compile("(negative for )(.*)");
    static final Pattern p2 = Pattern.compile("(negative )(.*)");
    static final Pattern p_noevi = Pattern.compile("(no evidence of )(.*)");
    
    /**
     * @param args the command line arguments
    */
    
    static String finaloutput = new String("<html><head><title>New Page</title></head><body><p>Radiology Document Analysis</p>");
    static String term = new String();
    static int first_time = 1;

    /**
     *
     * @param html
     * @param str
     */
    public static void changeString(ChangeableString html, String str) {
    html.changeTo(str);
}


public static String[] ConvertToSentence(ChangeableString html){
        String[] sentences;
        String local = (html.toString()).toLowerCase();;
        sentences = local.split("\\.");
        return sentences;
    }

 public static void format_output(ChangeableString html, String filename, int size, BufferedWriter bw1) throws IOException{
            
        String s = new String("<table><tr><th colspan=2>"+filename+"</th></tr>");
        System.out.println("Inside format_output" + " Size is : " + size);
        
        if(first_time == 1){
            first_time = 0;
            bw1.write(finaloutput + s + html + "</table>");
            System.out.println("Done here" + finaloutput + s + html+  "</table>");
            
        }
        else{
                bw1.write(s + html + "</table>");
                System.out.println("Done here" + s + html+ "</table>");
            
        }
        bw1.flush();
        
}
 public static void main(String args[]) throws Exception{
        
        boolean isPresent = false;
        /* creating a map for all the med terms 
        So Map will be 
            Head CT -> All associated Head CT Terms
            Chest CT -> Associated Chest CT Terms 
        */
      
        /* TreeMap<"HeadCT", <["mass effect", <a, b, c>], ["midline shift", <d, e, f>]> */
        TreeMap<String, TreeMap<String, ArrayList<String>>> map = new TreeMap();
        
        TreeMap<String, ArrayList<String>> headMap = new TreeMap();
        ArrayList<String> massDiag = new ArrayList<>(Arrays.asList("Cerebral Edema" , "Cerebral compression"));
        headMap.put("mass effect", massDiag);
        
        ArrayList<String> midlineDiag = new ArrayList<>(Arrays.asList("Diag 1 - Midline " , " Diag 2 - Midline"));
        headMap.put("midline shift", midlineDiag);
        
        map.put("HeadCT", headMap);
        
        int i = 0; 
        int j = 0;
        
        /*
        for (mass, midline)
            if(mass is present)
                mass -> diagnosis
                document -> HEadCT
        
        */
        String file = new String("C:/temp/meta/1/one.txt");
        Set keys = map.keySet(); //keys - HeadCT
        
        for (Iterator it = keys.iterator(); it.hasNext();) {
            String key = (String) it.next();
            TreeMap<String, ArrayList<String>> t = map.get(key);
            System.out.println("Value is :  " + t);
            //Value is :  {mass effect=[Cerebral Edema, Cerebral compression], midline shift=[Diag 1 - Midline ,  Diag 2 - Midline]}
            
            Set keys2 = t.keySet(); // mass effect, midline shift
            for (Iterator it2 = keys2.iterator(); it2.hasNext();){
                String local = (String)it2.next(); //contains mass effect
                isPresent = search(local, file);
                if(isPresent){
                    System.out.println("Found report type is: " + key + "\nThe diagnosis is: " + t.get(local) );
                    /* for(String diag: t.keySet()){
                        System.out.println(t.get(diag));
                    }*/
                }
            }
            
            //System.out.println(key + " = " + value);
        }
               
       /* for(Map.Entry<String,Integer> entry : treeMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            System.out.println(key + " => " + value);
        }
        */
        //below this - working
        /*
        String file = new String("C:/temp/meta/1/one.txt");
        TreeMap<String, ArrayList<String>> radiology = new TreeMap<>();
         
        ArrayList<String> headCTList = new ArrayList<>();
        String[] head = new String[]{"mass effect", "midline shift"};
        headCTList.addAll(Arrays.asList(head));
        
        ArrayList<String> cxrList = new ArrayList<>();
        String[] cxr = new String[]{"atelectasis", "infiltrates"};
        cxrList.addAll(Arrays.asList(cxr));
              
        radiology.put("Head CT", headCTList);
        radiology.put("CXR", cxrList);     
        
        int i = 0;
        for (String key : radiology.keySet()) {
            System.out.println("i= " + i );   
            i++;  
            for (String value : radiology.get(key)) {  
              
                System.out.println("Value is : "  + value ); 
                isPresent = search(value, file);
                if(isPresent){
                    System.out.println("Found report type is : " + key);
                }
            }  
            
        }  */
                
    }
public static boolean search(String text, String filePath) throws IOException {
    int count = 0;
    String line;
    String finalLine = new String();
    text = text.toLowerCase();
    FileReader fr = new FileReader(filePath);
    BufferedReader br = new BufferedReader(fr);
    while ((line = br.readLine()) != null) {
         finalLine += line;
    }
    finalLine = finalLine.toLowerCase();
    String[] sentences = finalLine.split("\\.");
    
    for(String sentence: sentences){
        int fromIndex = 0;
        int index = -1;
        while ((index = sentence.indexOf(text, fromIndex)) != -1) {
            count++;
            fromIndex = index + text.length();
            System.out.println("Sentence is: " + sentence + "\n");
        }

    }

    fr.close();
    br.close();
    /* Note: to be removed
    Step 2
    I have done search for one term. I should put all the terms in a 
    Map<head CT, <mass effect, midline shift, ...>> for all the terms and
    find the head CT, abdomen CTm etc... 
    
    */
    return (count>0);
    //((count>0)?true:false);
}
public static void findQuery(String key, TreeMap map){
    
}
public static class ChangeableString {
    String html;

    public ChangeableString(String html) {
        this.html = html;
    }

    public void changeTo(String newStr) {
        html = newStr;
    }

    public String toString() {
        return html;
    }
        
}
}