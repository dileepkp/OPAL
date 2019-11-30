/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import dileep.OPALFeature.base.Constants;
import dileep.OPALFeature.base.QueryPointTriple;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author aidb
 */
public class ReadInput {

    private static String prefix = null;

    static void readSettings() {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/settings.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

             String expression = "//input[@id='" + Settings.settingsInput + "']/cFile";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            Settings.cFile = node.getTextContent().trim();

             expression = "//input[@id='" + Settings.settingsInput + "']/dataFile";
             node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            Settings.dataFile = node.getTextContent().trim();
            
            Settings.inferredFile = Settings.dataFile.replaceAll(".ttl$", "_inferred.ttl");

            expression = "//input[@id='" + Settings.settingsInput + "']/cfgFile";
            node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            if(node != null)
            Settings.cfgFile = node.getTextContent().trim();

            expression = "//inputgroup[input/@id='" + Settings.settingsInput + "']/queryFile";
            node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            if(node == null){
            expression = "//queryFile[@id='" + Settings.analysisInput + "']";
            node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            }
            Settings.queryFile = node.getTextContent().trim();

            expression = "//input[@id='" + Settings.settingsInput + "']/prefix";
            node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            if(node != null)
            s = node.getTextContent().trim();
            Settings.fileIRI = s;

            if (prefix != null && s != null) {
                prefix = prefix.replace("zzz", s);
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ReadInput() {
        prefix = readPrefixQuery();

    }

    public static String readInput(String ip, String par1) {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/propagateAgentQueries.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + ip + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s = node.getTextContent().trim();

            if (par1 != null) {
                s = Utils.replaceLast(s, "}", par1 + "}");
            }
            /* expression = "//query[@id='"+ "0" +"']";
        node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        s1 = node.getTextContent().trim(); //to get prefixes
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + s;
    }

    public static String readGeneralQuery(String ip, String par1,String parValue) {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/generalQueries.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + ip + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s = node.getTextContent().trim();

            /*if (par1 != null) {
                s = s.replace("}", "FILTER(str(?o) = \"" + par1 + "\"^^xsd:string)}");
            }
             */
            if (par1 != null) {
                //s = Utils.replaceLast(s, "}", "FILTER(str(?func) = \"" + par1 + "\"^^xsd:string)}");
                s = Utils.replaceLast(s, "}", "FILTER(str("+ par1 + ") = \"" + parValue + "\"^^xsd:string)}");
            }

            //System.out.println(s);
            // expression = "//query[@id='"+ "0" +"']";
            // node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            // s1 = node.getTextContent().trim(); //to get prefixes
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + s;
    }

    public String readPrefixQuery() {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/prefix/prefixQuery.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + "0" + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s1 = node.getTextContent().trim(); //to get prefixes

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s1;
    }

    public static String readDebugQuery(String ip) {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/debugKnowledge.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + ip + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s = node.getTextContent().trim();

            /* expression = "//query[@id='"+ "0" +"']";
        node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        s1 = node.getTextContent().trim(); //to get prefixes
             */
            //s1 = readPrefixQuery();
            // System.out.println(prefix+s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + s;
    }

    /*
    public String readExecutionOrderQueries(String ip) {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/executionOrderQueries.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + ip + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s = node.getTextContent().trim();

            // expression = "//query[@id='"+ "0" +"']";
        //node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        //s1 = node.getTextContent().trim(); //to get prefixes
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + s;
    }

    public String readExecutionOrderQueriesWithOneParameter(String ip, String par1) {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/executionOrderQueries.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            String expression = "//query[@id='" + ip + "']";
            Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
            s = node.getTextContent().trim();

            s = s.replace("}", "FILTER(?par1 = \"" + par1 + "\"^^xsd:string)}");
            //System.out.println(s);

            // expression = "//query[@id='"+ "0" +"']";
        //node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        //s1 = node.getTextContent().trim(); //to get prefixes
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + s;
    }
     */
    private static String getPrefix(XPath xpath, Document doc) throws XPathExpressionException, DOMException {
        String expression;
        Node node;
        String s1;
        expression = "//prefix[@id='" + "0" + "']";
        node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        s1 = node.getTextContent().trim(); //to get prefixes
        return s1;
    }

    /*  // var val relation
   public static void processType1QueryConditionsInXML(){
      String s = null;
        String s1 = null;
        try {	
         File inputFile = new File("src/main/resources/analysisQuery.xml");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         
        XPath xpath = XPathFactory.newInstance().newXPath();
       
        String expression = "//queryCondition";
      // NodeList nl = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
         Node node = (Node) xpath.compile(expression).evaluate(doc, XPathConstants.NODE);
       NodeList nl = node.getChildNodes();
       
       String lhs;
       String rhs;
       String relation;
       NamedNodeMap nnm;
        List<QuerySolution> qs;
         for(int i=0; i< nl.getLength();++i){
            
             Node nNode = nl.item(i);
           
             if(nNode.getNodeName().equals("lhs")){
                 lhs = nNode.getTextContent().trim();
                 nnm = nNode.getAttributes();
                 if(nnm != null && nnm.getNamedItem("source") != null)
                    if(nnm.getNamedItem("source").getTextContent().equals("sparql"))
                        qs = Sparql.executeQuery(getPrefix(xpath, doc)+ lhs, Settings.postRuleModel);
              }
            
             // if(nNode.getNodeName().equals("rhs"))
             //System.out.println(nNode.getA);
             //NamedNodeMap nnm = nNode.getAttributes();
              //if(nnm != null && nnm.getNamedItem("type") != null)
               //  System.out.println(nnm.getNamedItem("type").getTextContent());
              // System.out.println(nNode.getTextContent().trim());
   
       
         }    
      } catch (Exception e) {
         e.printStackTrace();
      }
        //return s1+s;
   }  
     */
    public static List<QueryPointTriple> processQueryConditions() {
        String s = null;
        String s1 = null;
        try {
            File inputFile = new File("src/main/resources/checkAgentQueries.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :"
                    + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();

            List<QuerySolution> qsList;
            s = evaluateXML(xpath, doc, 1);
           
            qsList = Sparql.executeQuery(prefix + s, Settings.queryModel);
            List<QueryPointTriple> queryCondition = new ArrayList<>();

            for (int i = 0; i < qsList.size(); ++i) {

                String queryModel = qsList.get(i).get("model").toString();
                String queryLoc = qsList.get(i).get("location").toString();
                String queryClause = qsList.get(i).get("s").toString();

                if (queryModel.equals(Settings.paIRI + "VarRelVal")) {
                    s = evaluateXML(xpath, doc, 2);
                    s = Utils.replaceLast(s, "}", "FILTER(str(?query) = \"" + qsList.get(i).get("s").toString() + "\"^^xsd:string)}");

                    queryCondition.addAll(buildQueryObject(s,queryLoc,queryClause));
                } else if (queryModel.equals(Settings.paIRI + "VarRelVar")) {
                    s = evaluateXML(xpath, doc, 3);
                    s = Utils.replaceLast(s, "}", "FILTER(str(?query) = \"" + qsList.get(i).get("s").toString() + "\"^^xsd:string)}");
                    queryCondition.addAll(buildQueryObjectVarRelVar(s,queryLoc,queryClause));
                } else if (queryModel.equals(Settings.paIRI + "SPARQL")) {
                    s = evaluateXML(xpath, doc, 4);
                    qsList = Sparql.executeQuery(prefix + s, Settings.queryModel);
                    s = qsList.get(0).get("val").toString();
                   // System.out.println(prefix +s);
                    Sparql.executeQueryAndPrint(prefix + s, Settings.postRuleModel,true);
                    
                    //queryCondition = null;
                }
            }

            return queryCondition;

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return s1+s;
        return null;
    }

    static String evaluateXML(XPath xpath, Document doc, int id) throws XPathExpressionException, DOMException {
        String s;
        String expression = "//query[@id='" + id + "']";
        Node node = (Node) xpath.evaluate(expression, doc, XPathConstants.NODE);
        s = node.getTextContent().trim();
        return s;
    }

    static List<QueryPointTriple> buildQueryObject(String s,String queryLoc,String queryClause) {
       
        List<QuerySolution> qsList;
        List<QuerySolution> varList;
        List<QueryPointTriple> queryCondition = new ArrayList<QueryPointTriple>();
          
        // Sparql.executeQueryAndPrint(prefix +s, Settings.queryModel);
        qsList = Sparql.executeQuery(prefix + s, Settings.queryModel);
        for (QuerySolution qs : qsList) {
            RDFNode lhsValNode = qs.get("lhsVal");
            RDFNode rhsValNode = qs.get("rhsVal");
            RDFNode relValNode = qs.get("relVal");
            String rhsType = qs.get("rhsType").toString();
            Constants.QueryConditionType rhsEnum = Constants.getQueryConditionTypeEnum(rhsType);
         // System.out.println(prefix + lhsValNode.toString());
            varList = (Sparql.executeQuery(prefix + lhsValNode.toString(), Settings.postRuleModel));
            
           
           //Sparql.executeQueryAndPrint(prefix + lhsValNode.toString(), Settings.postRuleModel);

            for (QuerySolution qs2 : varList) {
                RDFNode queryPointNode = qs2.get("exp");
                RDFNode varNode = qs2.get("var");
                RDFNode varTypeNode = qs2.get("varType");
                if(queryPointNode != null)               
                queryCondition.add(new QueryPointTriple(queryPointNode.toString(), queryLoc, varTypeNode.toString(), rhsEnum, varNode.toString(), relValNode.toString(), rhsValNode.toString(),queryClause));
            }
        }
        return queryCondition;
    }

    static List<QueryPointTriple> buildQueryObjectVarRelVar(String s,String queryLoc,String queryClause) {
      
        List<QuerySolution> qsList;
        List<QuerySolution> varList, varList2;
        List<QueryPointTriple> queryCondition = new ArrayList<QueryPointTriple>();
        // Sparql.executeQueryAndPrint(s1+s, Settings.queryModel);
        qsList = Sparql.executeQuery(prefix + s, Settings.queryModel);
        for (QuerySolution qs : qsList) {
            RDFNode lhsValNode = qs.get("lhsVal");
            RDFNode rhsValNode = qs.get("rhsVal");
            RDFNode relValNode = qs.get("relVal");
            String rhsType = qs.get("rhsType").toString();
            Constants.QueryConditionType rhsEnum = Constants.getQueryConditionTypeEnum(rhsType);

            //   System.out.println(lhsValNode.toString());
            //   System.out.println(rhsValNode.toString());
            //   System.out.println(relValNode.toString());
            //Sparql.executeQueryAndPrint(prefix + lhsValNode.toString(), Settings.postRuleModel);
            //Sparql.executeQueryAndPrint(prefix + rhsValNode.toString(), Settings.postRuleModel);
            varList = (Sparql.executeQuery(prefix + lhsValNode.toString(), Settings.postRuleModel));
            varList2 = (Sparql.executeQuery(prefix + rhsValNode.toString(), Settings.postRuleModel));

            for (QuerySolution qs1 : varList) {
                String queryPointNode = qs1.get("exp").toString();
                RDFNode varNode = qs1.get("var");
                RDFNode varTypeNode = qs1.get("varType");
                for (QuerySolution qs2 : varList2.stream().filter(p -> p.get("exp").toString().equals(queryPointNode)).collect(Collectors.toList())) {
                    String varNode2 = qs2.get("var").toString();
                    queryCondition.add(new QueryPointTriple(queryPointNode, queryLoc, varTypeNode.toString(), rhsEnum, varNode.toString(), relValNode.toString(), varNode2,queryClause));
                }

            }
        }
        return queryCondition;
    }

   

}