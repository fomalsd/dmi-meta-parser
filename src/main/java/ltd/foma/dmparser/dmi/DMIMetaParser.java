package ltd.foma.dmparser.dmi;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class DMIMetaParser {
    final static Logger logger = getLogger(MethodHandles.lookup().lookupClass());

    public static final String STATE = "state = ";
    public static final String DIRS = "dirs = ";
    public static final String FRAMES = "frames = ";
    public static final String DELAY = "delay = ";
    public final String prefix;
    private String input;
    private HashSet<Sprite> sprites = new LinkedHashSet<>();

    public DMIMetaParser(String input, String prefix) {
        this.input = input;
        this.prefix = prefix;
    }

    public DMIMetaParser(Path path){
        this.input = readMeta(path);
        String filename = path.getFileName().toString();
        this.prefix = filename.substring(0, filename.indexOf(".")).concat("_");
    }

    private String readMeta(Path path) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(path.toFile());
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if(tag.toString().contains("# BEGIN DMI")){
                        return tag.toString();
                    }
                }
            }
        } catch (ImageProcessingException | IOException e) {
            logger.error("{}: {}", path.getFileName(), e.getMessage());
        }
        return ""; //?
    }

    public void parse() {
//        input = input.replace("&#x0A;", "\r\n").replace("&#x09;", "").replace("&quot;", "");
//        logger.info(input);
        input = input.replace("\"", "");
        String state = null;
        int dirs = 0;
        int frames = 0;
        String delay = "";
        int offset = 0;
        boolean inObj = false;
        try (LineNumberReader lr = new LineNumberReader(new StringReader(input))) {
            for (String line; (line = lr.readLine()) != null; ) {
                if (inObj) {
                    if (line.contains(STATE) || line.startsWith("#")) { //finish parsing one sprite
                        inObj = false;
                        Sprite s;
                        if (frames > 1) {
                            s = new Sprite(state, dirs, frames, delay, offset, prefix);
                        } else {
                            s = new Sprite(state, dirs, frames, offset, prefix);
                        }
                        sprites.add(s);
                        offset += frames*dirs;
                    } else if (line.contains(DIRS)) {
                        dirs = Integer.parseInt(line.trim().substring(DIRS.length()));
                    } else if (line.contains(FRAMES)) {
                        frames = Integer.parseInt(line.trim().substring(FRAMES.length()));
                    } else if (line.contains(DELAY)) {
                        delay = line.trim().substring(DELAY.length());
                    }
                }
                if (line.contains(STATE) && !inObj) {
                    inObj = true;
                    state = line.substring(STATE.length());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public HashSet<Sprite> getSprites() {
        return sprites;
    }
}
