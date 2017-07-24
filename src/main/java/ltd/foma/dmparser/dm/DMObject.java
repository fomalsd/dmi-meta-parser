package ltd.foma.dmparser.dm;

import ltd.foma.dmparser.dmi.Sprite;

import java.util.Map;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class DMObject extends AbstractDMObject {
    protected Sprite icon_state;
    protected Sprite item_state;
    private transient Sprite item_stateL;
    private transient Sprite item_stateR;
    //physicsCrap:
//    W_CLASS w_class;
//    W_CLASS max_w_class;
//    int force;
//    int force_unwielded;
//    int force_wielded;
//    int throw_speed;
//    int throw_range;
//    int throwforce;
//    SLOT_FLAGS slot_flags;
//    RESISTANCE_FLAGS resistance_flags;
//    FLAGS ltd.foma.dmparser.flags;
//    List<Map<ORIGIN_TECH, Integer>> origin_tech; //"combat=6;powerstorage=5;magnets=4"
//    String hitsound;
//    String fire_sound;
//    int max_amount;
//    int armour_penetration;
//    List<String> attack_verb; //list("bludgeoned", "smashed", "beaten")
//    String mag_type; // /obj/item/ammo_box/magazine/toy/pistol
//    double slowdown; //2.0
//    List<Map<MATERIAL, Integer>> materials; // list(MAT_METAL=1000)
//    List<Map<DAMAGE_TYPE, Integer>> armor; // list(melee = 10, bullet = 0, laser = 0,energy = 0, bomb = 0, bio = 0, rad = 0, fire = 100, acid = 60)

//    DMObject(String filename, String desc, Sprite icon_state, Map<String, String> otherCrap) {
//        this(filename, desc, icon_state, null, otherCrap);
//    }

    DMObject(String name, String desc, Sprite icon_state, Sprite item_state, Map<String, String> otherCrap) {
        super(desc, item_state.getState(), icon_state.getState(), name, otherCrap);
        this.item_stateL = item_state;
        this.item_stateR = item_state;
    }
    public boolean hasItemState() {
        return item_stateL != null && item_stateR != null;
    }
    public Sprite getItem_stateL() {
        return item_stateL;
    }

    public void setItem_stateL(Sprite item_state) {
        this.item_stateL = item_state;
    }

    public Sprite getItem_stateR() {
        return item_stateR;
    }

    public void setItem_stateR(Sprite item_state) {
        this.item_stateR = item_state;
    }

    @Override
    public String toString() {
        return name +
                " (" + desc + "):" +
                "\n     icon_state=" + getIcon_state() +
                "\n     item_stateL=" + item_stateL +
                "\n     item_stateR=" + item_stateR +
                "\n     otherCrap=" + otherCrap +
                ";\n";
    }

    public Sprite getIcon_state() {
        return icon_state;
    }

    public void setIcon_state(Sprite icon_state) {
        this.icon_state = icon_state;
    }
}
