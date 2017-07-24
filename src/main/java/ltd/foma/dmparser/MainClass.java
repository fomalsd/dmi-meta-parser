package ltd.foma.dmparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ltd.foma.dmparser.crawlers.DMCrawler;
import ltd.foma.dmparser.crawlers.DMICrawler;
import ltd.foma.dmparser.io.Writer;
import org.slf4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class MainClass {
   final static Logger logger = getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        if(args.length == 0){
            logger.error("Path not provided! provide path to tgstation root folder as a first argument:\n" +
                    "java -jar dmi_meta_parser.jar C:/tgstation-master");
            System.exit(0);
        }
        Path path = Paths.get(args[0]);
        logger.info("Crawling {}", path);
        crawlDM(path);
        crawlDMI(path);
        logger.info("Done! look inside that folder you provided");

    }

    private static void crawlDMI(Path inpath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //icons folder in tgstation-master
        Path path = Paths.get(inpath.toString(), "icons");
        logger.info("Crawling DMI in {}",path);
        try {
            DMICrawler crawler = new DMICrawler(inpath);
            Files.walkFileTree(path, crawler);
            String json = gson.toJson(crawler.getDmiFiles());
            Path outFile = Paths.get(path.getParent().toString(), "dmi.json");
            Writer.write(outFile, json);
//            logger.info("Wrote dmi json to {}", outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crawlDM(Path inpath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //code folder in tgstation-master
        Path path = Paths.get(inpath.toString(), "code");
        logger.info("Crawling DM in {}",path);
        try {
            //writing dm json
            DMCrawler crawler = new DMCrawler(inpath);
            Files.walkFileTree(path, crawler);
            String json = gson.toJson(crawler.getAllObjects());
            Path outFile = Paths.get(path.getParent().toString(), "dm.json");
            Writer.write(outFile, json);

            //writing hier
            Set<String> hier = crawler.getAllObjects()
                    .stream()
                    .map(obj -> obj.get("hierarchy"))
                    .collect(Collectors.toCollection(TreeSet::new));
            StringBuilder sb = new StringBuilder();
            hier.forEach(x->sb.append(x).append("\n"));
           sb.append("total: ").append(hier.size());
            Writer.write(Paths.get(path.getParent().toString(), "hier.txt"), sb.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
