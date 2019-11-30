/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import dileep.OPALFeature.base.Constants;
import dileep.OPALFeature.base.Constants.ProgramConditionTruthValue;
import dileep.OPALFeature.base.ProgramCondition;
import dileep.OPALFeature.base.QueryAndProgram;
import dileep.OPALFeature.base.QueryPointTriple;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;

/**
 *
 * @author aidb
 */
public class TriplesToZ3 {

    //This method can be improved for performance.
    public static void ConvertToZ3(ProgInfo progInfo, QueryAndProgram qap, Context ctx, Solver solver) {

        List<QuerySolution> collect;
        if (qap.getPaths() != null) {
            collect = progInfo.variableValues.stream().
                    filter(p -> p.get("qp").toString().equals(qap.queryPoint)
                    && qap.getPaths().stream().anyMatch(q -> q.pathId.equals(p.get("pathId").toString()))).
                    collect(Collectors.toList());
        } else {
            collect = progInfo.variableValues.stream().
                    filter(p -> p.get("qp").toString().equals(qap.queryPoint)).
                    collect(Collectors.toList());
        }

        /*collect = progInfo.variableValues.stream().
                filter(p -> p.get("qp").toString().equals(qap.queryPoint) 
                        && qap.getPaths().contains(p.get("pathId").toString())).
                collect(Collectors.toList());
        
        
        collect = progInfo.variableValues.stream().
                filter(p -> p.get("qp").toString().equals(qap.queryPoint) 
                        ).
                collect(Collectors.toList());
         */
        convertVariablesToZ3(collect, ctx, solver);

        //Process program conditions.
        List<ProgramCondition> conditions = qap.getConditions();
        if (conditions == null) {
            return;
        }

        Map<QuerySolution, ProgramConditionTruthValue> map = new HashMap<>();

        ProgramCondition get;
        for (QuerySolution qs : progInfo.binaryConditions) {
            Optional<ProgramCondition> findFirst = conditions.stream().filter(q -> qs.get("exp").toString().equals(q.condition) && q.z3Allowable).findFirst();
            if (findFirst.isPresent()) {
                get = findFirst.get();
                map.put(qs, get.pred);

            }

        }
        convertBinaryConditionsToZ3(map, ctx, solver, progInfo, qap);
        map.clear();

        /*collect = progInfo.unaryConditions.stream().
                filter(p -> (conditions.stream().
                        anyMatch(q -> p.get("exp").toString().equals(q.condition) && q.z3Allowable))).
                collect(Collectors.toList());
         */
        for (QuerySolution qs : progInfo.unaryConditions) {
            Optional<ProgramCondition> findFirst = conditions.stream().filter(q -> qs.get("exp").toString().equals(q.condition) && q.z3Allowable).findFirst();
            if (findFirst.isPresent()) {
                get = findFirst.get();
                map.put(qs, get.pred);

            }
        }
        convertUnaryConditionsToZ3(map, ctx, solver);

        //testZ3Assertions(solver);
    }

//      exp            | var           | op      | val | dataType | type |
// ====================================================================
//    | file0:5_5_5_10 | file0:4_2_4_8 | c:NeqOp | "0" | c:int    | 1    
    private static void convertBinaryConditionsToZ3(Map<QuerySolution, ProgramConditionTruthValue> resultList, Context ctx, Solver solver, ProgInfo pi, QueryAndProgram qap) {

        IntExpr val = null;
        int intValue;

        for (QuerySolution qs : resultList.keySet()) {
            String expNode = qs.get("exp").toString();
            RDFNode varNode = qs.get("var");
            RDFNode opNode = qs.get("op");
            RDFNode valNode = qs.get("val");
            RDFNode dataTypeNode = qs.get("dataType");
            /*            String psAtCondition = qs.get("ps").toString();//ps of the variable at the condition
             //In a particular path
            //If the program state of a variable at qp 
            // is different from the ps of the variable at the condition
            // then the condition is no longer valid for that qp in that path
           Boolean diff = pi.variableValues.stream().anyMatch(p -> 
                    !p.get("ps").toString().equals(psAtCondition)
                    && p.get("qp").toString().equals(qap.queryPoint)
                    && p.get("var").toString().equals(varNode.toString()));
           if(diff)
              continue;
             */
            if (pi.invalidCondtions != null && pi.invalidCondtions.stream().anyMatch(p
                    -> p.get("qp").toString().equals(qap.queryPoint)
                    && p.get("invalidCond").toString().equals(expNode)
                    && qap.getPaths().stream().anyMatch(q -> q.pathId.equals(p.get("pathId").toString()))
                    && p.get("queryLoc").toString().equals(qap.qtp.queryLoc)
            )) {
                continue;
            }

            intValue = Utils.extractIntFromRdfLiteral(valNode.toString());
            if (intValue != -999) {
                val = ctx.mkInt(intValue);

            }

            Utils.addEntitiesToSolver(ctx, varNode, val, opNode, solver, resultList.get(qs));
        }
    }

    //| exp            | var             | op             |
//=====================================================
//| file0:7_6_7_10 | file0:3_27_3_30 | c:LogicalNegOp |
    private static void convertUnaryConditionsToZ3(Map<QuerySolution, ProgramConditionTruthValue> resultList, Context ctx, Solver solver) {
        for (QuerySolution qs : resultList.keySet()) {
            RDFNode varNode = qs.get("var");
            RDFNode opNode = qs.get("op");
            IntExpr var = ctx.mkIntConst(varNode.toString());

            IntExpr val = ctx.mkInt(0);
            /* if (opNode.toString().equals("http://www.semanticweb.org/yzhao30/ontologies/2015/7/c#LogicalNegOp")) {
                solver.add(ctx.mkNot(ctx.mkEq(var, val)));
            }*/

            Utils.addEntitiesToSolver(ctx, varNode, val, opNode, solver, resultList.get(qs));

        }

    }

    // | var           | val | relation        |
// =========================================
// | file0:4_2_4_8 | 0   | pa:greaterEqual |
    private static void convertVariablesToZ3(List<QuerySolution> resultList, Context ctx, Solver solver) {
        int intValue;
        for (QuerySolution qs : resultList) {
            RDFNode varNode = qs.get("var");
            RDFNode valNode = qs.get("val");
            RDFNode relationNode = qs.get("relation");

            IntExpr var = ctx.mkIntConst(varNode.toString());
            IntExpr val = null;
            intValue = Utils.extractIntFromRdfLiteral(valNode.toString());
            if (intValue != -999) {
                val = ctx.mkInt(intValue);
            } else {
                continue;
            }

            if (relationNode.toString().equals(Settings.paIRI + "greaterEqual")) {
                solver.add((ctx.mkGe(var, val)));
            } else if (relationNode.toString().equals(Settings.paIRI + "equals")) {
                solver.add((ctx.mkEq(var, val)));
            }
        }
    }

    public static void testZ3Assertions(Solver solver, String qp) {
        BoolExpr[] asser = solver.getAssertions();
        for (BoolExpr b : asser) {
            System.out.println(b.toString());
        }
        System.out.println("at " + qp + " number: " + solver.getNumAssertions() + "\n\n");
        //System.out.println("------------------------------------------------------------------------------------------");
    }

    //In this version query location is taken into account for variables only.
    //Need to extend this to conditions?
  /*  static ProgInfo readProgInfo_old(String queryPoints, Model postRuleModel, String preCall, String postCall, List<QueryPointTriple> qpt) {
        ProgInfo pi = new ProgInfo(qpt);
        String ip;

        ip = ReadInput.readInput("4", preCall);
        // System.out.println(ip);
        // Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        pi.variableValues = Sparql.executeQuery(ip, postRuleModel);

        ip = ReadInput.readInput("5", postCall);
        pi.variableValues.addAll(Sparql.executeQuery(ip, postRuleModel));

        //Process program conditions.
        ip = ReadInput.readInput("2", queryPoints);
        pi.programConditionsInRdf = Sparql.executeQuery(ip, postRuleModel);
        //System.out.println(ip);
        Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        pi.linkQueryAndProgram();
        String progConditions = pi.getProgConditionString();

        ip = ReadInput.readInput("101", progConditions);
        //System.out.println(ip);
        // Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);

        pi.binaryConditions = Sparql.executeQuery(ip, postRuleModel);

        ip = ReadInput.readInput("102", progConditions);
        pi.unaryConditions = Sparql.executeQuery(ip, postRuleModel);

        return pi;
    }*/

    static ProgInfo readProgInfo(String queryPoints, Model postRuleModel, String preCall, String postCall, List<QueryPointTriple> qpt) {
        ProgInfo pi = new ProgInfo(qpt);
        String ip;

        // Settings.startTime = System.currentTimeMillis();
        ip = ReadInput.readInput("4", preCall);
        pi.variableValues = Sparql.executeQuery(ip, postRuleModel);

        if (postCall != null) {
            ip = ReadInput.readInput("5", postCall);
            pi.variableValues.addAll(Sparql.executeQuery(ip, postRuleModel));
        }

        //System.out.println(ip);
       // Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        ip = ReadInput.readInput("101", null);
        //System.out.println(ip);
        // Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        pi.binaryConditions = Sparql.executeQuery(ip, postRuleModel);

        ip = ReadInput.readInput("102", null);
        pi.unaryConditions = Sparql.executeQuery(ip, postRuleModel);

        String progConditions = pi.getParseCondString();

        //Process program conditions.
        ip = ReadInput.readInput("2", queryPoints + ".\n" + progConditions);
        //System.out.println(queryPoints);
        //System.out.println(ip);
        //Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        pi.programConditionsInRdf = Sparql.executeQuery(ip, postRuleModel);
        //  Settings.endTime = System.currentTimeMillis();
        //     Settings.inferenceTime = Settings.endTime - Settings.startTime;
        //    System.out.println( "\n data read time:" + Settings.inferenceTime + " milliseconds");

        //Settings.startTime = System.currentTimeMillis();
        pi.linkQueryAndProgram();

        // Settings.endTime = System.currentTimeMillis();
        //  Settings.inferenceTime = Settings.endTime - Settings.startTime;
        //  System.out.println( "\n link time:" + Settings.inferenceTime + " milliseconds");
        /**
         * *********************invalid conditions*****************
         */
        if(Settings.useInvalidCond){
        
        ip = ReadInput.readInput("preCallInvalidCond", preCall);
        pi.invalidCondtions = Sparql.executeQuery(ip, postRuleModel);
        //Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);

        if (postCall != null) {
            ip = ReadInput.readInput("postCallInvalidCond", postCall);
            pi.invalidCondtions.addAll(Sparql.executeQuery(ip, postRuleModel));
        }
        // System.out.println(ip);
        // Sparql.executeQueryAndPrint(ip, Settings.postRuleModel);
        }
        return pi;
    }

    static ProgInfo buildQueryAndProgram(List<QueryPointTriple> queryPoints) {
        ProgInfo pi = new ProgInfo(queryPoints);
        pi.buildQueryAndProgram(queryPoints);
        return pi;

    }

    static void buildQueryAndProgram(List<QueryPointTriple> qpToBeProcessed, ProgInfo progInfo) {
        progInfo.buildQueryAndProgram(qpToBeProcessed);
    }

}
