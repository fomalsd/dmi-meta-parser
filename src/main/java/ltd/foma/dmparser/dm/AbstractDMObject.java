package ltd.foma.dmparser.dm;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Foma.ltd on 24.05.2017.
 */
public abstract class AbstractDMObject implements Serializable {

    protected String hierarchy;
    protected String name;
    protected String desc;
    protected final String item_state;
    protected final String icon_state;
    protected Map<String, String> otherCrap;

    public AbstractDMObject(String name, String desc, String icon_state, String item_state, Map<String, String> otherCrap) {
        this.desc = desc;
//        this.item_state = item_state;
//        this.icon_state = icon_state;
        this.name = name;
        initOtherCrap(otherCrap);
        this.icon_state = icon_state;
        this.item_state = item_state;
    }

    protected void initOtherCrap(Map<String, String> otherCrap) {
        this.otherCrap = otherCrap;
        //todo: parse other crap here and assign to fields
//todo: for icon, righthand_file, lefthand_file - parse path and search icons there
//        SLOT_FLAGS slot_flags = lookupEnum(SLOT_FLAGS.class, dmoParams.get("slot_flags"));
//        Integer force = getInt(dmoParams.get("force"));
//        Integer throwforce = getInt(dmoParams.get("throwforce"));
//        W_CLASS w_class = lookupEnum(W_CLASS.class, dmoParams.get("w_class"));
// adv       List<Map<ORIGIN_TECH, Integer>> origin_tech;
// noob       String origin_tech = dmoParams.get("origin_tech");
// adv      List<String> attack_verb = new ArrayList<>(Arrays.asList(section.get("attack_verb").split(",")));
// noob       String attack_verb = dmoParams.get("attack_verb");
// adv       List<Map<DAMAGE_TYPE, Integer>> armor = section.get("armor").substring(LIST_BEGIN.length());
// noob       String armor = dmoParams.get("armor");
        /*if(filename != null) {
                logger.info("{} ({}): icon_state={}, item_state={}, slot_flags={}, force={}, throwforce={}, W_CLASS={}, ORIGIN_TECH={}, attack_verb={}, armor={}",
                        filename, desc, icon_state, item_state, slot_flags, force, throwforce, w_class, origin_tech, attack_verb, armor);
            }*/
    }

}
