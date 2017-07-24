package ltd.foma.dmparser.io;

import java.nio.file.Path;

/**
 * Created by foma on 25.05.2017.
 */
public class Separator {
    static public String pathToPortableString(Path p)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        Path root = p.getRoot();
        if (root != null)
        {
            sb.append(root.toString().replace('\\','/'));
        /* root elements appear to contain their
         * own ending separator, so we don't set "first" to false
         */
        }
        for (Path element : p)
        {
            if (first)
                first = false;
            else
                sb.append("/");
            sb.append(element.toString());
        }
        return sb.toString();
    }
}
