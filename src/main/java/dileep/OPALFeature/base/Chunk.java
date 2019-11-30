/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature.base;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Statement;

/**
 *
 * @author aidb
 */
public class Chunk {

    public List<Statement> stList = new ArrayList<>();
    String begin; String end;
    public Chunk(String _begin, String _end) {
        begin = _begin;
        end = _end;
        fillList();
    }

    private void fillList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
