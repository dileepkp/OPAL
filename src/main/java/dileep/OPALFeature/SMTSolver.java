/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import com.microsoft.z3.Context;
import com.microsoft.z3.Log;
import com.microsoft.z3.Version;
import com.microsoft.z3.Z3Exception;
import java.util.HashMap;

/**
 *
 * @author aidb
 */
public class SMTSolver {
  
    @SuppressWarnings("serial")
    class TestFailedException extends Exception
    {
        public TestFailedException()
        {
            super("Check FAILED");
        }
    };
    
       public static void main(String[] args)
    {
        JavaExample p = new JavaExample();
        try
        {
            
            com.microsoft.z3.Global.ToggleWarningMessages(true);
            Log.open("test.log");

            System.out.print("Z3 Major Version: ");
            System.out.println(Version.getMajor());
            System.out.print("Z3 Full Version: ");
            System.out.println(Version.getString());
            System.out.print("Z3 Full Version String: ");
            System.out.println(Version.getFullVersion());

            p.simpleExample();

            { // These examples need model generation turned on.
                HashMap<String, String> cfg = new HashMap<>();
                cfg.put("model", "true");
                Context ctx = new Context(cfg);
        
                p.optimizeExample(ctx);
                p.basicTests(ctx);
                p.castingTest(ctx);
                p.sudokuExample(ctx);
                p.quantifierExample1(ctx);
                p.quantifierExample2(ctx);
                p.logicExample(ctx);
                p.parOrExample(ctx);
                p.findModelExample1(ctx);
                p.findModelExample2(ctx);
                p.pushPopExample1(ctx);
                p.arrayExample1(ctx);
                p.arrayExample3(ctx);
                p.bitvectorExample1(ctx);
                p.bitvectorExample2(ctx);
                p.parserExample1(ctx);
                p.parserExample2(ctx);
                p.parserExample4(ctx);
                p.parserExample5(ctx);
                p.iteExample(ctx);
                p.evalExample1(ctx);
                p.evalExample2(ctx);
                p.findSmallModelExample(ctx);
                p.simplifierExample(ctx);
                p.finiteDomainExample(ctx);
                p.floatingPointExample1(ctx);
                p.floatingPointExample2(ctx);
            }

            { // These examples need proof generation turned on.
                HashMap<String, String> cfg = new HashMap<String, String>();
                cfg.put("proof", "true");
                Context ctx = new Context(cfg);
                p.proveExample1(ctx);
                p.proveExample2(ctx);
                p.arrayExample2(ctx);
                p.tupleExample(ctx);
                p.parserExample3(ctx);
                p.enumExample(ctx);
                p.listExample(ctx);
                p.treeExample(ctx);
                p.forestExample(ctx);
                p.unsatCoreAndProofExample(ctx);
                p.unsatCoreAndProofExample2(ctx);
            }

            { // These examples need proof generation turned on and auto-config
              // set to false.
                HashMap<String, String> cfg = new HashMap<String, String>();
                cfg.put("proof", "true");
                cfg.put("auto-config", "false");
                Context ctx = new Context(cfg);
                p.quantifierExample3(ctx);
                p.quantifierExample4(ctx);
            }

            p.translationExample();

            Log.close();
            if (Log.isOpen())
                System.out.println("Log is still open!");
        } catch (Z3Exception ex)
        {
            System.out.println("Z3 Managed Exception: " + ex.getMessage());
            System.out.println("Stack trace: ");
            ex.printStackTrace(System.out);
        } catch (JavaExample.TestFailedException ex)
        {
            System.out.println("TEST CASE FAILED: " + ex.getMessage());
            System.out.println("Stack trace: ");
            ex.printStackTrace(System.out);
        } catch (Exception ex)
        {
            System.out.println("Unknown Exception: " + ex.getMessage());
            System.out.println("Stack trace: ");
            ex.printStackTrace(System.out);
        }
    }
}
