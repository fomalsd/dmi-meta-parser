package ltd.foma.dmparser.dm;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Foma.ltd on 23.05.2017.
 */
public class DMFile implements Serializable {
    String filename;
    Set<AbstractDMObject> dmObjects;

    public DMFile(String filename, Set<AbstractDMObject> dmObjects) {
        this.filename = filename;
        this.dmObjects = dmObjects;
    }

    public String getFilename() {
        return filename;
    }

    public Set<AbstractDMObject> getDmObjects() {
        return dmObjects;
    }

    @Override
    public String toString() {
//        (!dmObjects.isEmpty() ? " {" + dmObjects + "}" : "")
        final StringBuilder sb = new StringBuilder(filename);
        if (!dmObjects.isEmpty()) {
            sb.append(":\n");
            for (AbstractDMObject dmo : dmObjects) {
                sb.append(dmo);
            }
        } else {
            sb.append(": nothing of interest inside");
        }

        return sb.toString();
    }
}
