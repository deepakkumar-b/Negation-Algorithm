/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negation_initial;

import java.io.FileReader;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Deepakkumar
 */

public class Negation_Initial{

    /**
     * @param args the command line arguments
     */   
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
    checkSentence(); //calling function for negation in a sentence check
    }

    public Negation_Initial() throws FileNotFoundException, IOException {
        //constructor block        
    }
    public static void checkSentence() 
            throws IOException, FileNotFoundException{
    //Scanner scanner = new Scanner(new FileInputStream("C://temp/a1.txt"));
    Scanner scanner = new Scanner(new FileInputStream
        ("G://SBU_523_524/Negation_find_Raj/Files_Raj/txtfiles/12685_op.txt"));
   //location to a file name
    
    try {
      while (scanner.hasNextLine()){ //do until the end of the file
         String sen = scanner.nextLine();
         boolean contains = sen.matches(".*\\bProcessing\\b.*"); 
         //Processing is the first word output of metamap that shows a sentence
         
         if(contains){ 
            Pattern p = Pattern.compile
        ("(negative for|no evidence of|no )\\s*(\\w+ \\w+)"); 
            //initial patterns that are found in a number of places 
            //extract the two words after the patterns
            
            Matcher m = p.matcher(sen); //finding the pattern match

            while (m.find()) {
                System.out.println(m.group(1) + "\t" + m.group(2));
                //print the pattern followed by the two words
            }
            
         }
      }
    }
    finally{
      scanner.close();
    }        
       
    
    }
    
}

