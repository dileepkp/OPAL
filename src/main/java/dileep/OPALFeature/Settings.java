/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.net.URI;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author aidb
 */
public class Settings {
    //word2vec
    //writeToFile3
    //test_div_zero
    //maintenance
    //rose_lu_single
    //stream/simple_stream_checks
    //stream/leaky_bucket
    
  static String inferredFile;
    static String dataFile;// = "src/main/resources/input/stream/leaky_bucket.ttl";
    //static String cfgFile = null;
    static String cfgFile;// = "src/main/resources/input/stream/leaky_bucket_cfg.ttl"; _oct15_2018
    static String ruleFile = "src/main/resources/ForwardChaining/programAnalysisRules.txt";
    static String testdataFile = "src/main/resources/testdata.ttl";
    static String testruleFile = "src/main/resources/testrules.txt";
    static String queryFile = "src/main/resources/analysis/canonicalLoopAnalysis.ttl";
   static String cFile;
   
    //conditionReadability
    //forLoopMaintenance
    //canonicalLoop
      
    static long startTime;
    static long endTime;
    static long inferenceTime;
    static long solverBeginTime;
    static long solverEndTime;
    static long solverTime = 0;
    
  
    
    static Model rawModel;
    static Model postRuleModel = ModelFactory.createDefaultModel(); ;
    static Model queryModel;
    static Model bddModelInTriples;
    
    static String bugFindIRI = "http://www.semanticweb.org/aidb/ontologies/BugFindingOntology#";
    static String ontologyIRI = "http://www.semanticweb.org/yzhao30/ontologies/2015/7/c#";
    public static String paIRI = "http://www.semanticweb.org/aidb/ontologies/BugFindingOntology#";
    static String intURI = "^^http://www.w3.org/2001/XMLSchema#integer";
    static String fileIRI;
    
    static String analysisInput= "count";
    static String settingsInput ="utilities";
    
    
   //  static String executeFunction = "ftp:/home/demo/Dropbox/pato/tools/onto-build-rose/test/spec/feat.c#755_1_776_1";
  static String executeFunction = null;
   static boolean useRdf3x = false;
    
   static boolean bddMode = false;
    static Boolean forwardChainAndStop = false; // used for storign propagate agent result
    static boolean forwardChainNeeded = false; 
    static boolean forwardChainOnlyFclose = false;//perform forward chaining
    //only on the functions having fclose else perform on all functions
    
    static boolean useInvalidCond = false;
    static boolean chunkingNeeded = false;
    static long bcStartTime;
    static long bcEndTime;
    static long bcTime;
    static long fcStartTime;
    static long fcEndTime;
    static long fcInferenceTime;
    
    
    
    
    
    
}
/*
if(isa<CallInst>(&(*i)) ) {
StringRef fname = cast<CallInst>(&(*i))->getCalledFunction()->getName();
if(fname.compare("printf") != 0)
continue;
}*/