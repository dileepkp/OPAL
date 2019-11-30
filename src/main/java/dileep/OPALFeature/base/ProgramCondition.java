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
public class ProgramCondition implements Cloneable {

    public String condition;
    public Constants.ProgramConditionTruthValue pred;
    int iter;
    public Boolean z3Allowable;
    public int executionOrder;

    public ProgramCondition(Constants.ProgramConditionTruthValue _pred, String _condition, int _iter, int _order) {
        condition = _condition;
        pred = _pred;
        iter = _iter;
        z3Allowable = true;
        executionOrder = _order;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ProgramCondition)) {
            return false;
        }

        ProgramCondition that = (ProgramCondition) other;

        // Custom equality check here.
        return this.condition.equals(that.condition)
                && this.iter == that.iter
                && this.pred == that.pred 
                && this.executionOrder == that.executionOrder;
                
    }

    @Override
    public int hashCode() {
        int hashCode = 1;

        hashCode = hashCode * 37 + this.condition.hashCode();
        hashCode = hashCode * 37 + this.iter;
        hashCode = hashCode * 37 + this.executionOrder;
        hashCode = this.pred.ordinal() + hashCode * 37;
        //hashCode = (this.pred ? 0 : 1) + hashCode * 37;
        //hashCode = (this.z3Allowable ? 0 : 1) + hashCode * 37;

        return hashCode;
    }

    public Object clone()  {

        try {
            return (ProgramCondition) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException comes out : " + e.getMessage());
        }
        return null;

    }
}
