/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dileep.OPALFeature;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author aidb
 */
public class CodeMultiplier {

    public static void main(String[] args) throws Exception {
        //synthetic_loop();
        //synthetic2_loop();
       // TrutleToNTriples();
      // synthetic_stream();
      synthetic3_loop();
    }

    private static void TrutleToNTriples() throws Exception {
     /*  String path = "/home/aidb/nas-synthetic/";
        String destPath = "/home/aidb/nas-synthetic/";
        replaceCode(path + "synthetic_loop.ttl", destPath + "rdf3x_loop.ttl");
        replaceCode(path + "synthetic_loop1.ttl", destPath + "rdf3x_loop1.ttl");
        replaceCode(path + "synthetic_loop10.ttl", destPath + "rdf3x_loop10.ttl");
        replaceCode(path + "synthetic_loop20.ttl", destPath + "rdf3x_loop20.ttl");
        replaceCode(path + "synthetic_loop40.ttl", destPath + "rdf3x_loop40.ttl");
        replaceCode(path + "synthetic_loop60.ttl", destPath + "rdf3x_loop60.ttl");
*/
        
         String path = "/home/aidb/nas-synthetic/";
        String destPath = "/home/aidb/nas-synthetic/";
       // replaceCode(path + "synthetic3_loop.ttl", destPath + "rdf3x_syn3_loop.ttl");
        //replaceCode(path + "synthetic3_loop1.ttl", destPath + "rdf3x_syn3_loop1.ttl");
       // replaceCode(path + "synthetic3_loop10.ttl", destPath + "rdf3x_syn3_loop10.ttl");
       // replaceCode(path + "synthetic3_loop20.ttl", destPath + "rdf3x_syn3_loop20.ttl");
        //replaceCode(path + "synthetic3_loop40.ttl", destPath + "rdf3x_syn3_loop40.ttl");
       // replaceCode(path + "synthetic3_loop60.ttl", destPath + "rdf3x_syn3_loop60.ttl");
        
         replaceCode(path + "xaa", destPath + "rdf3x_xaa");
         replaceCode(path + "xab", destPath + "rdf3x_xab");
         replaceCode(path + "xac", destPath + "rdf3x_xac");
         replaceCode(path + "xad", destPath + "rdf3x_xad");

        /*
        String path = "/home/aidb/Dropbox/BugFinderJena/src/main/resources/input/stream/";
        String destPath = "/home/aidb/Dropbox/BugFinderJena/src/main/resources/input/rdf3x/";
        replaceCode(path + "profile.ttl", destPath + "rdf3x_profile.ttl");
        replaceCode(path + "IV_IO.ttl", destPath + "rdf3x_iv_io.ttl");
        replaceCode(path + "feat.ttl", destPath + "rdf3x_feat.ttl");
        replaceCode(path + "leaky_bucket.ttl", destPath + "rdf3x_leaky_bucket.ttl");

        replaceCode(path + "sgfnode.ttl", destPath + "rdf3x_sgfnode.ttl");
        replaceCode(path + "Utilities_iohb.ttl", destPath + "rdf3x_utilities_iohb.ttl");
        replaceCode(path + "FrontMtx_IO.ttl", destPath + "rdf3x_frontmtx_io.ttl");
      */
        /* replaceCode(path + "rose_bt_single.ttl", destPath + "rose_bt_single.ttl");
        replaceCode(path + "rose_ep_single.ttl", destPath + "rose_ep_single.ttl");
        replaceCode(path + "rose_ft_single.ttl", destPath + "rose_ft_single.ttl");
        replaceCode(path + "rose_is_single.ttl", destPath + "rose_is_single.ttl");
*/
     }

    static void synthetic_loop() throws Exception {
        String path = "src/main/resources/input/nas/";
        String destPath = "/home/aidb/Dropbox/pato/tools/onto-build-rose/test/nas/";
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop1.c", 1000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop10.c", 10000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop20.c", 20000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop40.c", 40000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop60.c", 60000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop80.c", 80000);
        replicateCode(path + "synthetic_loop.c", destPath + "synthetic_loop100.c", 100000);
        //replicateCode(path + "synthetic_loop.c", path + "synthetic_loop600.c", 600000);
    }
    
     static void synthetic2_loop() throws Exception {
        String path = "src/main/resources/input/nas/";
        String destPath = "/home/aidb/Dropbox/pato/tools/onto-build-rose/test/nas/";
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop1.c", 1000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop10.c", 10000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop20.c", 20000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop40.c", 40000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop60.c", 60000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop80.c", 80000);
        replicateCode(path + "synthetic2_loop.c", destPath + "synthetic2_loop100.c", 100000);
        //replicateCode(path + "synthetic_loop.c", path + "synthetic_loop600.c", 600000);
    }
    
     static void synthetic3_loop() throws Exception {
        String path = "src/main/resources/input/nas/";
        String destPath = "/home/aidb/Dropbox/pato/tools/onto-build-rose/test/nas/";
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop1.c", 1000);
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop10.c", 10000);
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop20.c", 20000);
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop40.c", 40000);
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop60.c", 60000);
        /*replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop80.c", 80000);
        replicateCode2(path + "synthetic3_loop.c", destPath + "synthetic3_loop100.c", 100000);
        *///replicateCode(path + "synthetic_loop.c", path + "synthetic_loop600.c", 600000);
    }
    
    
  static void synthetic_stream() throws Exception {
        String path = "src/main/resources/input/stream/";
        String destPath = "/home/aidb/Dropbox/pato/tools/onto-build-rose/test/stream/";
        replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream1.c", 1000);
        replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream2.c", 2000);
        replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream3.c", 3000);
        replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream4.c", 4000);
        replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream5.c", 5000);
        //replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream8.c", 60000);
        //replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream10.c", 80000);
        //replicateCode(path + "synthetic_stream.c", destPath + "synthetic_stream100.c", 100000);
        //replicateCode(path + "synthetic_stream.c", path + "synthetic_stream600.c", 600000);
    }

    public static String replicateCode(String input, String output, int noOfLines) throws Exception {

        String data = "";
        String header = "#include <stdio.h>\n\n";
        try (FileOutputStream outputStream = new FileOutputStream(output)) {

            byte[] readAllBytes = Files.readAllBytes(Paths.get(input));
            data = new String(readAllBytes);
            int count = StringUtils.countMatches(data, "\n");
            outputStream.write(header.getBytes());
            int loopCounter = noOfLines / count;
            for (int i = 0; i <= loopCounter; ++i) {
                String data2 = data.replaceAll("foo", "foo" + i);
                outputStream.write(data2.getBytes());

            }
        }
        return data;
    }
    
    //code replication in one single large function
     public static String replicateCode2(String input, String output, int noOfLines) throws Exception {

        String data = "";
        String header = "#include <stdio.h>\n\n void foo(){\n";
        try (FileOutputStream outputStream = new FileOutputStream(output)) {

            byte[] readAllBytes = Files.readAllBytes(Paths.get(input));
            data = new String(readAllBytes);
            int count = StringUtils.countMatches(data, "\n");
            outputStream.write(header.getBytes());
            int loopCounter = noOfLines / count;
            String data2 ="";
            for (int i = 0; i <= loopCounter; ++i) {
                data2 = data.replaceAll("index", "index" + i).
                        replaceAll("space", "space" + i).
                        replaceAll("rows", "rows" + i).
                        replaceAll("var", "var" + i).
                        replaceAll("count", "count" + i).
                        replaceAll("total", "total" + i);
            //String data3 = data2.concat("}");
            outputStream.write(data2.getBytes());    

            }
            outputStream.write("}".getBytes());
            // int index, space, rows, var=0, count = 0, total = 0;
        }
        return data;
    }

    public static String replaceCode(String input, String output) throws Exception {

        String data = "";
         byte[] readAllBytes = Files.readAllBytes(Paths.get(input));
            data = new String(readAllBytes);

            String data2 = data.replaceAll("rdf:(\\S*)", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#$1>");
            String data3 = data2.replaceAll("file0:(\\S*)", "<file.c#$1>");
            String data4 = data3.replaceAll("c:(\\S*)", "<c#$1>");
            String data5 = data4.replaceAll("(\\s)([0-9\\.]+)(\\s*)(\\.)", "$1\"$2\"$3$4");
            String data6 = data5.replaceAll("\\s(\\.)(\\n)", "$1$2");
            String data7 = data6.replaceAll("\"(\\d)\"([\\.0-9e+\\-]+)(\\.)(\\n)", "\"$1$2\"$3$4");
            String data8 = data7.replaceAll("(\\d+)([\\.0-9e+\\-]+)\\s*(\\.)(\\n)", "\"$1$2\"$3$4");
            String data9 = data8.replaceAll("@prefix.*\\n", "");

        try (FileOutputStream outputStream = new FileOutputStream(output)) {

           
            outputStream.write(data9.getBytes());

        }
        return data;
    }

}
