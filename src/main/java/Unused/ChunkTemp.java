/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Unused;

import dileep.OPALFeature.ReadInput;
import dileep.OPALFeature.Settings;
import dileep.OPALFeature.Sparql;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.rdf.model.impl.StatementImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.RETERuleInfGraph;
import org.apache.jena.vocabulary.ReasonerVocabulary;

/**
 *
 * @author aidb
 */
public class ChunkTemp {
            
     public static Model processRules2(String fileloc, Model modelIn,Statement func) {
        // create a simple model; create a resource  and add rules from a file 

        Model m = ModelFactory.createDefaultModel();
        Resource configuration = m.createResource();
        configuration.addProperty(ReasonerVocabulary.PROPruleSet, fileloc);
        configuration.addProperty(ReasonerVocabulary.PROPruleMode, "forwardRETE");

        // Create an instance of a reasoner 
        Reasoner reasoner
                = GenericRuleReasonerFactory.theInstance().create(configuration);

        // reasoner.setParameter(ReasonerVocabulary.PROPtraceOn, true);
        // Now with the rawdata model & the reasoner, create an InfModel 
        
        List<Statement> topLevelStatments = getChunkBoundaries(modelIn, func.getObject().toString());
        ChunkManager.createChunks(topLevelStatments,func.getObject().toString());
        //modelIn.remove(topLevelStatments);
        InfModel infmodel = createInfModelLocal(reasoner, modelIn,topLevelStatments);

        return infmodel;

    }
     
      public static InfModel createInfModelLocal( Reasoner reasoner, Model model,List<Statement> topLevelStatments ) {
         RETERuleInfGraph graph = (RETERuleInfGraph)reasoner.bind(model.getGraph());
         InfModelImpl temp = new InfModelImpl(graph);
        Model m = ModelFactory.createDefaultModel();
         for(Statement s : topLevelStatments){
             m.removeAll();
             m.add(s);
             graph.performAdd(m.getGraph());
             // temp = new InfModelImpl(graph);
             //tr = s.asTriple();
             //graph.remove(tr.getSubject(), tr.getPredicate(), tr.getObject());
            graph.performDelete(m.getGraph());
             
             System.out.println("--------------------------------------");
             System.out.println("--------------------------------------");
         }
                      
         
         return new InfModelImpl(graph);
    }

   
     private static List<Statement> getChunkBoundaries(Model rawModel, String func) {
        List<QuerySolution> resultList;

        String ip;
        ip = ReadInput.readGeneralQuery("getAllChunks","?func", func);
        System.out.println(ip);
        Sparql.executeQueryAndPrint(ip,rawModel);
        resultList = Sparql.executeQuery(ip, rawModel);
        //Statement s = new StatementImpl
        return transformQSToStatement(resultList,"nextStatement");

    }

    private static List<Statement> transformQSToStatement(List<QuerySolution> qsList,String pred) {

        List<Statement> stList = new ArrayList();
        for (QuerySolution qs : qsList) {
            Resource varNode = qs.getResource("s");
            Property p = new PropertyImpl("http://www.semanticweb.org/aidb/ontologies/BugFindingOntology#", pred);
            RDFNode valNode = qs.get("o");
            stList.add(new StatementImpl(varNode, p, valNode));
        }
        return stList;
    }

 
    private static Model getStatements2() {
         List<Statement> stList = new ArrayList();
        Model m = ModelFactory.createDefaultModel();
        Resource varNode;
        Property p;
        Resource valNode;
        
       
    /*    
        varNode = new ResourceImpl(Settings.fileIRI + "551_17_551_21");
        p = new PropertyImpl(Settings.bugFindIRI + "nextFalseStatement");
        valNode = new ResourceImpl(Settings.fileIRI + "551_5_559_5_exit");
       // stList.add(new StatementImpl(varNode, p, valNode));
       
        varNode = new ResourceImpl(Settings.fileIRI + "551_17_551_21");
        p = new PropertyImpl(Settings.bugFindIRI + "nextTrueStatement");
        valNode = new ResourceImpl(Settings.fileIRI + "552_2_557_2");
       //stList.add(new StatementImpl(varNode, p, valNode));
       
       varNode = new ResourceImpl(Settings.fileIRI + "550_5_550_10");
        p = new PropertyImpl(Settings.bugFindIRI + "nextStatement");
        valNode = new ResourceImpl(Settings.fileIRI + "551_10_551_15");
       //stList.add(new StatementImpl(varNode, p, valNode));
        
        varNode = new ResourceImpl(Settings.fileIRI + "488_30_488_32");
        p = new PropertyImpl(Settings.bugFindIRI + "firstStatementInFunction");
        valNode = new ResourceImpl(Settings.fileIRI + "489_1_593_1");
       // stList.add(new StatementImpl(varNode, p, valNode));
        
        varNode = new ResourceImpl(Settings.fileIRI + "488_30_488_32");
        p = new PropertyImpl(Settings.bugFindIRI + "nextStatement");
        valNode = new ResourceImpl(Settings.fileIRI + "488_41_488_44");
        stList.add(new StatementImpl(varNode, p, valNode));
       
         varNode = new ResourceImpl(Settings.fileIRI + "488_41_488_44");
        p = new PropertyImpl(Settings.bugFindIRI + "nextStatement");
        valNode = new ResourceImpl(Settings.fileIRI + "488_53_488_54");
        stList.add(new StatementImpl(varNode, p, valNode));
       */
        m.add(stList);
        return m;
    }

    private static void readCFGInChunks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
