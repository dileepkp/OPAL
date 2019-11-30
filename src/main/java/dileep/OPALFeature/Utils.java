/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.ArrayExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.EnumSort;
import com.microsoft.z3.Expr;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.SeqSort;
import com.microsoft.z3.SetSort;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.StringSymbol;
import com.microsoft.z3.Symbol;
import dileep.OPALFeature.base.Constants;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;

/**
 *
 * @author aidb
 */
public class Utils {

    public static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length(), string.length());
        } else {
            return string;
        }
    }

    static int extractIntFromRdfLiteral(String lit) {
        String replace = null;
        if (lit.endsWith(Settings.intURI)) {
            replace = lit.replace(Settings.intURI, "");
            return Integer.valueOf(replace);
        }
        return -999;
    }

    //Assuming the size of list is 1.. 
    //In future, extend this.
    static ArithExpr convertArithExprToZ3(List<QuerySolution> executeQuery, Context ctx) {
        ArithExpr ae = null;
        Comparator<QuerySolution> order = Comparator.comparing(p -> Integer.valueOf(XMLDataTypeToInt(p.get("pos").toString())));

        for (QuerySolution p : executeQuery.stream().sorted(order.reversed()).collect(Collectors.toList())) {
            ArithExpr ae1 = getZ3ArithExprFromRdf(p.get("left").toString(), p.get("lType").toString(), ctx);
            ArithExpr ae2 = getZ3ArithExprFromRdf(p.get("right").toString(), p.get("rType").toString(), ctx);
            ae = buildZ3ArithExpr(ae1, ae2, p.get("exprType").toString(), ctx);
        }

        return ae;
    }

    static void addEntitiesToSolver(Context ctx, RDFNode varNode, IntExpr val, RDFNode opNode, Solver solver, Constants.ProgramConditionTruthValue pred) {
        IntExpr var = ctx.mkIntConst(varNode.toString());
        addEntitiesToSolver(ctx, var, val, opNode.toString(), solver, pred);
    }
    
     static void addEntitiesToSolver(Context ctx, ArrayExpr a1, ArrayExpr a2, String opNode, Solver solver, Boolean TRUE) {
         SeqSort stringSort = ctx.getStringSort();
       
         BoolExpr be = null;
        if (opNode.equals(Settings.paIRI + "notIn") ) {
            be = ctx.mkNot(ctx.mkSetSubset(a1, a2));
        } else if (opNode.equals(Settings.paIRI + "intersects") ) {
             ArrayExpr intersect = ctx.mkSetIntersection(a1,a2);
             ArrayExpr empty = ctx.mkEmptySet(stringSort);
            be = ctx.mkNot(ctx.mkEq(intersect, empty));
        } 
         solver.add(be);
    }

    static void addEntitiesToSolver(Context ctx, ArithExpr var, ArithExpr val, String opNode, Solver solver, Constants.ProgramConditionTruthValue pred) {
        BoolExpr be = null;
        if (opNode.equals(Settings.ontologyIRI + "NeqOp")  || opNode.equals(Settings.paIRI + "notEqual") ) {
            be = (ctx.mkNot(ctx.mkEq(var, val)));
        } else if (opNode.equals(Settings.ontologyIRI + "EqualOp") || opNode.equals(Settings.ontologyIRI + "LogicalNegOp") || opNode.equals(Settings.paIRI + "equals")) {
            be = (ctx.mkEq(var, val));
        } else if (opNode.equals(Settings.ontologyIRI + "LeqOp")) {
            be = (ctx.mkLe(var, val));
        }
        if (pred == Constants.ProgramConditionTruthValue.True) {
            solver.add(be);
        } else if (pred == Constants.ProgramConditionTruthValue.False) {
            solver.add(ctx.mkNot(be));
        }
        else{
            BoolExpr notBe = ctx.mkNot(be);
            BoolExpr orBe = ctx.mkOr(be,notBe);
            solver.add(orBe);
        }
    }

    private static ArithExpr getZ3ArithExprFromRdf(String operand, String opType, Context ctx) {
        IntExpr var = null;
        int intValue;
        if (opType.equals(Settings.ontologyIRI + "VariableRef")) {
            var = ctx.mkIntConst(operand);
        } else if (opType.equals(Settings.ontologyIRI + "Value")) {
            intValue = Utils.extractIntFromRdfLiteral(operand);
            if (intValue != -999) {
                var = ctx.mkInt(intValue);
            }
        }
        return var;

    }

    private static ArithExpr buildZ3ArithExpr(ArithExpr ae1, ArithExpr ae2, String operator, Context ctx) {
        ArithExpr ae = null;
        if (operator.equals(Settings.ontologyIRI + "SubOp")) {
            ae = ctx.mkSub(ae1, ae2);
        }
        return ae;
    }

  
    //https://stackoverflow.com/questions/22942297/values-of-a-set-in-a-model-found-by-z3?lq=1
    static ArrayExpr getZ3ArrayFromRdf(String rdfList,String enumSortName, Context ctx) {
        SeqSort stringSort = ctx.getStringSort();
        ArrayExpr arr1 = ctx.mkEmptySet(stringSort);
         String[] split = rdfList.split(" ");
        String[] unique = Arrays.stream(split).distinct().toArray(String[]::new);
        //BoolExpr True = ctx.mkTrue();
        for(String s : unique){
            Expr e = ctx.MkString(s);
          // Expr e = ctx.mkConst(s, stringSort);
           arr1 = ctx.mkSetAdd(arr1,e);    
         // arr1= ctx.mkStore(arr1, e, True);
        }
       // System.out.println(arr1.toString());
        Expr[] args = arr1.getArgs();
         return arr1;
    }
    
   /*  static ArrayExpr getZ3ArrayFromRdf(String rdfList,String enumSortName, Context ctx) {
        SeqSort stringSort = ctx.getStringSort();
         Symbol name = ctx.mkSymbol(enumSortName);
        String[] split = rdfList.split(" ");
        String[] unique = Arrays.stream(split).distinct().toArray(String[]::new);
        Symbol[] uniqueSymbols = new Symbol[unique.length];
        for(int i=0; i< unique.length; ++i){
           uniqueSymbols[i] = ctx.mkSymbol(unique[i]);
        }
        EnumSort rSort = ctx.mkEnumSort(stringSort.getName(),uniqueSymbols);
        
        SetSort rSet = ctx.mkSetSort(rSort);

ArrayExpr rID = (ArrayExpr)ctx.mkConst(enumSortName +"arr", rSet);
System.out.println(rSort.toString());

for(int i = 0 ;i < rSort.getConsts().length; ++i)
System.out.println((rSort.getConsts()[i]));
System.out.println(rID.getSort().toString());
 return rID;
         
    }*/

    private static String XMLDataTypeToInt(String xmlString) {
        
         Pattern r = Pattern.compile("(\\d+).*");

      // Now create matcher object.
      Matcher m = r.matcher(xmlString);
      if (m.find( )) 
         return m.group(1);
     
        return null;
    }
 
    static Map<String, String> scalarDataTypes = new HashMap<>();
    
      public static void intialize() {
        scalarDataTypes.put("c:char", "%c");
        scalarDataTypes.put("c:wchar", "%c");
        scalarDataTypes.put("c:float", "%f");
        scalarDataTypes.put("c:double", "%f");
        scalarDataTypes.put("c:long_double", "%Lf");
        scalarDataTypes.put("c:int", "%d");
        scalarDataTypes.put("c:short", "%d");
        scalarDataTypes.put("c:long_long", "%lld");
        scalarDataTypes.put("c:long", "%li");
        scalarDataTypes.put("c:signed_char", "%c");
        scalarDataTypes.put("c:signed_int", "%d");
        scalarDataTypes.put("c:signed_long", "%li");
        scalarDataTypes.put("c:signed_long_long", "%lld");
        scalarDataTypes.put("c:signed_short", "%d");
        scalarDataTypes.put("c:unsigned_char", "%c");
        scalarDataTypes.put("c:unsigned_long", "%lu");
        scalarDataTypes.put("c:unsigned_long_long", "%llu");
        scalarDataTypes.put("c:unsigned_short", "%hu");
        scalarDataTypes.put("c:unsigned_int", "%u");
        //scalarDataTypes.put("c:pointer_type", "%lu");
    }

   

     static boolean isScalarType(String cType) {
        return scalarDataTypes.containsKey(cType);
    }

    static boolean isArrayMemberAccess(String rdfType) {
        if(rdfType != null && rdfType.endsWith("ArraySub"))
            return true;
        return false;
    }

    static boolean isArrayType(String cType) {
        if(cType != null && cType.endsWith("array_type"))
            return true;
        return false;
    }
    
    static boolean isPointerToComplexType(String cType, String baseType) {
    //     if(cType != null && cType.endsWith("pointer_type") && !isScalarType(baseType))
         if(cType != null && cType.endsWith("pointer_type"))
            return true;
        return false;
    }
    
    static boolean isPointerToSimpleType(String cType, String baseType) {
        if(cType != null && cType.endsWith("pointer_type") && isScalarType(baseType))
            return true;
        return false;
    }

static boolean isPointerToSimpleTypeExceptChar(String cType, String baseType) {
        if(cType != null && baseType!= null && !baseType.endsWith("char") && cType.endsWith("pointer_type") && isScalarType(baseType))
            return true;
        return false;
    }
   
   
}
