/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author aidb
 */
public class CFile {
    
    static List<String> cLines;
    static List<String> cLinesReadOnly;
    
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
    
}
