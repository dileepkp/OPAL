/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import dileep.OPALFeature.base.QueryAndProgram;
import dileep.OPALFeature.base.QueryPointTriple;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author aidb
 */
public class CheckAgent {
       public static void main(String[] args) {

        ReadInput input = new ReadInput();
        ReadInput.readSettings();
        Settings.bcStartTime = System.currentTimeMillis();
        Settings.postRuleModel = getModel();
            
        Model queryModel = ModelFactory.createDefaultModel();
        queryModel.read(Settings.queryFile);
        Settings.queryModel = queryModel;
            RunAnalysis(input);

           
         

        
    }

    public static void RunAnalysis(ReadInput input) {
        List<QueryPointTriple> queryPoints = QueryToZ3.readQuery(input);
        if (queryPoints == null || queryPoints.isEmpty()) {
            Settings.bcEndTime = System.currentTimeMillis();
            Settings.inferenceTime = Settings.bcEndTime - Settings.bcStartTime;
            System.out.println( "\n Backward chaining time (final):" + Settings.inferenceTime + " milliseconds");
            return;
        }
        HashMap<String, String> cfg = new HashMap<>();
        cfg.put("model", "true");
        Context ctx = new Context(cfg);
        Solver solver = ctx.mkSolver();
        // Solver solverForQuery = ctx.mkSolver();
        ProgInfo progInfo = null;
        if(Settings.forwardChainNeeded){
            List<QueryPointTriple> preCall = queryPoints.stream().filter(p -> p.queryLoc.equals(Settings.bugFindIRI + "preCall")).collect(Collectors.toList());
            List<QueryPointTriple> postCall = queryPoints.stream().filter(p -> p.queryLoc.equals(Settings.bugFindIRI + "postCall")).collect(Collectors.toList());
            String strPreCall = QueryToZ3.getQueryPointString(preCall);
            String strPostCall = QueryToZ3.getQueryPointString(postCall);
            String queryPointString = QueryToZ3.getQueryPointString(queryPoints);
            
            progInfo = TriplesToZ3.readProgInfo(queryPointString, Settings.postRuleModel,strPreCall,strPostCall,queryPoints);
            
        }
        else
            progInfo = TriplesToZ3.buildQueryAndProgram(queryPoints);
        // Settings.startTime = System.currentTimeMillis();
        
        final List<String> qpProcessed = progInfo.queryAndProgramLink.stream().map(p -> p.queryPoint).collect(Collectors.toList());
        List<QueryPointTriple> qpToBeProcessed = queryPoints.stream().filter(p -> !qpProcessed.contains(p.queryPoint)).collect(Collectors.toList());
        TriplesToZ3.buildQueryAndProgram(qpToBeProcessed,progInfo);
        
        // Settings.endTime = System.currentTimeMillis();
        // Settings.inferenceTime = Settings.endTime - Settings.startTime;
        // System.out.println( "\n build query and prog time:" + Settings.inferenceTime + " milliseconds");
        for (QueryAndProgram qap : progInfo.queryAndProgramLink) {
            
            QueryToZ3.ConvertQueryToZ3(queryPoints, qap.queryPoint,input, ctx, solver);
            
            if(Settings.forwardChainNeeded)
                TriplesToZ3.ConvertToZ3(progInfo,qap, ctx, solver);
            //TriplesToZ3.testZ3Assertions(solver,qap.queryPoint);
            
            Main.solveConstraints_exp(solver,qap);
            solver.reset();
            
        }
        Settings.bcEndTime = System.currentTimeMillis();
        Settings.bcTime = Settings.bcEndTime - Settings.bcStartTime;
        System.out.println( "\n Backward chaining time (final):" + Settings.bcTime + " milliseconds");
        return;
    }
       
       private static Model getModel() {
        // create a basic RAW model that can do no inferencing

        Model rawModel = ModelFactory.createDefaultModel();
        rawModel.read(Settings.inferredFile);
        return rawModel;
    }


 
    
}
