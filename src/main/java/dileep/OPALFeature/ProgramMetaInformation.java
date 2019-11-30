/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.LiteralImpl;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.rdf.model.impl.StatementImpl;

/**
 *
 * @author aidb
 */
public class ProgramMetaInformation {

    

    private void createVariableEncoding() {
        variableBits = (int) ((Math.log(variables.size()) / Math.log(2)) + 1);
        //ArrayList<Integer> code = binaryCode(variableBits);
        int b = 0;
        for(CStatementLocation qs: variables){
            int hashCode = qs.statement.hashCode();
            variableMap.put(hashCode, ++b);
            //System.out.println(qs.statement+ " " + b);
            
          Resource  subject = new ResourceImpl(qs.statement);
          Literal value = ResourceFactory.createTypedLiteral(b);
          entityRdf.add(new StatementImpl(subject, hasEncoding, value));
       
        }
       
    }

    void createAllEncodings() {
        createVariableEncoding();
        createStatementEncoding();
       createConditionEncoding();
       createInequalityEncoding();
       values = (long) Math.pow(2, 32);
       
       //System.out.println(getVariableCount());
        
    }

    private void createStatementEncoding() {
         statementBits = (int) ((Math.log(statements.size()) / Math.log(2)) + 1);
        ArrayList<Integer> code = grayCode(statementBits);
        int b = 0;
        for(CStatementLocation qs: statements){
            int hashCode = qs.statement.hashCode();
            statementMap.put(hashCode, code.get(++b));
           // System.out.println(qs.statement+ " " + code.get(b));
           Resource  subject = new ResourceImpl(qs.statement);
          Literal value = ResourceFactory.createTypedLiteral(b);
          entityRdf.add(new StatementImpl(subject, hasEncoding, value));
        }  
    }

/*    private void createConditionEncoding() {
        final int b = 0;
        List<String> functions = conditions.stream().map(p -> p.get("function").toString()).distinct().collect(Collectors.toList());
        for(String f : functions){
            List<QuerySolution> querySolution = conditions.stream().filter(p -> p.get("function").toString().equals(f)).collect(Collectors.toList());
        
               conditionMap.put(cond.toString().hashCode(), ++b);
        }
        
       
    }
*/
    
    private void createConditionEncoding() {
        Map<String, Long> functions = conditions.stream().collect(Collectors.groupingBy(x -> x.function, Collectors.counting()));
        String preFunc ="";
        //System.out.println(collect);
       
       // ArrayList<Integer> code = binaryCode(conditionBits);
        int b = 1;
        int hashCode2 = 0,size = 0;
        for(CStatementLocation qs: conditions){
            int hashCode = qs.statement.hashCode();
          //  conditionMap.put(hashCode, ++b);
           //System.out.println(qs.statement+ " " + b);
             Resource  subject = new ResourceImpl(qs.statement);
          Literal value = ResourceFactory.createTypedLiteral(b);
          entityRdf.add(new StatementImpl(subject, hasEncoding, value));
        
          if(!preFunc.equalsIgnoreCase(qs.function)){
              if(!preFunc.equals("")) b++;
               preFunc = qs.function;
                hashCode2 = qs.function.hashCode();
               size = functions.get(qs.function).intValue() + 1;
               conditionFuncMap.put(hashCode2, new Integer[] {hashCode2,b,size});
               
                   subject = new ResourceImpl(qs.function);
           value = ResourceFactory.createTypedLiteral(size);
          entityRdf.add(new StatementImpl(subject, hasEncodingSize, value));
        
           }
           conditionFuncMap.put(hashCode, new Integer[] {hashCode2,b,size});
           b++;
        } 
        conditionBits = conditions.size() + conditionFuncMap.size();
       	/*	for (Map.Entry<Integer, Integer[]> entry : funcConditionMap.entrySet()) {
                    Integer [] array = entry.getValue();
		    System.out.println(entry.getKey() + " = " + array[0] + " " + array[1]);
		}
*/
       
    }

    private void createInequalityEncoding() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getEncodingOfEntity(Entity entity, String stateRelation) {
        if(stateRelation.equals(Settings.paIRI + "notEqual"))
            return Inequality.neq.ordinal();
         if(stateRelation.equals(Settings.paIRI + "equals"))
            return Inequality.eq.ordinal();
        if(stateRelation.equals(Settings.paIRI + "greaterEqual"))
            return Inequality.geq.ordinal();
        if(stateRelation.equals(Settings.paIRI + "greaterThan"))
            return Inequality.gt.ordinal();
        if(stateRelation.equals(Settings.paIRI + "lessEqual"))
            return Inequality.leq.ordinal();
        if(stateRelation.equals(Settings.paIRI + "lessThan"))
            return Inequality.lt.ordinal();
       
        return 0;
    }

    Model materializeEncodings() {
        //for()
        return null;
    }

  
  
    public enum Entity{
        Condition,Statement,iter, Var,inequality,Value
    }
    
    public enum Inequality{
        eq,neq,gt, geq, lt, leq
    }
    public List<CStatementLocation> variables;
    public List<CStatementLocation> statements;
    public List<CStatementLocation> conditions;
    int variableBits;
    int statementBits;
    int conditionBits;
    int inequalityBits;
    long values;
    public HashMap<Integer, Integer> variableMap;
    public HashMap<Integer, Integer> statementMap;
   // public HashMap<Integer, Integer> conditionMap;
    public HashMap<Integer, Integer[]> conditionFuncMap;
    /*  List<Statement> stList = new ArrayList();
        List<Statement> varList = new ArrayList();
          List<Statement> condList = new ArrayList();
     */ List<Statement> entityRdf = new ArrayList();
    Property hasEncoding = new PropertyImpl(Settings.bugFindIRI + "hasEncoding");
    Property hasEncodingSize = new PropertyImpl(Settings.bugFindIRI + "hasEncodingSize");
    
    //public int noOfFunctions;
    //public int noOfStatements;
    
    public ProgramMetaInformation(){
        variableMap = new HashMap<>();
        statementMap = new HashMap<>();
        //conditionMap = new HashMap<>();
        conditionFuncMap = new HashMap<>();
                
    }
    public int getEncodingOfEntity(Entity ent,int value){
        /*if(ent == Entity.Condition)
            return conditionMap.get(value); */
        if(ent == Entity.Condition){
            Integer[] get = conditionFuncMap.get(value);
            return get[1];
        }
        else if(ent == Entity.Statement){
            if(statementMap.containsKey(value))
                return statementMap.get(value);
            else
                return -1;
        }
        else if(ent == Entity.Var)
            return variableMap.get(value);
        return 0;
    }
    
    public int getEntityFromEncoding(Entity ent,int value){
        return 0;
    }
    
    
    public int getVariableCount() {
        return (int) (Math.pow(2, variableBits) + Math.pow(2, statementBits) +
                conditionBits +3 + 3 + 32);
        //3 for iter
        //3 for inequalities
        // 32 for values
        
    }
  
    public long[] getBddBlockSizes(){
        long[] sizes = new long[6];
        sizes[0] = conditions.size() + conditionFuncMap.size();
        sizes[1] = statements.size();
        sizes[2] = 8;
        sizes[3] = variables.size();
        sizes[4] = Inequality.values().length;
        sizes[5] = values;
        return sizes;
    }
    
    // Taken from https://gist.github.com/wayetan/8156551
    // Given a non-negative integer n representing the total number of bits in the code,
    // print the sequence of gray code. A gray code sequence must begin with 0.
     private ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(n <= 1){
            res.add(0);
            if(n == 1)
                res.add(1);
            return res;
        }
        ArrayList<Integer> prev = grayCode(n - 1);
        int highest_bit = 1 << (n - 1);
        for(int i = prev.size() - 1; i >= 0; i--)
            res.add(prev.get(i) + highest_bit);
        prev.addAll(res);
        return prev;
    }
     
      private ArrayList<Integer> binaryCode(int n) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(n <= 1){
            res.add(0);
            if(n == 1)
                res.add(1);
            return res;
        }
        
        for(int i = 0; i< Math.pow(2,n); ++i)
            res.add(i);
        return res;
    }
    
}
