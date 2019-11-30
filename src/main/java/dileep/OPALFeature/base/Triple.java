/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

/**
 *
 * @author aidb
 */
public class Triple {
    
    public Triple(String s, String p, String o){
        subject = s;
        predicate = p;
        object = o;
    }
    
    public String subject;
    public String object;
    public String predicate;
}
