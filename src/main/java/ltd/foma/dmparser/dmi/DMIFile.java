package ltd.foma.dmparser.dmi;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by Foma.ltd on 23.05.2017.
 */
public class DMIFile implements Serializable {
    String icon;
    HashSet<Sprite> states;

    public DMIFile(String icon, HashSet<Sprite> sprites) {
        this.icon = icon;
        this.states = sprites;
    }

    public String getIcon() {
        return icon;
    }

    public HashSet<Sprite> getStates() {
        return states;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(icon);
        if (!states.isEmpty()) {
            sb.append(":\n");
            for (Sprite spr : states) {
                sb.append(spr).append("; ");
            }
        } else {
            sb.append(": nothing of interest inside");
        }
        sb.append("\n");
        return sb.toString();
    }
}
