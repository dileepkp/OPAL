/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

//import dileep.OPALFeature.CEntity;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author aidb
 */
public class ProgramCodeInformation {
    
  static List<String> cLines;
    static List<String> cLinesReadOnly;
    
    public ProgramCodeInformation() throws IOException{
        readFile();
    }
    
    static void readFile() throws IOException {
        cLines = Files.readAllLines(Paths.get(Settings.cFile));
        cLinesReadOnly = Files.readAllLines(Paths.get(Settings.cFile));
        
    }
    
    static String getCode(int line, int bc, int ec){
     /*   String curLine = cLines.get(line - 1);
        int index = curLine.indexOf("\n");
        if(index != -1)
            curLine = curLine.substring(index+1);
         // return  curLine.substring(index + 1).substring(bc-1, ec);
       // else
            return curLine.substring(bc-1, ec);
        // - 1 because list indices begin from zero
*/
     try{
     return cLinesReadOnly.get(line-1).substring(bc-1, ec);
     }catch(Exception ex){
         if(line -1 >= 0)
         System.out.println("exception" + cLinesReadOnly.get(line-1) 
                 + "zzzz  bc-1=" + Integer.toString(bc-1) + "ec=" + Integer.toString(ec));
         else
             System.out.println("exception");
     }
     return "";
    }
    
    static String getCode(int bl, int bc, int el, int ec){
        StringBuilder sb = new StringBuilder();
        sb.append(cLinesReadOnly.get(bl - 1).substring(bc-1));
        for(int i = bl + 1; i< el; ++i)
           sb.append(cLinesReadOnly.get(i - 1));
        sb.append(cLinesReadOnly.get(el - 1).substring(0, ec));
        return sb.toString();
    }
    
    int beginLine;
    int endLine;
    int beginCol;
    int endCol;
   public String statement;
   public Statement stUri;
   public String function;
   //public String pattern = "#(\\d+)[_](\\d+)[_](\\d+)[_](\\d+)";
   //public Pattern r;
   
  public String pattern = "#(\\d+)[_](\\d+)[_](\\d+)[_](\\d+)[_]?(exit)?";
    ArrayList<CEntity> varList = new ArrayList<>();
    public Pattern r;
    
     public void setCStatementLocation(Statement st2){
        stUri = st2;
        statement = st2.toString();
        setStatement(st2.toString());
        
    }
    
    public  void setCStatementLocation(String st){
        //function = fn;
        statement = st;
        setStatement(st);
        
    }
    
    public void setStatement(String line){
        
      // Create a Pattern object
      r = Pattern.compile(pattern);

      // Now create matcher object.
      Matcher m = r.matcher(line);
      if (m.find( )) {
         /*System.out.println("Found value: " + m.group(0) );
         System.out.println("Found value: " + m.group(1) );
         System.out.println("Found value: " + m.group(2) );
         System.out.println("Found value: " + m.group(3) );
         System.out.println("Found value: " + m.group(4) );*/
          beginLine = Integer.parseInt(m.group(1));
          beginCol = Integer.parseInt(m.group(2));
          endLine = Integer.parseInt(m.group(3));
          endCol = Integer.parseInt(m.group(4));
      }
      /*else {
         System.out.println("NO MATCH");
      }*/
    }
    
    public  String getCode(){
        String cCode;
        if(beginLine == endLine)
            cCode = getCode(beginLine, beginCol, endCol);
        else 
            cCode = getCode(beginLine, beginCol,endLine, endCol);
        return cCode;
        
    }
    
}
