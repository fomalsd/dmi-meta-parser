package ltd.foma.dmparser.crawlers;

import ltd.foma.dmparser.dm.AbstractDMObject;
import ltd.foma.dmparser.dm.DMFile;
import ltd.foma.dmparser.dm.DMParser;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static ltd.foma.dmparser.io.Separator.*;
import static java.nio.file.FileVisitResult.CONTINUE;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class DMCrawler extends SimpleFileVisitor<Path> {
    final static Logger logger = getLogger(MethodHandles.lookup().lookupClass());
    private final transient PathMatcher matcher;
//    private LinkedList<DMFile> dmFiles = new LinkedList<>();
//    private transient LinkedList<HashSet<Map<String,String>>> dmFiles = new LinkedList<>();
    private Set<Map<String,String>> allObjects = new TreeSet<>();
    private transient final Path startingPath;
    public DMCrawler(Path path){
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + "*.dm");
        startingPath = path;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        Path name = file.getFileName();

        if(matcher.matches(name)) {
            DMParser dmp = new DMParser(file);
            dmp.parse();
//            HashSet<AbstractDMObject> dmoList = dmp.getDmoList();
//            if (!dmoList.isEmpty()) {
//                DMFile dmFile = new DMFile(pathToPortableString(startingPath.relativize(file)), dmoList);
//                logger.debug("dmFile: {}", dmFile.getFilename());
//                dmFiles.add(dmFile);
//            }
            Set<Map<String,String>> parsedFile = dmp.getParsedFile();
            if(!parsedFile.isEmpty()){
                allObjects.addAll(parsedFile);
//                logger.info("{}", parsedFile);
//                dmFiles.add(parsedFile);
            }
        }
        return CONTINUE;
    }

    public Set<Map<String, String>> getAllObjects() {
        return allObjects;
    }
    //    public LinkedList<HashSet<Map<String,String>>> getDmFiles() {
//        return dmFiles;
//    }
}
