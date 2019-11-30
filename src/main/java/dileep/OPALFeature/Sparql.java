/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;

/**
 *
 * @author aidb
 */
public class Sparql {

    public static List<QuerySolution> executeQuery(String sparqlQuery, Model model) {

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, model);
        ResultSet results2 = qe.execSelect();
        // ResultSet results = ResultSetFactory.copyResults( results2 );
        //ResultSetFormatter.out(System.out, results2, query) ;
        //
        //results.
        java.util.List<QuerySolution> qs = ResultSetFormatter.toList(results2);
        return qs;
    }

    public static void executeQueryAndPrint(String sparqlQuery, Model model) {

        Query query = QueryFactory.create(sparqlQuery);
        QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, model);
        ResultSet results2 = qe.execSelect();
        // ResultSet results = ResultSetFactory.copyResults( results2 );
        ResultSetFormatter.out(System.out, results2, query);
        //
        //results.

    }
    
     public static void executeQueryAndPrint(String sparqlQuery, Model model, boolean measureTime) {

        Query query = QueryFactory.create(sparqlQuery);
        Settings.startTime = System.currentTimeMillis();
        QueryExecution qe = QueryExecutionFactory.create(sparqlQuery, model);
        
        ResultSet results2 = qe.execSelect();
                    

        ResultSet results = ResultSetFactory.copyResults( results2 );
        
         Settings.endTime = System.currentTimeMillis();
       Settings.inferenceTime = Settings.endTime - Settings.startTime;
       ResultSetFormatter.out(System.out, results, query);
        System.out.println("\nBackward chaining time:" + Settings.inferenceTime + " milliseconds");
        //
        //results.

    }

    /*
     * Queries the RDF-3X based on the query
     * present in queryFile.
     */
    public static HashSet<String> rdf3xQuery(String dataFile,String queryFile) {

        StringBuffer output = new StringBuffer();
        HashSet<String> al = new HashSet<>();

        try {
            runShellScript("src/main/resources/compile.sh",dataFile);
            Settings.startTime = System.currentTimeMillis();
            Process proc = runShellScript("src/main/resources/run.sh",queryFile);
           

                    
           /* BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                al.add(line);
                output.append(line + "\n");
            }
            reader.close();
*/
           
                        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        String line = "";                       
                        while ((line = reader.readLine())!= null) {
                                output.append(line + "\n");
                        }
                        System.out.println("### " + output);
                           Settings.endTime = System.currentTimeMillis();
            Settings.inferenceTime = Settings.endTime - Settings.startTime;
           
                         System.out.println( "\n Backward chaining time:" + Settings.inferenceTime + " milliseconds");

        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.out.print(al.size() + ",");
        return al;
    }

    static Process runShellScript(String loc,String arg) throws IOException {
        
        try {
            //String target = new String(loc);
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(loc + " " + arg);
            proc.waitFor();
            return proc;
        } catch (InterruptedException ex) {
            Logger.getLogger(Sparql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
