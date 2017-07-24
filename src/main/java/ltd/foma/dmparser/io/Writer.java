package ltd.foma.dmparser.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;

/**
 * Created by Foma.ltd on 24.05.2017.
 */
public class Writer {
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void write(Path outFile, String contents) {
        write(outFile, false, contents);
    }
    public static void write(Path outFile, boolean append, String contents) {
//        logger.debug("..writing started! append="+append);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile.toFile(), append))) {
            bw.write(contents);
            logger.info("Wrote a file {}", outFile);
        } catch (IOException e) {
            logger.error("Error: {}", e.getMessage());
        }
//        logger.debug("..write finished");
    }
}
