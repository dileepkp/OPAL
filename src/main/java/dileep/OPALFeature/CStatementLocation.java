/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author aidb
 */
public class CStatementLocation {
    int beginLine;
    int endLine;
    int beginCol;
    int endCol;
    String statement;
    Statement stUri;
    String function;
    String pattern = "#(\\d+)[_](\\d+)[_](\\d+)[_](\\d+)";
    Pattern r;
    
    public CStatementLocation(){
        
    }
    public CStatementLocation(String st){
        statement = st;
        setStatement(st);
        
    }
    
    
    public CStatementLocation(Statement st2){
        stUri = st2;
        statement = st2.toString();
        setStatement(st2.toString());
        
    }
    
    public CStatementLocation(String st, String fn){
        function = fn;
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
    
    //change name to isInScopeOf
    public boolean isInScope(String s){
        int bl,bc,el,ec;
       Matcher m = r.matcher(s);
      if (m.find( )) {
          bl = Integer.parseInt(m.group(1));
          bc = Integer.parseInt(m.group(2));
          el = Integer.parseInt(m.group(3));
          ec = Integer.parseInt(m.group(4));
          
          if(checkForAllZero(bl,bc,el,ec))
              return true;
          
          if((beginLine > bl || (bl== beginLine && beginCol >= bc)) &&
               (endLine <  el || (el== endLine && endCol <= ec)))
              return true;
          else return false;
      }else {
         return false;
      }
    }
    
    /*public static void main(String[] args) {
        CStatementLocation cl = new CStatementLocation();
        cl.setStatement("#10_3_11_4");
        System.out.println(cl.isInScope("#10_3_11_4"));
    }
*/

    private boolean checkForAllZero(int bl, int bc, int el, int ec) {
        if(bl == 0 || bc ==0 || el ==0 || ec ==0)
            return true;
        return false;
    }

    boolean isInScopeOf(CStatementLocation funcDefCsl) {
           int bl,bc,el,ec;
          bl = funcDefCsl.beginLine;
          bc = funcDefCsl.beginCol;
          el = funcDefCsl.endLine;
          ec = funcDefCsl.endCol;
          
          if(checkForAllZero(beginLine,beginCol,endLine,endCol))
              return true;
          
          if((beginLine > bl || (bl== beginLine && beginCol >= bc)) &&
               (endLine <  el || (el== endLine && endCol <= ec)))
              return true;
          else return false;
          
    }
}
