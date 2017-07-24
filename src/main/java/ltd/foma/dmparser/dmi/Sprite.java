package ltd.foma.dmparser.dmi;

import java.io.Serializable;

/**
 * Created by Foma.ltd on 22.05.2017.
 */
public class Sprite implements Serializable {
    private final String unityName;
    private String state;
    private final transient String prefix;
    private String delay; //passing it as is, I guess?
    private int offset, frames, dirs;


    public Sprite(String state, int dirs, int frames, String delay, int offset, String prefix) {
        this.state = state;
        this.dirs = dirs;
        this.frames = frames;
        this.delay = delay;
        this.offset = offset;
        this.prefix = prefix;
        this.unityName = prefix+offset;
    }

    public Sprite(String state) {
        this.state = state;
        this.prefix = "";
        this.unityName = "";
    }

    public Sprite(String state, int dirs, int frames, int offset, String prefix) {
        this(state, dirs, frames, null, offset, prefix);
    }

    public int getOffset() {
        return offset;
    }

    public String getUnityName() {
        return unityName;
    }

    public String getState() {
        return state;
    }

    public boolean hasDelay() {
        return delay != null;
    }

    @Override
    public String toString() {
        String frameInfo = ": " + this.frames + " frames (" + delay + ")";
        return state + "(" + getUnityName() + ")" + (frames > 1 ? frameInfo : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprite sprite = (Sprite) o;

        if (!state.equals(sprite.state)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }
}
