/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import dileep.OPALFeature.base.Constants;
import dileep.OPALFeature.base.Constants.ProgramConditionTruthValue;
import dileep.OPALFeature.base.QueryAndProgram;
import dileep.OPALFeature.base.ProgramConditionToPath;
import dileep.OPALFeature.base.ProgramPath;
import dileep.OPALFeature.base.ProgramCondition;
import dileep.OPALFeature.base.QueryPointTriple;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;

/**
 *
 * @author aidb
 */

//Every queryPoint will have a list of parsable programConditions
    // and a set of programPaths that have the same programConditions.
    
    //ProgramCondition = (rdf:item|pa:negItem), condition, iter
    //ProgramPath = pathId(front end generated), List<ProgramCondition>, hrPath
    //ProgramConditionToPath =  List<ProgramCondition> z3ProgramConditions, List<ProgramPath>, 
    //QueryAndProgram = queryPoint, List<ProgramConditionToPath>

//The above process has to be done for each query clause

public class ProgInfo {
    public  List<QuerySolution> variableValues;
    public  List<QuerySolution> unaryConditions;
    public  List<QuerySolution> binaryConditions;
    List<QuerySolution> programConditionsInRdf;
    List<QueryAndProgram> queryAndProgramLink;
    List<ProgramCondition> progCondition;
    List<ProgramPath> progPath;
    List<ProgramConditionToPath> progCondToPath;
    List<QuerySolution> invalidCondtions;
    List<QueryPointTriple> qpt;
    
    public ProgInfo(List<QueryPointTriple> _qpt){
        progCondition = new ArrayList<>();
        progPath = new ArrayList<>();
        progCondToPath = new ArrayList<>();
        queryAndProgramLink = new ArrayList<>();
        qpt = _qpt;
    }

    void linkQueryAndProgram() {
        
         buildProgramConditions();
         // To do: convert program path id to integer using dictionary.
         
        adjustPathConditions();
        buildProgramConditionListToPathList(progCondition,progPath);
        buildQueryAndProgram();
         
    }

    String getProgConditionString() {
        List<ProgramCondition> distinctElements =  progCondition.stream().filter(distinctByKey(p -> p.condition)).
                collect(Collectors.toList());
        StringBuilder s = new StringBuilder(255);
        s.append("FILTER ( str(?exp) IN (");
        for(ProgramCondition tr: distinctElements){
         s.append("\"" +tr.condition + "\"^^xsd:string");
         s.append(",");
        }
        s.deleteCharAt(s.lastIndexOf(","));
        s.append("))");
        return s.toString();
    }

  /*  private void buildProgramConditions() {
        Boolean negation = false;
        for (QuerySolution qs : programConditionsInRdf) {
                negation = false;
                RDFNode predNode = qs.get("pred");
                RDFNode conditionNode = qs.get("condition");
                int iterNode = qs.get("iter").asLiteral().getInt();
                
                if(predNode.toString().equals(Settings.paIRI + "negItem"))
                    negation = true;
                ProgramCondition pc = new ProgramCondition(negation,conditionNode.toString(),iterNode);
                if(!progCondition.contains(pc))
                    progCondition.add(pc);
        }
    }
  */  
      private void buildProgramConditions() {
        ProgramConditionTruthValue negation = ProgramConditionTruthValue.False;
        for (QuerySolution qs : programConditionsInRdf) {
                negation = ProgramConditionTruthValue.True;
                if(qs.get("qp") == null) continue;
                RDFNode predNode = qs.get("pred");
                RDFNode conditionNode = qs.get("condition");
                int iterNode = qs.get("iter").asLiteral().getInt();
                int executionOrderNode = qs.get("order").asLiteral().getInt();
                String pathId = qs.get("pathId").toString();
                
                if(predNode.toString().equals(Settings.paIRI + "negItem"))
                    negation = ProgramConditionTruthValue.False;
                else if (predNode.toString().equals("*"))
                    negation = ProgramConditionTruthValue.EitherTrueOrFalse;
                ProgramCondition pc = new ProgramCondition(negation,conditionNode.toString(),iterNode,executionOrderNode);
                 if(!progCondition.contains(pc))
                    progCondition.add(pc);
                 else{ 
                     final ProgramCondition pc2 = pc;
                  pc = progCondition.stream().filter(p -> p.equals(pc2)).findFirst().get();
                }
                 // pc contians the current qs program condition
                 
                ProgramPath pp  = new ProgramPath(pathId);
                if(!progPath.contains(pp))
                    progPath.add(pp);
                else{
                    final ProgramPath pp2 = pp;
                    pp = progPath.stream().filter(p -> p.equals(pp2)).findFirst().get();
                }
                 // pp contains the current qs program path
                 List<ProgramCondition> temp = pp.getConditions();
                 if(!temp.contains(pc))
                     temp.add((ProgramCondition)pc.clone());
        }
    }

   

    private void buildProgramConditionListToPathList(List<ProgramCondition> progCondition, List<ProgramPath> progPath) {
       
        //Comparator<ProgramCondition> order = Comparator.comparing(p -> p.executionOrder);
       for(ProgramPath pp : progPath){
           
           //Collections.sort(pp.getConditions(),order);
            List<ProgramCondition> pcList = pp.getConditions();
            ProgramConditionToPath pcp = new ProgramConditionToPath(pcList);
            if(!progCondToPathContains(pcp))
                progCondToPath.add(pcp);
            else{
                    //final ProgramConditionToPath pcp2 = pcp;
                    //pcp = progCondToPath.stream().filter(p -> p.equals(pcp2)).findFirst().get();
                    pcp = progCondToPathFind(pcp);
                }
                
                //pcp
                
            List<ProgramPath> temp = pcp.getPaths();
            if(!temp.contains(pp))
                temp.add(pp);
        }
    }

    private void buildQueryAndProgram() {
        List<ProgramPath> temp;
        for (QuerySolution qs : programConditionsInRdf) {
            if(qs.get("qp") == null) continue;
                String queryPoint = qs.get("qp").toString();
                String path = qs.get("pathId").toString();
                ProgramPath tempPP = new ProgramPath(path);
               // boolean exists = progCondToPath.stream().anyMatch(p -> p.getPaths().contains(temp));
               // if(!exits)
              ProgramConditionToPath underlyingPctp = 
               progCondToPath.stream().filter(p -> p.getPaths().contains(tempPP)).findFirst().get();
               
              QueryPointTriple qp = qpt.stream().filter(p -> p.queryPoint.equals(queryPoint)).findFirst().get();
              
               QueryAndProgram qap = new QueryAndProgram(underlyingPctp.getConditions(),
                       underlyingPctp.getPaths(), queryPoint,qp);
               if(!queryAndProgContains(tempPP,qp.queryClause,queryPoint)){
                   queryAndProgramLink.add(qap);
                   temp = qap.getPaths();
               }
               else{
                  // final QueryAndProgram qap2 = qap;
                  // qap = queryAndProgramLink.stream().filter(p -> p.equals(qap2)).findFirst().get();
                  temp = queryAndProgFind(tempPP,qp.queryClause,queryPoint).getPaths();
               }
               
               //qap

                if(!temp.contains(tempPP))
                    temp.add(tempPP);
        }
    }
    
    public void buildQueryAndProgram(List<QueryPointTriple> queryPoints) {
        
         for(QueryPointTriple qpt : queryPoints){
          QueryAndProgram qap = new QueryAndProgram(null,null,qpt.queryPoint,qpt);
          queryAndProgramLink.add(qap);
         }
        
         
     }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    //If a path contains both +ve and -ve condition, use -ve one only
    //This occurs in loops
    private void adjustPathConditions() {
        List<ProgramCondition> pcList;
        for (ProgramPath pp : progPath){
            pcList = pp.getConditions();
            final List<ProgramCondition> pcList2 = pcList;
            List<ProgramCondition> collect = pcList2.stream().filter(p -> (pcList2.stream().anyMatch(q -> q.condition.equals(p.condition) 
                    && q.pred == ProgramConditionTruthValue.False && p.pred == ProgramConditionTruthValue.True))).collect(Collectors.toList());
           
            collect.forEach(p -> p.z3Allowable = false);
        }
    }

    private boolean progCondToPathContains(ProgramConditionToPath pcp) {
       if(progCondToPathFind(pcp)!= null)
           return true;
       return false;
        
    }

    private Boolean AreConditionListsSubset(List<ProgramCondition> ipList, List<ProgramCondition> tempList) {
      //Check based on pred and condition only
      long c = ipList.stream().filter(p -> (tempList.stream().anyMatch(q -> q.condition.equals(p.condition)
                         && q.pred == p.pred))).count();
      if(c == ipList.size())
          return true;
      return false;
    }

    

    private ProgramConditionToPath progCondToPathFind(ProgramConditionToPath pcp) {
         List<ProgramCondition> ipList = pcp.getConditions();
        Boolean first;
        Boolean second;
        for(ProgramConditionToPath temp : progCondToPath){
            first = false;
            second = false;
            List<ProgramCondition> tempList = temp.getConditions();
            first = AreConditionListsSubset(ipList,tempList);
            second = AreConditionListsSubset(tempList,ipList);
            if(first && second)
                return temp;
        }
        return null;
    }

    private boolean queryAndProgContains(ProgramPath qap,String queryClause, String qp) {
        if(queryAndProgFind(qap,queryClause,qp)!= null)
           return true; 
       return false;
    }

    private QueryAndProgram queryAndProgFind(ProgramPath pp,String queryClause,String qp) {
        //Every program path must be present in some qap
        Optional opt = queryAndProgramLink.stream().filter(p -> p.qtp.queryClause.equals(queryClause) 
                && p.getPaths().stream().anyMatch(q -> q.pathId.equals(pp.pathId))
                && p.queryPoint.equals(qp)).findFirst();
               // && p.getPaths().contains(pp)).findFirst();
        if(opt.isPresent())
            return (QueryAndProgram)opt.get();
        return null;
        
         
    }

    String getParseCondString() {
          List<String> distinctElements;
        distinctElements = binaryConditions.stream().map(p -> p.get("exp").toString()).distinct().collect(Collectors.toList());
        distinctElements.addAll(unaryConditions.stream().map(p -> p.get("exp").toString()).distinct().collect(Collectors.toList()));
        //if(distinctElements.isEmpty())
        //    return "";
        StringBuilder s = new StringBuilder(255);
        s.append("FILTER ( str(?o) IN (");
        for(String tr: distinctElements){
         s.append("\"" +tr + "\"^^xsd:string");
         s.append(",");
        }
        
        if(!distinctElements.isEmpty())
        s.deleteCharAt(s.lastIndexOf(","));
        s.append("))");
        return s.toString();
        
    }
}
