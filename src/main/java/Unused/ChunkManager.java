/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Unused;

import dileep.OPALFeature.base.Chunk;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author aidb
 */
public class ChunkManager {
    
    public static List<Chunk> chunks = new ArrayList<>();
    public static void createChunks(List<Statement> topLevelStatments,String func){
        
        Chunk c = new Chunk(func, topLevelStatments.get(0).getSubject().toString());
        chunks.add(c);
        for(int i=0; i< topLevelStatments.size() -1 ; ++i ){
            Statement s = topLevelStatments.get(i);
            Statement s1 = topLevelStatments.get(i + 1);
           c = new Chunk(s.getSubject().toString(), s1.getSubject().toString()) ;
           chunks.add(c);
        }
        c = new Chunk(topLevelStatments.get(0).getSubject().toString(),func);
        chunks.add(c);
    }
    
}
