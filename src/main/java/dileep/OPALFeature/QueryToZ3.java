/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.ArrayExpr;
import dileep.OPALFeature.base.QueryPointTriple;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import java.util.List;
import java.util.stream.Collectors;
import dileep.OPALFeature.base.Constants;
import dileep.OPALFeature.base.Constants.QueryConditionType;

/**
 *
 * @author aidb
 */
public class QueryToZ3 {

    public static void ConvertQueryToZ3(List<QueryPointTriple> queryConditons,String queryPoint, ReadInput input, Context ctx, Solver solver) {
        convertVarValQuery(queryConditons.stream()
                .filter(p -> p.queryPoint.equals(queryPoint) && p.objectType == QueryConditionType.Value)
                .collect(Collectors.toList()), queryPoint, ctx, solver);
        convertVarVarQuery(queryConditons.stream()
                .filter(p -> p.queryPoint.equals(queryPoint) && p.objectType == QueryConditionType.ValueList)
                .collect(Collectors.toList()), queryPoint, ctx, solver);
    }
    
    private static void convertVarVarQuery(List<QueryPointTriple> queryConditons, String queryPoint, Context ctx, Solver solver) {

 ArrayExpr a1 = null, a2 = null;

        if (queryConditons != null) {
            for (QueryPointTriple tr : queryConditons ) {
                if(tr.subjectType.equals(Settings.paIRI + "varList"))   
            a1 = Utils.getZ3ArrayFromRdf(tr.subject,"lhs",ctx);
                if(tr.objectType == Constants.QueryConditionType.ValueList)   
            a2 = Utils.getZ3ArrayFromRdf(tr.object,"rhs",ctx);
               
                Utils.addEntitiesToSolver(ctx, a1, a2, tr.predicate, solver, Boolean.TRUE);
                
            }
        }
    }


    static void convertVarValQuery(List<QueryPointTriple> queryConditons, String queryPoint, Context ctx, Solver solver) {
        //List<Triple> queryConditons = input.processType1QueryConditions();
        int intValue;
        if (queryConditons != null) {
            for (QueryPointTriple tr : queryConditons ) {

                IntExpr val = null;
                /*if (tr.object.equals("0")) {
                val = ctx.mkInt(0);
                }*/
                intValue = Utils.extractIntFromRdfLiteral(tr.object);
                if (intValue != -999) {
                    val = ctx.mkInt(intValue);
                    
                }
                
                ArithExpr var = null;
                //In future, generalize this to ArithmeticOp
                if (tr.subjectType.equals(Settings.ontologyIRI + "SubOp")) {
                    String query = ReadInput.readGeneralQuery("arithmeticExpression","?expr", tr.subject);
                   // System.out.println(query);
                   // Sparql.executeQueryAndPrint(query,Settings.postRuleModel);
                    // ArithExpressions of the form a-1
                    var = Utils.convertArithExprToZ3(Sparql.executeQuery(query, Settings.postRuleModel),ctx);
                } else {
                    var = ctx.mkIntConst(tr.subject);
                }
                
                
                Utils.addEntitiesToSolver(ctx, var, val, tr.predicate, solver, Constants.ProgramConditionTruthValue.True);
            }
        }
    }

    static List<QueryPointTriple> readQuery(ReadInput input) {
        List<QueryPointTriple> queryConditons = input.processQueryConditions();
        return queryConditons;
    }

    static String getQueryPointString(List<QueryPointTriple> queryPoints) {
        StringBuilder s = new StringBuilder(255);

        s.append("FILTER ( str(?qp) IN (");
        for (QueryPointTriple tr : queryPoints) {
            s.append("\"" + tr.queryPoint + "\"^^xsd:string");
            s.append(",");
        }
        if(s.lastIndexOf(",") >= 0 ){
        s.deleteCharAt(s.lastIndexOf(","));
        s.append("))");
        return s.toString();
        }
        else return null;
    }

    
}
