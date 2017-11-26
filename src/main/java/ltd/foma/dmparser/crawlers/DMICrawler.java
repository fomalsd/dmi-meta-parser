package ltd.foma.dmparser.crawlers;

import ltd.foma.dmparser.dmi.DMIFile;
import ltd.foma.dmparser.dmi.DMIMetaParser;
import ltd.foma.dmparser.dmi.Sprite;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.LinkedList;

import static java.nio.file.FileVisitResult.CONTINUE;
import static ltd.foma.dmparser.io.Separator.pathToPortableString;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class DMICrawler extends SimpleFileVisitor<Path> {
    final static Logger logger = getLogger(MethodHandles.lookup().lookupClass());
    private final transient PathMatcher matcher;
    private  LinkedList<DMIFile> dmiFiles = new LinkedList<>();
    private transient final Path startingPath;

    public DMICrawler(Path path) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + "*.png");
        startingPath = path;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        Path name = file.getFileName();

        if(matcher.matches(name)) {
//            logger.info("visiting {}", file);
            DMIMetaParser dmp = new DMIMetaParser(file);
            dmp.parse();
            HashSet<Sprite> spriteList = dmp.getSprites();
            if (!spriteList.isEmpty()) {
                DMIFile dmiFile = new DMIFile(pathToPortableString(startingPath.relativize(file)).replace(".png",".dmi"), spriteList);

                dmiFiles.add(dmiFile);
            }
        }

        return CONTINUE;
    }

    public LinkedList<DMIFile> getDmiFiles() {
        return dmiFiles;
    }
}
