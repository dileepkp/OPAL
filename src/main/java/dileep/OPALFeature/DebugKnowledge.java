/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

/**
 *
 * @author aidb
 */
public class DebugKnowledge {
     public static void processQuery(String ip){
        if(ip != null){
        String query = ReadInput.readDebugQuery(ip);
        Sparql.executeQueryAndPrint(query, Settings.postRuleModel);
        }
    }
}
