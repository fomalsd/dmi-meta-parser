package ltd.foma.dmparser.dm;

import com.google.gson.internal.LinkedHashTreeMap;
import ltd.foma.dmparser.HierLinkedHashMap;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class DMParser {
    public static final String OBJ = "/obj/";
    public static final String DELIMITER = "\r\n";
    public static final String LIST_BEGIN = "list(";
    public static final String EQUALS = "=";
    public static final String COMMENTS = "//";
    public static final String NULL = "null";
    public static final String EMPTY = "empty ";
    final static Logger logger = getLogger(MethodHandles.lookup().lookupClass());
    private File filename;
    private Set<Map<String, String>> parsedFile = new TreeSet<>();

    public DMParser(Path filename) {
        this.filename = filename.toFile();
    }

    public DMParser(File filename) {
        this.filename = filename;
    }

    public static <T extends Enum<T>> T lookupEnum(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
        return null;
    }

    private static Integer getInt(String str) {
        if (str == null) {
            return 0;
        } else try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void parse() {
        StringBuilder sb = new StringBuilder();

        boolean inObj = false;
        try (LineNumberReader lr = new LineNumberReader(new FileReader(filename))) {
            for (String line; (line = lr.readLine()) != null; ) {
                if (inObj) {
                    if (line.startsWith(OBJ)) { //finish parsing one sprite
                        inObj = false;
                        String tmp = sb.toString();
//                            logger.info("tmp = {}",tmp);
                        tryParseObj(tmp);
                        sb.setLength(0);

                    } else if (line.contains(EQUALS) && !line.contains(NULL) && !line.contains("[")) {
                        int comments = line.indexOf(COMMENTS);
                        if (comments == -1) {
                            sb.append(line.trim().replace("\"", "")).append(DELIMITER);
                        } else {
                            sb.append(line.substring(0, comments).trim().replace("\"", "")).append(DELIMITER);
                        }
                    }
                }
                if (line.startsWith(OBJ) && !inObj && !line.contains("(")) { //begin capturing stuff
                    inObj = true;
                    int comments = line.indexOf(COMMENTS);
                    if (comments == -1) {
                        sb.append("hierarchy = ").append(line).append(DELIMITER);
                    } else {
                        sb.append("hierarchy = ").append(line.substring(0, comments).trim()).append(DELIMITER);
                    }
                }
            }
            tryParseObj(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryParseObj(String tmp) throws IOException {
        if (tmp.contains("name") && tmp.contains("icon_state")) { //don't bother parsing if it doesn't have filename
            parseObject(tmp);
        }
    }

    private void parseObject(String string) throws IOException {
        Map<String, String> dmoParams = new HierLinkedHashMap<>();

        //read line-by line, placing 'key = value' pairs into the map, that gets processed later
        String[] lines = string.split(DELIMITER);
        for (String line : lines) {
            String[] d = line.split(EQUALS);
            if (d.length == 2) {
                String key = d[0].trim();
                String val = d[1].trim().replace("|", ",");
                dmoParams.put(key, val.startsWith("'") ? val.replace("'", "") : val); //remove singlequotes from paths
//                logger.debug("{}: {}", key, val);
            }
        }
//        logger.info("{}",dmoParams);
        parsedFile.add(dmoParams);
//        String hierarchy = dmoParams.get("hierarchy");
//        String name = dmoParams.get("filename");
//        String desc = dmoParams.get("desc");
//        String icon_state = dmoParams.get("icon_state");
//        String item_state = dmoParams.get("item_state");
//        String icon = dmoParams.get("icon");
//
//        dmoParams.remove("hierarchy");
//        dmoParams.remove("filename");
//        dmoParams.remove("desc");
//        dmoParams.remove("icon_state");
//        dmoParams.remove("item_state");
//        dmoParams.remove("icon");

//        if (icon_state != null && desc != null) {
//            if (item_state != null) {
//        dmoList.add(new JsonDMObject(hierarchy, name, desc, icon_state, item_state, icon, dmoParams));
//            } else {
//                dmoList.add(new DMObject(filename, desc, new Sprite(icon_state), dmoParams));
//            }
//        } /*else {
//            logger.warn("Ignored something! {} ({}): item_state={}, otherCrap={}", filename, desc, item_state, dmoParams);
//        }*/
    }

    public Set<Map<String, String>> getParsedFile() {
        return parsedFile;
    }
//    public HashSet<AbstractDMObject> getDmoList() {
//        return dmoList;
//    }
}
