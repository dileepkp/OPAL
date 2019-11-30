/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

//import BddPackage.ProgramMetaInformation;
import Unused.ChunkManager;
import dileep.OPALFeature.base.QueryPointTriple;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import dileep.OPALFeature.base.QueryAndProgram;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import java.util.stream.Collectors;
import org.apache.jena.graph.Node;
import org.apache.jena.ontology.impl.DatatypePropertyImpl;
import org.apache.jena.ontology.impl.ObjectPropertyImpl;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.reasoner.rulesys.builtins.Bdd;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.rdf.model.impl.StatementImpl;
import org.apache.jena.rdf.model.impl.StmtIteratorImpl;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.RETERuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.reasoner.rulesys.builtins.StaticTrace;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.Filter;
import org.apache.jena.vocabulary.ReasonerVocabulary;

/**
 *
 * @author aidb
 */
public class Main {

    static List<Statement> funcDeclList = new ArrayList();
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Settings.fcStartTime = System.currentTimeMillis();
        ReadInput input = new ReadInput();
        ReadInput.readSettings();
        Settings.rawModel = getRawModel();
        getLineNumberModel();
        ProgramMetaInformation pmi = new ProgramMetaInformation();
        ProgramCodeInformation pci = new ProgramCodeInformation();
        if(Settings.bddMode){
            prepareBdd(pmi);
            pmi.createAllEncodings();
            Bdd.buildBdd(pmi);
           
        }
       
        if (Settings.forwardChainAndStop) {
            StaticTrace.initialize(pci);
            CreateModels(input);
            if(Settings.bddMode){
                Settings.bddModelInTriples = Bdd.materializeBddInTriples();
                Settings.bddModelInTriples.add(pmi.materializeEncodings());
            }
            FileOutputStream fout=new FileOutputStream(Settings.inferredFile);
            //register();
              RDFDataMgr.write(fout, Settings.postRuleModel,RDFFormat.TURTLE_FLAT) ;
              System.out.println("Size:" + Settings.postRuleModel.size());
              Settings.fcEndTime = System.currentTimeMillis();
            //rawModel.remove(func);

            Settings.fcInferenceTime = Settings.fcEndTime - Settings.fcStartTime;
            System.out.println( "\nForward Chaining time:" + Settings.fcInferenceTime + " milliseconds");

             //Settings.postRuleModel.write(fout, "TURTLE");
              
            //  DebugKnowledge.processQuery("5");
            //   input.processType1QueryConditions();
        } else {
            if(Settings.useRdf3x){
                Sparql.rdf3xQuery(Settings.dataFile, Settings.queryFile);
                return;
            }
            if(Settings.forwardChainNeeded)
            CreateModels(input);
            else{
                Settings.postRuleModel = Settings.rawModel;
            }
            //DebugKnowledge.processQuery("2");
            
            
             Model queryModel = ModelFactory.createDefaultModel();
        queryModel.read(Settings.queryFile);
        Settings.queryModel = queryModel;

           
            Settings.bcStartTime = System.currentTimeMillis();
            CheckAgent.RunAnalysis(input);
            
         /*   List<QueryPointTriple> queryPoints = QueryToZ3.readQuery(input);
            if(queryPoints == null || queryPoints.size() == 0)
                return;
           
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
             // TriplesToZ3.testZ3Assertions(solver,qap.queryPoint);
              
               solveConstraints_exp(solver,qap);
                solver.reset();
              
            }
             Settings.bcEndTime = System.currentTimeMillis();
            Settings.bcTime = Settings.bcEndTime - Settings.bcStartTime;
            System.out.println( "\n Backward chaining time:" + Settings.bcTime + " milliseconds");

           */
         

        }
    }
    
    private static void prepareBdd(ProgramMetaInformation pmi) {
        String ip = ReadInput.readGeneralQuery("getVariables",null,null);
        List<QuerySolution> entities = Sparql.executeQuery(ip, Settings.rawModel);
        List<CStatementLocation> varsWithLineNumbers = new ArrayList<>();
        for(QuerySolution qs: entities){
            varsWithLineNumbers.add(new CStatementLocation(qs.get("entity").toString()));
        }
        Collections.sort(varsWithLineNumbers, Comparator.comparingInt((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol));
        /*varsWithLineNumbers.stream().sorted(Comparator.comparing((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol)).collect(Collectors.toList());
*/
        pmi.variables = varsWithLineNumbers;
                
        ip = ReadInput.readGeneralQuery("getStatements",null,null);
        entities = Sparql.executeQuery(ip, Settings.rawModel);
         List<CStatementLocation> stsWithLineNumbers = new ArrayList<>();
        for(QuerySolution qs: entities){
            stsWithLineNumbers.add(new CStatementLocation(qs.get("entity").toString()));
        }
        Collections.sort(stsWithLineNumbers, Comparator.comparingInt((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol));
        /*varsWithLineNumbers.stream().sorted(Comparator.comparing((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol)).collect(Collectors.toList());
*/
        pmi.statements = stsWithLineNumbers;
        
         ip = ReadInput.readGeneralQuery("getConditions",null,null);
        entities = Sparql.executeQuery(ip, Settings.rawModel);
         List<CStatementLocation> condsWithLineNumbers = new ArrayList<>();
        for(QuerySolution qs: entities){
            condsWithLineNumbers.add(new CStatementLocation(qs.get("entity").toString(),qs.get("func").toString()));
        }
        Collections.sort(condsWithLineNumbers, Comparator.comparingInt((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol));
        /*varsWithLineNumbers.stream().sorted(Comparator.comparing((CStatementLocation l) ->l.beginLine)
                .thenComparingInt((CStatementLocation l1) -> l1.beginCol)).collect(Collectors.toList());
*/
        pmi.conditions = condsWithLineNumbers;
        
/*        ip = ReadInput.readGeneralQuery("getConditions",null,null);
        pmi.conditions = Sparql.executeQuery(ip, Settings.rawModel);
*/
        
    }

    private static void CreateModels(ReadInput input) {

        Model rawModel = Settings.rawModel;
      
        List<CStatementLocation> stsWithLineNumbers = new ArrayList<>();
        StmtIterator listStatements = rawModel.listStatements();
        while(listStatements.hasNext()){
            stsWithLineNumbers.add(new CStatementLocation(listStatements.nextStatement()));
        }

        List<Statement> allFunctions = getFunctions(input, rawModel, Settings.executeFunction,true);
        List<Statement> functions ;//= getFunctions(input, rawModel, Settings.executeFunction,true);
        
        if(Settings.forwardChainOnlyFclose)
            functions = getFunctions(input, rawModel, Settings.executeFunction,false);
        else
            functions = allFunctions;
        rawModel.remove(allFunctions);

        Model postRuleModel = ModelFactory.createDefaultModel();
        Model interimModel;

        List<Statement> execFuncStatements = functions;
        if (Settings.executeFunction != null) {
            execFuncStatements = functions.stream().filter(p -> p.getObject().toString().equals(Settings.executeFunction)).collect(Collectors.toList());
        };
        
        

       for (Statement func : execFuncStatements) {
            
            Model funcModel = getModelOfFunction(func,stsWithLineNumbers);
            funcModel.add(func);

            //rawModel.add(func);

            Settings.startTime = System.currentTimeMillis();
            interimModel = ForwardChain(funcModel,func);    
            postRuleModel.add(interimModel);
            Settings.endTime = System.currentTimeMillis();
            //rawModel.remove(func);

            Settings.inferenceTime += Settings.endTime - Settings.startTime;
            System.out.println("func" + func.toString() + "\nGraph inference time:" + Settings.inferenceTime + " milliseconds");

        }
       
        Settings.postRuleModel = postRuleModel;
        Settings.postRuleModel.add(Settings.rawModel);
       
    }
    
     
    private static List<Statement> transformQSToStatement(List<QuerySolution> qsList,String pred) {

        List<Statement> stList = new ArrayList();
        for (QuerySolution qs : qsList) {
            Resource varNode = qs.getResource("s");
            Property p = new PropertyImpl("http://www.semanticweb.org/aidb/ontologies/BugFindingOntology#", pred);
            RDFNode valNode = qs.get("o");
            stList.add(new StatementImpl(varNode, p, valNode));
            
            Resource varNode2 = qs.getResource("decl");
            Property p2 = new PropertyImpl("http://www.semanticweb.org/yzhao30/ontologies/2015/7/c#", "hasDefinition");
            //RDFNode valNode2 = qs.get("decl");
            funcDeclList.add(new StatementImpl(varNode2, p2, valNode));
            
            
        }
        return stList;
    }


    
    private static List<Statement> getFunctions(ReadInput input, Model rawModel, String func,boolean allFunc) {
        List<QuerySolution> resultList;

        String ip;
        if(!allFunc)
            ip = ReadInput.readGeneralQuery("getAllFunctionsWithFclose", null,null);
        else          
            ip = ReadInput.readGeneralQuery("getAllFunctions", null,null);
        //Sparql.executeQueryAndPrint(ip,rawModel);
        resultList = Sparql.executeQuery(ip, rawModel);
        //Statement s = new StatementImpl
        return transformQSToStatement(resultList,"beginExecutionInFunction");

    }
    private static Model ForwardChain(Model rawModel,Statement func) {

        Model postRuleModel;
        if(Settings.chunkingNeeded)
            postRuleModel = Unused.ChunkTemp.processRules2(Settings.ruleFile, rawModel,func);
        else
            postRuleModel = processRules(Settings.ruleFile, rawModel,func);
        register();
        return postRuleModel;
    }

    private static Model getRawModel() {
        // create a basic RAW model that can do no inferencing

        Model rawModel = ModelFactory.createDefaultModel();
        rawModel.read(Settings.dataFile);
        if (Settings.cfgFile != null ) {
            Model cfgModel = ModelFactory.createDefaultModel();
            cfgModel.read(Settings.cfgFile);
            rawModel.add(cfgModel);
        }
        //rawModel.listStatements().toList();
        return rawModel;
    }

    public static Model processRules(String fileloc, Model modelIn,Statement func) {
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
        InfModel infmodel = ModelFactory.createInfModel(reasoner, modelIn);

        return infmodel;

    }
    public static void printStatements(Model m, Resource s, Property p, Resource o) {

        PrintUtil.registerPrefix("x", "http://www.codesupreme.com/#");
        for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
        }

    }

    public static void printInferredStatements(final Model baseModel, Model inferred) {

        // An iterator over the statements of pModel that *aren't* in the base model.
        ExtendedIterator<Statement> stmts = inferred.listStatements().filterDrop(new Filter<Statement>() {
            @Override
            public boolean accept(Statement o) {
                return baseModel.contains(o);
            }
        });

        Model deductions = ModelFactory.createDefaultModel().add(new StmtIteratorImpl(stmts));

        //PrintUtil.registerPrefix("x", "http://www.codesupreme.com/#"); 
        for (StmtIterator i = deductions.listStatements(); i.hasNext();) {
            Statement stmt = i.nextStatement();
            System.out.println(PrintUtil.print(stmt));
        }
        System.out.println("count:" + deductions.listStatements().toList().size());
    }

    public static void register() {
        PrintUtil.registerPrefix("pa", "http://www.semanticweb.org/aidb/ontologies/BugFindingOntology#");
        PrintUtil.registerPrefix("file0", "ftp:/home/demo/Dropbox/pato/tools/onto-build-rose/test/spec445/sgf/sgfnode.c");
        PrintUtil.registerPrefix("c", "http://www.semanticweb.org/yzhao30/ontologies/2015/7/c#");

    }

    private static void solveConstraints(Solver solver, QueryAndProgram qap) {
        if (Status.UNSATISFIABLE == solver.check()) {
            System.out.println(qap.queryPoint + ": The query is safe and there is no bug");
        } else {
            System.out.println(qap.queryPoint + ": The query is unsafe and there is bug");
            if(Settings.forwardChainNeeded)
                System.out.println(qap.getErrorMessage());
            else
            { TriplesToZ3.testZ3Assertions(solver, qap.queryPoint);
                
            }
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
    
    public static void solveConstraints_exp(Solver solver, QueryAndProgram qap) {
        if (Status.UNSATISFIABLE != solver.check()) {
            System.out.println(qap.queryPoint + ":Bug");
            if(Settings.forwardChainNeeded)
                System.out.println(qap.getErrorMessage());
            else
            { TriplesToZ3.testZ3Assertions(solver, qap.queryPoint);
                
            }
        }
        //System.out.println("------------------------------------------------------------------------------------------");
    }

    private static void getLineNumberModel() {
        
    }
   // static int temp = 0;
    private static Model getModelOfFunction(Statement funcUri, List<CStatementLocation> stsWithLineNumbers) {
        //CStatementLocation func = new CStatementLocation(funcUri.toString());
        Statement funcDef = funcDeclList.stream().filter(p -> p.getObject().toString().equals(
                funcUri.getObject().toString())).findFirst().get();
        
        CStatementLocation funcDefCsl = new CStatementLocation(funcDef.getSubject().toString());
        Model funcModel = ModelFactory.createDefaultModel();
        for(CStatementLocation csl : stsWithLineNumbers){
            
            if(csl.isInScopeOf(funcDefCsl))
                funcModel.add(csl.stUri);
        }
       // temp+=funcModel.size();
       // System.out.println("temp:" + temp);
        return funcModel;
    }

  

    
    
    public Model loadModel2 (String label) {

 Dataset dataset=TDBFactory.createDataset(label);
    Model model=dataset.getDefaultModel();
        return model;
    }


}

//file0:488_30_488_32 pa:firstStatementInFunction file0:489_1_593_1
//file0:488_30_488_32 pa:nextStatement file0:488_41_488_44 .
//file0:488_41_488_44 pa:nextStatement file0:488_53_488_54 .

//file0:550_5_550_10 pa:nextStatement file0:551_10_551_15 .


//file0:562_5_569_5 pa:nextTrueStatement file0:563_2_567_2 .
//file0:562_5_569_5 pa:nextFalseStatement file0:562_5_569_5_exit .