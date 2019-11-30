/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import dileep.OPALFeature.Settings;

/**
 *
 * @author aidb
 */
public class Constants {
       public enum QueryConditionType{
    Value,ValueList,variable
}
       
        public enum ProgramConditionTruthValue{
    True,False,EitherTrueOrFalse
}


   public static QueryConditionType getQueryConditionTypeEnum(String rhsType) {
        if(rhsType.equals(Settings.paIRI + "value"))
            return QueryConditionType.Value;
        else if(rhsType.equals(Settings.paIRI + "valueList"))
            return QueryConditionType.ValueList;
        else if(rhsType.equals(Settings.paIRI + "variable"))
            return QueryConditionType.variable;
        
        return null;
    }
    
}
