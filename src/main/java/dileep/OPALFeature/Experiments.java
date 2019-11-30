/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aidb
 */
public class Experiments {
    
    public static void main(String[] args) throws Exception{
        //specBenchmarks(args);
        samateBenchmarks(args);
        
    }

    static void specBenchmarks(String[] args) throws IOException {
        List<String> specBenchMarks = new ArrayList<String>();
        specBenchMarks.add("leaky_bucket");
        specBenchMarks.add("profile");
        specBenchMarks.add("iv_io");
        specBenchMarks.add("feat");
        specBenchMarks.add("sgfnode");
        specBenchMarks.add("utilities");
        specBenchMarks.add("frontmtx");
        for(String benchmark: specBenchMarks){
            System.out.println("--------------------------------------------");
            System.out.println("--------------------------------------------");
            System.out.println(benchmark);
            Settings.settingsInput = benchmark;
            Main.main(args);
        }
    }
    
    
    static void samateBenchmarks(String[] args) throws IOException {
        List<String> samateBenchMarks = new ArrayList<String>();
        samateBenchMarks.add("divideByZero_t1");
        samateBenchMarks.add("divideByZero_t2");
        samateBenchMarks.add("divideByZero_t3");
        samateBenchMarks.add("divideByZero_t4");
        samateBenchMarks.add("divideByZero_t5");
        samateBenchMarks.add("divideByZero_t6");
        samateBenchMarks.add("divideByZero_t7");
        
        
        for(String benchmark: samateBenchMarks){
            System.out.println("--------------------------------------------");
            System.out.println("--------------------------------------------");
            System.out.println(benchmark);
            Settings.settingsInput = benchmark;
            Main.main(args);
        }
    }
    
    
    
}
