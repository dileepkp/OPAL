/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import java.util.ArrayList;
import java.util.List;

/** Each memeber in pcList is cloned
 *
 * @author aidb
 */
public class ProgramPath {

    public String pathId;
    List<ProgramCondition> pcList;
    public ProgramPath(String _pathId) {
        pathId = _pathId;
        pcList = new ArrayList<>();
    }

    public List<ProgramCondition> getConditions() {
        return pcList;
    }
    
     @Override
    public boolean equals(Object other) {
        if (!(other instanceof ProgramPath)) {
            return false;
        }
        ProgramPath that = (ProgramPath) other;
        // Custom equality check here.
        return this.pathId.equals(that.pathId);
    }
    
    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + this.pathId.hashCode();
        hashCode = hashCode * 37 + this.pcList.hashCode();
        return hashCode;
    }
}
