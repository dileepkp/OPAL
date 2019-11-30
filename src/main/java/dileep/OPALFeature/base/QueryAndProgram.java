/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import java.util.List;

/** For each queryPoint, conditions is the list of conditions at that point.
 * paths is the set of paths having the same conditions.
 *
 * @author aidb
 */
public class QueryAndProgram extends ProgramConditionToPath {

   public String queryPoint;
   public QueryPointTriple qtp;
    public QueryAndProgram(List<ProgramCondition> conditions, List<ProgramPath> paths, String _queryPoint,QueryPointTriple _qp) {
        super(conditions,paths);
        queryPoint = _queryPoint;
        qtp = _qp;
    }
 
}
