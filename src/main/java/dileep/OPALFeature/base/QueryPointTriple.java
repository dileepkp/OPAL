/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import dileep.OPALFeature.base.Triple;

/**
 *
 * @author aidb
 */
public class QueryPointTriple extends Triple {

    public String queryPoint;
    public String queryLoc;
    public String subjectType;
    public Constants.QueryConditionType objectType;
    public String queryClause;
    
    /*public QueryPointTriple(String qp,String st,String s, String p, String o) {
        super(s, p, o);
        queryPoint = qp;
        subjectType = st;
        objectType = null;
    }*/

 public QueryPointTriple(String qp,String st,Constants.QueryConditionType ot,String s, String p, String o,String qc) {
        super(s, p, o);
        queryPoint = qp;
        subjectType = st;
        objectType = ot;
        queryClause = qc;
    }
 
 public QueryPointTriple(String qp,String loc, String st,Constants.QueryConditionType ot,String s, String p, String o, String qc) {
        super(s, p, o);
        queryPoint = qp;
        subjectType = st;
        objectType = ot;
        queryLoc = loc;
        queryClause = qc;
    }
    
}
