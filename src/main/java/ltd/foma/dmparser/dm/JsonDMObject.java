package ltd.foma.dmparser.dm;

import ltd.foma.dmparser.dmi.Sprite;

import java.util.Map;

/**
 * Created by Foma.ltd on 24.05.2017.
 */
public class JsonDMObject extends AbstractDMObject {
    private final String icon;


    public JsonDMObject(String hierarchy, String name, String desc, String icon_state, String item_state, String icon, Map<String, String> otherCrap) {
        super(name, desc, icon_state, item_state, otherCrap);
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name +
                " (" + desc + "):" +
                "\n     icon_state=" + icon_state +
                "\n     item_state=" + item_state +
                "\n     icon=" + this.icon +
                "\n     otherCrap=" + otherCrap +
                ";\n";
    }

    public Sprite getIcon_state() {
        return new Sprite(icon_state);
    }

    public boolean hasItemState() {
        return item_state != null;
    }
}
