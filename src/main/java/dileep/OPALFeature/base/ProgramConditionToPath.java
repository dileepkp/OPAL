/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.jena.query.QuerySolution;

/** A list of program paths having the same executable conditions 
 *
 * @author aidb
 */
public class ProgramConditionToPath {

    List<ProgramCondition> pcList;
    List<ProgramPath> ppList;
    public ProgramConditionToPath(List<ProgramCondition> _pcList) {
        pcList = _pcList;
        ppList = new ArrayList<>();
    }

    public ProgramConditionToPath(List<ProgramCondition> _pcList, List<ProgramPath> _ppList) {
        pcList = _pcList;
        ppList = _ppList;
    }

    public List<ProgramPath> getPaths() {
        return ppList;
    }

    public List<ProgramCondition> getConditions() {
        return pcList;
    }
    
    // output format is cond[pred](opt msg)->....cond[pred](opt msg)
    public String getErrorMessage(){
        // [rule1] if all the paths have same condtion and differ only in iter..
        // return msg as all iterations of the loop
        
        // [rule2] if a path has both +ve and -ve pred of the same condition it means after the 
        // iteation of the loop.
        StringBuilder error = new StringBuilder(255);
        if(ppList != null)
        ppList.forEach((pp) -> {
            error.append("\nPath: ");
            pp.getConditions().forEach((pc) -> {
                error.append(pc.condition).append("(").append(pc.pred.toString()).append(") iter:").append(pc.iter)
                        .append("-> ");
            });
        });
        
        return error.toString();
    }
    
}
